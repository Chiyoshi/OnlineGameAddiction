package com.oga.app.service.businesslogic.redstone.daily;

import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.DateUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.dataaccess.entity.DailyRewardHistory;
import com.oga.app.dataaccess.entity.RSManagement;
import com.oga.app.service.beans.DailyRewardServiceBean;
import com.oga.app.service.businesslogic.BusinessLogicBase;
import com.oga.app.service.provider.redstone.daily.RedstoneDailyRewardProvider;
import com.oga.app.service.response.LoginResult;
import com.oga.app.service.response.redstoneapi.DailyRewardResponse;
import com.oga.app.service.response.redstoneapi.Reward;
import com.oga.app.service.response.redstoneapi.Wallet;
import com.oga.app.service.response.redstoneapi.WalletResponse;

/**
 * デイリーリワードビジネスロジック
 */
public class RedStoneDailyRewardBusinessLogic extends BusinessLogicBase<DailyRewardServiceBean> {

	/** デイリーリワードビジネスロジック */
	private static RedStoneDailyRewardBusinessLogic redStoneDailyRewardBusinessLogic;

	/** デイリーリワードプロバイダ */
	private RedstoneDailyRewardProvider redstoneDailyRewardProvider;

	/** DailyRewardServiceBean */
	private DailyRewardServiceBean dailyRewardServiceBean;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WebDriver
	 */
	public RedStoneDailyRewardBusinessLogic() {
		this.redstoneDailyRewardProvider = RedstoneDailyRewardProvider.getInstance();
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedStoneDailyRewardBusinessLogic getInstance() {
		if (redStoneDailyRewardBusinessLogic == null) {
			redStoneDailyRewardBusinessLogic = new RedStoneDailyRewardBusinessLogic();
		}
		return redStoneDailyRewardBusinessLogic;
	}

	@Override
	protected void createServiceBean(DailyRewardServiceBean serviceBean) {
		this.dailyRewardServiceBean = serviceBean;

		// 基準日を設定する
		this.dailyRewardServiceBean.setBaseDate(DateUtil.getBaseDate());
	}

	/**
	 * データ取得およびチェック処理
	 * <pre>
	 * 以下のデータ取得およびチェック処理を行う。
	 * ・ログイン状態チェック
	 * ・認証情報チェック
	 * </pre>
	 * @throws SystemException 
	 */
	@Override
	protected void checkSelectData() throws ApplicationException, SystemException {
		// ログイン状態チェック
		if (!dailyRewardServiceBean.isLogged()) {
			throw new ApplicationException("レッドストーンにログインしていないため処理を終了します。");
		}

		// 認証情報チェック
		if (dailyRewardServiceBean.getLoginResult() == null) {
			throw new ApplicationException("認証情報が設定されていないため処理を終了します。");
		}
	}

	/**
	 * デイリーリワード処理
	 * <pre>
	 * 以下の業務処理を行う。
	 * ・レッドストーン管理情報を取得する
	 * ・ログインキャンペーン情報照会APIを実行して現在の情報を取得する
	 * ・ログインキャンペーンを実施するか判定する
	 * ・ログインキャンペーンAPIを実行する
	 * ・ログインキャンペーン情報を更新する
	 * ・ログインキャンペーン履歴を登録する
	 * ・ログインキャンペーンコンプリートマスAPIを実行する
	 * ・レッドストーン管理を更新する
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {
		LogUtil.info("[デイリーリワード処理] [START]");

		// レッドストーン管理情報を取得する
		RSManagement currentRSManagement = fetchRSManagement(dailyRewardServiceBean.getUserId());

		// デイリーリワードを実施するべきか判定する
		if (!shouldExecuteDailyReward(currentRSManagement, dailyRewardServiceBean.getBaseDateSql())) {
			return;
		}

		// デイリーリワードAPI
		DailyRewardHistory dailyRewardHistory = handleDailyReward();

		if (dailyRewardHistory != null) {
			// デイリーリワード履歴を登録する
			insertDailyRewardHistory(dailyRewardHistory);

			LogUtil.info("[デイリーリワード処理] [獲得SP：" + dailyRewardHistory.getServicePoint() + "]");
		}

		LogUtil.info("[デイリーリワード処理] [残高情報更新] [START]");

		// 残高照会API
		RSManagement rsManagement = handleWallet();

		if (rsManagement != null) {
			// レッドストーン管理を更新する
			updateRSManagement(rsManagement);
		}

		LogUtil.info("[デイリーリワード処理] [残高情報更新] [END]");
		LogUtil.info("[デイリーリワード処理] [END]");
	}

	/**
	 * レッドストーン管理情報を取得する
	 * 
	 * @param userId ユーザID
	 * @return
	 */
	private RSManagement fetchRSManagement(String userId) {
		return redstoneDailyRewardProvider.fetchRSManagement(userId);
	}

	/**
	 * ログインキャンペーンを実施するべきか判定する
	 * 
	 * @param rsManagement レッドストーン管理情報
	 * @param loginCampaignDetails ログインキャンペーン詳細情報
	 * @param baseDate 基準日(yyyy-MM-dd形式)
	 * @return
	 */
	private boolean shouldExecuteDailyReward(RSManagement rsManagement, String baseDate) {
		// デイリーリワードフラグが「N」の場合はスキップする
		if (YesNo.NO.getValue().equals(rsManagement.getDailyRewardFlg())) {
			LogUtil.info("デイリーリワードフラグが「N」の場合はスキップします。");
			return false;
		}

		// 既に本日分のデイリーリワードを実施済みの場合はスキップする
		if (baseDate.equals(rsManagement.getLastDailyRewardDate())) {
			LogUtil.info("本日のデイリーリワードは実施済みのためスキップします。");
			return false;
		}

		return true;
	}

	/**
	 * デイリーリワードAPIを実行し、結果に応じたデイリーリワード履歴を生成する。
	 *
	 * <pre>
	 * APIが成功した場合はレスポンス内の情報からデイリーリワード履歴を生成し、
	 * エラーが発生した場合はNULLを返却する。
	 * </pre>
	 *
	 * @return デイリーリワード履歴
	 * @throws SystemException API通信エラーが発生した場合
	 * @throws ApplicationException 想定外のエラー応答が発生した場合
	 */
	private DailyRewardHistory handleDailyReward() throws SystemException, ApplicationException {
		// デイリーリワードAPI
		DailyRewardResponse response = dailyReward(dailyRewardServiceBean.getLoginResult());

		if (response.getErrorInfo() == null) {
			return buildDailyRewardHistory(response.getDailyRewardData().getReward());

		} else if ("DONE".equals(response.getErrorInfo().getMessage())) {
			// エラーメッセージが「DONE」の場合は既にデイリーリワードを実施済みとなる
			// 実施済みの場合は獲得情報が不明なためNULLを返却する
			return null;

		} else {
			throw new ApplicationException("デイリーリワードAPIでエラーが発生しました：" +
					response.getErrorInfo().getMessage());
		}
	}

	private RSManagement handleWallet() throws SystemException, ApplicationException {
		// 残高照会API
		WalletResponse response = fetchWallet(dailyRewardServiceBean.getLoginResult());

		if (response.getErrorInfo() == null && response.isDone()) {
			return buildRSManagement(response.getWallet());

		} else if (!response.isDone()) {
			// 残高情報を取得できなかった場合はNULLを返却する
			return null;

		} else {
			throw new ApplicationException("残高照会APIでエラーが発生しました：" +
					response.getErrorInfo().getMessage());
		}
	}

	/**
	 * デイリーリワードAPIを実行してレスポンス内容を返却する
	 * 
	 * @param loginResult 認証情報
	 * @return API実行結果
	 * @throws SystemException API通信に失敗した場合など、システムレベルの例外
	 */
	private DailyRewardResponse dailyReward(LoginResult loginResult) throws SystemException {
		return redstoneDailyRewardProvider.performDailyRewardApi(loginResult);
	}

	/**
	 * 残高照会APIを実行してレスポンス内容を返却する
	 * 
	 * @param loginResult 認証結果
	 * @return API実行結果
	 * @throws SystemException API通信エラーが発生した場合
	 */
	private WalletResponse fetchWallet(LoginResult loginResult) throws SystemException {
		return redstoneDailyRewardProvider.performWalletApi(loginResult);
	}

	/**
	 * デイリーリワード履歴を登録する
	 * 
	 * @param dailyRewardHistory デイリーリワード履歴
	 */
	private void insertDailyRewardHistory(DailyRewardHistory dailyRewardHistory) {
		redstoneDailyRewardProvider.insertDailyRewardHistory(dailyRewardHistory);
	}

	/**
	 * レッドストーン管理情報を更新する
	 * 
	 * @param rsManagement レッドストーン管理情報
	 */
	private void updateRSManagement(RSManagement rsManagement) {
		redstoneDailyRewardProvider.updateRSManagement(rsManagement);
	}

	/**
	 * デイリーリワードの獲得情報からデイリーリワード履歴を構築する
	 * 
	 * @param reward デイリーリワードの獲得情報
	 * @return デイリーリワード履歴
	 */
	private DailyRewardHistory buildDailyRewardHistory(Reward reward) {
		return DailyRewardHistory.builder()
				.userId(dailyRewardServiceBean.getUserId())
				.targetDate(dailyRewardServiceBean.getBaseDateSql())
				.servicePoint(reward.getServicePoint())
				.isPointsRewardRate2x(reward.isPointsRewardRate2x() == true ? "Y" : "N")
				.deleteFlg(YesNo.NO.getValue())
				.build();
	}

	/**
	 * 残高情報からレッドストーン管理情報を構築する
	 * 
	 * @param wallet 残高情報
	 * @return レッドストーン管理情報
	 */
	private RSManagement buildRSManagement(Wallet wallet) {
		return RSManagement.builder()
				.userId(dailyRewardServiceBean.getUserId())
				.gem(wallet.getGem())
				.servicePoint(wallet.getServicePoint())
				.redsPoint(wallet.getRedsPoint())
				.lastDailyRewardDate(dailyRewardServiceBean.getBaseDateSql())
				.deleteFlg(YesNo.NO.getValue())
				.build();
	}
}
