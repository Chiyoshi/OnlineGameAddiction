package com.oga.app.service.provider.redstone.daily;

import java.util.Map;

import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.HttpClientUtil;
import com.oga.app.dataaccess.dao.DailyRewardHistoryDao;
import com.oga.app.dataaccess.entity.DailyRewardHistory;
import com.oga.app.master.provider.MasterRepositoryProvider;
import com.oga.app.service.provider.base.RedstoneApiProviderBase;
import com.oga.app.service.response.LoginResult;
import com.oga.app.service.response.redstoneapi.DailyRewardResponse;
import com.oga.app.service.response.redstoneapi.WalletResponse;

public class RedstoneDailyRewardProvider extends RedstoneApiProviderBase {

	/** RedstoneDailyProvider */
	private static RedstoneDailyRewardProvider redstoneDailyRewardProvider;

	/** MasterRepositoryProvider */
	private MasterRepositoryProvider masterRepositoryProvider;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WEBドライバー
	 */
	public RedstoneDailyRewardProvider() {
		this.masterRepositoryProvider = MasterRepositoryProvider.getInstance();
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedstoneDailyRewardProvider getInstance() {
		if (redstoneDailyRewardProvider == null) {
			redstoneDailyRewardProvider = new RedstoneDailyRewardProvider();
		}
		return redstoneDailyRewardProvider;
	}

	/**
	 * デイリーリワード履歴を登録する
	 * 
	 * @param dailyRewardHistory デイリーリワード履歴
	 */
	public void insertDailyRewardHistory(DailyRewardHistory dailyRewardHistory) {
		if (dailyRewardHistory != null) {
			DailyRewardHistoryDao.getInstance().insert(dailyRewardHistory);
		}
	}

	/**
	 * デイリーリワードAPIを実行してレスポンス内容を返却する
	 * 
	 * @param loginResult 認証情報
	 * @throws SystemException API通信エラーが発生した場合
	 */
	public DailyRewardResponse performDailyRewardApi(LoginResult loginResult) throws SystemException {
		// デイリーリワードAPIのURLを取得する
		String url = masterRepositoryProvider.getRedstoneApiUrl("redstone.api.dailyreward");

		// リクエストプロパティ
		Map<String, String> requestProperties = createRequestProperties(loginResult.getAccessToken(), loginResult.getHttpTransactionLog());

		// デイリーリワード
		String jsonString = HttpClientUtil.sendPost(url, requestProperties, null);

		return loadJsonStringApiResponse(jsonString, DailyRewardResponse.class);
	}

	/**
	 * デイリーリワードAPIを実行してレスポンス内容を返却する
	 * 
	 * @param loginResult 認証情報
	 * @throws SystemException API通信エラーが発生した場合
	 */
	public WalletResponse performWalletApi(LoginResult loginResult) throws SystemException {
		// 残高照会APIのURLを取得する
		String url = masterRepositoryProvider.getRedstoneApiUrl("redstone.api.wallet");

		// リクエストプロパティ
		Map<String, String> requestProperties = createRequestProperties(loginResult.getAccessToken(), loginResult.getHttpTransactionLog());

		// 残高照会
		String jsonString = HttpClientUtil.sendPost(url, requestProperties, null);

		return loadJsonStringApiResponse(jsonString, WalletResponse.class);
	}

}
