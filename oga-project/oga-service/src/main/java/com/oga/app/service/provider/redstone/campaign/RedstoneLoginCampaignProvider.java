package com.oga.app.service.provider.redstone.campaign;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oga.app.common.enums.LoginCampaignType;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.HttpClientUtil;
import com.oga.app.dataaccess.dao.LoginCampaignDetailsDao;
import com.oga.app.dataaccess.dao.LoginCampaignHistoryDao;
import com.oga.app.dataaccess.entity.LoginCampaignDetails;
import com.oga.app.dataaccess.entity.LoginCampaignHistory;
import com.oga.app.master.model.RedstoneApi;
import com.oga.app.master.provider.MasterRepositoryProvider;
import com.oga.app.service.provider.base.RedstoneApiProviderBase;
import com.oga.app.service.response.LoginResult;
import com.oga.app.service.response.redstoneapi.LoginCampaignResponse;
import com.oga.app.service.response.redstoneapi.State;

public class RedstoneLoginCampaignProvider extends RedstoneApiProviderBase {

	/** RedstoneLoginCampaignProvider */
	private static RedstoneLoginCampaignProvider redstoneLoginCampaignProvider;

	/** MasterRepositoryProvider */
	private MasterRepositoryProvider masterRepositoryProvider;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WEBドライバー
	 */
	public RedstoneLoginCampaignProvider() {
		this.masterRepositoryProvider = MasterRepositoryProvider.getInstance();
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedstoneLoginCampaignProvider getInstance() {
		if (redstoneLoginCampaignProvider == null) {
			redstoneLoginCampaignProvider = new RedstoneLoginCampaignProvider();
		}
		return redstoneLoginCampaignProvider;
	}

	/**
	 * ログインキャンペーン詳細を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetMonth 対象月
	 * @return ログインキャンペーン詳細
	 */
	public LoginCampaignDetails fetchLoginCampaignDetails(String userId, String targetMonth) {
		return LoginCampaignDetailsDao.getInstance().findByPKey(userId, targetMonth);
	}

	/**
	 * ログインキャンペーン詳細の件数を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetMonth 対象月
	 * @return 件数
	 */
	public int countLoginCampaignDetails(String userId, String targetMonth) {
		return LoginCampaignDetailsDao.getInstance().countByPKey(userId, targetMonth);
	}

	/**
	 * ログインキャンペーン詳細を登録または更新する
	 * 
	 * @param loginCampaignDetails ログインキャンペーン詳細
	 */
	public void updateLoginCampaignDetails(LoginCampaignDetails loginCampaignDetails) {
		// 件数取得
		int count = countLoginCampaignDetails(loginCampaignDetails.getUserId(), loginCampaignDetails.getTargetMonth());

		if (count > 0) {
			LoginCampaignDetailsDao.getInstance().update(loginCampaignDetails);
		} else {
			LoginCampaignDetailsDao.getInstance().insert(loginCampaignDetails);
		}
	}

	/**
	 * ログインキャンペーン履歴を登録する
	 * 
	 * @param loginCampaignHistory ログインキャンペーン履歴
	 */
	public void insertLoginCampaignHistory(LoginCampaignHistory loginCampaignHistory) {
		LoginCampaignHistoryDao.getInstance().insert(loginCampaignHistory);
	}

	/**
	 * ログインキャンペーン情報照会APIを実行する
	 * 
	 * @param loginResult 認証情報
	 * @return ログインキャンペーン情報照会のレスポンス情報
	 * @throws SystemException 
	 */
	public State performLoginCampaignStateApi(LoginResult loginResult) throws SystemException {
		// ログインキャンペーン情報照会APIのURLを取得する
		String url = masterRepositoryProvider.getRedstoneApiUrl("redstone.api.redslogin.state");

		// リクエストプロパティ
		Map<String, String> requestProperties = createRequestProperties(loginResult.getAccessToken(), loginResult.getHttpTransactionLog());

		// ログインキャンペーン情報照会
		String jsonString = HttpClientUtil.sendPost(url, requestProperties, null);

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readTree(jsonString);
			return mapper.convertValue(rootNode.path("data"), State.class);
		} catch (JsonProcessingException e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	/**
	 * ログインキャンペーンAPIを実行する
	 * 
	 * @param loginResult 認証情報
	 * @param loginCampaignType ログインキャンペーン種別(通常時：0、コンプリート：1～3)
	 * @throws SystemException 
	 */
	public LoginCampaignResponse performLoginCampaignApi(LoginResult loginResult, LoginCampaignType loginCampaignType) throws SystemException {
		// ログインキャンペーンAPIのURLを取得する
		RedstoneApi redstoneApi = masterRepositoryProvider.getRedstoneApiRepository().get("redstone.api.redslogin");

		// リクエストプロパティ
		Map<String, String> requestProperties = createRequestProperties(loginResult.getAccessToken(), loginResult.getHttpTransactionLog());

		// リクエストパラメータ
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("chapter", loginCampaignType.getValue());

		// ログインキャンペーン
		String jsonString = HttpClientUtil.sendPost(redstoneApi.getUrl(), requestProperties, formParams);

		return loadJsonStringApiResponse(jsonString, LoginCampaignResponse.class);
	}

}
