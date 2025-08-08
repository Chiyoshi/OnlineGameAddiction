package com.oga.app.service.businesslogic.redstone.login;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.devtools.v138.network.model.Request;
import org.openqa.selenium.devtools.v138.network.model.RequestId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.common.utils.StringUtil;
import com.oga.app.devtools.EdgeDevToolsSessionManager;
import com.oga.app.master.provider.MasterRepositoryProvider;
import com.oga.app.service.beans.LoginServiceBean;
import com.oga.app.service.businesslogic.BusinessLogicBase;
import com.oga.app.service.provider.redstone.login.RedstoneLoginProvider;
import com.oga.app.service.response.HttpTransactionLog;
import com.oga.app.service.response.LoginResult;
import com.oga.app.service.response.redstoneapi.AccessToken;

/**
 * ログイン処理
 */
public class RedstoneLoginBusinessLogic extends BusinessLogicBase<LoginServiceBean> {

	/** RedstoneLoginBusinessLogic */
	private static RedstoneLoginBusinessLogic redstoneLoginBusinessLogic;

	/** RedstoneLoginProvider */
	private RedstoneLoginProvider redstoneLoginProvider;

	/** MasterRepositoryProvider */
	private MasterRepositoryProvider masterRepositoryProvider;

	/** ログインServiceBean */
	private LoginServiceBean loginServiceBean;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WebDriver
	 */
	public RedstoneLoginBusinessLogic() {
		this.redstoneLoginProvider = RedstoneLoginProvider.getInstance();
		this.masterRepositoryProvider = MasterRepositoryProvider.getInstance();
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedstoneLoginBusinessLogic getInstance() {
		if (redstoneLoginBusinessLogic == null) {
			redstoneLoginBusinessLogic = new RedstoneLoginBusinessLogic();
		}
		return redstoneLoginBusinessLogic;
	}

	@Override
	protected void createServiceBean(LoginServiceBean serviceBean) {
		this.loginServiceBean = serviceBean;
	}

	/**
	 * データ取得およびチェック処理
	 * <pre>
	 * (1) 入力チェック 
	 *     ・ユーザID 空チェック
	 *     ・パスワード 空チェック
	 * </pre>
	 */
	@Override
	protected void checkSelectData() throws ApplicationException {
		// 入力チェック
		// ユーザIDが空の場合はエラー
		if (StringUtil.isNullOrEmpty(loginServiceBean.getUserId())) {
			throw new ApplicationException("ユーザIDが入力されていません");
		}

		// パスワードが空の場合はエラー
		if (StringUtil.isNullOrEmpty(loginServiceBean.getPassword())) {
			throw new ApplicationException("パスワードが入力されていません");
		}
	}

	/**
	 * ログイン処理のビジネスロジック
	 * <pre>
	 * (1) Edgeブラウザを初期化する
	 * (2) DevToolsに接続してネットワーク監視を有効化する
	 * (3) 認証APIに関連するリクエストを監視対象に設定する
	 * (4) ログイン処理を呼び出す
	 * (5) ログインチェック
	 * (6) 認証APIのレスポンス情報を解析して認証結果を構築する
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws ApplicationException {
		// ブラウザが初期化済みではない場合
		if (!loginServiceBean.isBrowserInitialized()) {
			// ブラウザを起動する
			redstoneLoginProvider.browserInitializer();

			loginServiceBean.setBrowserInitialized(true);
		}

		LogUtil.info("[ログイン処理] [START]");

		// レッドストーンAPI（ログイン認証）のURLを取得する
		String authUrl = masterRepositoryProvider.getRedstoneApiUrl("redstone.api.authenticate");

		Map<RequestId, Request> authRequests = new LinkedHashMap<>();

		try (EdgeDevToolsSessionManager sessionManager = new EdgeDevToolsSessionManager()) {
			// ネットワーク監視を有効化
			setupDevToolsSession(sessionManager, authUrl, authRequests);

			LogUtil.info("[ログイン処理] [ログインチェック] [START]");

			if (!performLoginCheck(loginServiceBean.getUserId(), loginServiceBean.getPassword(), 2)) {
				// ログイン状態を設定する
				loginServiceBean.setLogged(false);

				throw new ApplicationException("ログイン処理に失敗しました");
			}

			LogUtil.info("[ログイン処理] [ログインチェック] [END]");
			LogUtil.info("[ログイン処理] [アクセストークン解析] [START]");

			Map.Entry<RequestId, Request> lastAuthEntry = null;
			for (Map.Entry<RequestId, Request> authEntry : authRequests.entrySet()) {
				lastAuthEntry = authEntry;
			}

			String responseBody = sessionManager.getResponseBody(lastAuthEntry.getKey());

			// 認証APIのリクエスト情報チェック
			validateLoginAuthRequest(responseBody);

			LogUtil.info("[ログイン処理] [アクセストークン解析] [END]");

			// ログイン状態を設定する
			loginServiceBean.setLogged(true);
			// 認証結果を設定する
			loginServiceBean.setLoginResult(setLoginResult(lastAuthEntry.getValue(), responseBody));
		}

		LogUtil.info("[ログイン処理] [END]");

	}

	/**
	 * DevToolsセッションを初期化して認証APIに関連するリクエストを検出するための
	 * ネットワークリスナーを登録します。
	 *
	 * @param sessionManager DevTools セッション管理クラス
	 * @param authUrl 認証APIのURL
	 * @param authRequests 認証APIに一致するリクエストを格納するマップ
	 */
	private void setupDevToolsSession(EdgeDevToolsSessionManager sessionManager, String authUrl, Map<RequestId, Request> authRequests) {
		sessionManager.attach(redstoneLoginProvider.getWebDriver());
		sessionManager.addNetworkListeners(event -> {
			Request req = event.getRequest();
			if (!"OPTIONS".equals(req.getMethod()) && req.getUrl().contains(authUrl)) {
				authRequests.put(event.getRequestId(), req);
			}
		});
	}

	/**
	 * 指定されたユーザーIDとパスワードでログイン処理を実行して認証状態をチェックする
	 * 認証失敗時は3回までリトライする
	 *
	 * @param userId ユーザーID
	 * @param password パスワード
	 * @param retryCount リトライ回数
	 * @return  認証成功時はtrue、失敗時にfalseで返却する
	 */
	private boolean performLoginCheck(String userId, String password, int retryCount) {
		// リトライ回数が0回になるまでログインする
		if (retryCount == 0) {
			return false;
		}

		// ログイン処理
		redstoneLoginProvider.performLogin(userId, password);

		// ログインチェック
		if (!redstoneLoginProvider.isLogged()) {
			return performLoginCheck(userId, password, retryCount - 1);
		}

		return true;
	}

	/**
	 * ログイン認証APIのレスポンス情報が存在することをチェックする
	 *
	 * @param responseBody 認証APIのレスポンス内容
	 * @throws ApplicationException リクエストが1件でない場合に例外を発生させる
	 */
	private void validateLoginAuthRequest(String responseBody) throws ApplicationException {
		if (StringUtil.isNullOrEmpty(responseBody)) {
			throw new ApplicationException("ログイン認証APIのアクセストークンを取得できませんでした");
		}
	}

	/**
	 * 認証結果を構築する
	 * 
	 * @param request 認証APIのリクエスト情報
	 * @param response 認証APIのレスポンス内容
	 * @throws ApplicationException
	 */
	@SuppressWarnings("deprecation")
	private LoginResult setLoginResult(Request request, String response) throws ApplicationException {
		LoginResult loginResult = new LoginResult();

		HttpTransactionLog httpTransactionLog = new HttpTransactionLog();
		httpTransactionLog.setRequestUrl(request.getUrl());
		httpTransactionLog.setRequestMethod(request.getMethod());
		httpTransactionLog.setRequestPayload(request.getPostData().orElse(""));
		httpTransactionLog.setRequestHeaders(request.getHeaders());
		httpTransactionLog.setResponseBody(response);
		loginResult.setHttpTransactionLog(httpTransactionLog);

		try {
			ObjectMapper mapper = new ObjectMapper();
			AccessToken accessToken = mapper.readValue(response, AccessToken.class);
			loginResult.setAccessToken(accessToken);
		} catch (JsonMappingException e) {
			throw new ApplicationException(e.getMessage(), e);
		} catch (JsonProcessingException e) {
			throw new ApplicationException(e.getMessage(), e);
		}
		return loginResult;
	}
}
