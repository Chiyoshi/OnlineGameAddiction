package com.oga.app.batch;

import java.util.ArrayList;
import java.util.List;

import com.oga.app.batch.base.BatchBase;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.prop.OgaProperty;
import com.oga.app.common.utils.DateUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.dataaccess.entity.LoginCampaignDetails;
import com.oga.app.dataaccess.entity.RSManagement;
import com.oga.app.dataaccess.entity.User;
import com.oga.app.service.beans.DailyRewardServiceBean;
import com.oga.app.service.beans.LoginCampaignServiceBean;
import com.oga.app.service.beans.LoginServiceBean;
import com.oga.app.service.businesslogic.redstone.campaign.RedStoneLoginCampaignBusinessLogic;
import com.oga.app.service.businesslogic.redstone.daily.RedStoneDailyRewardBusinessLogic;
import com.oga.app.service.businesslogic.redstone.login.RedstoneLoginBusinessLogic;
import com.oga.app.service.businesslogic.redstone.login.RedstoneLogoutBusinessLogic;
import com.oga.app.service.provider.redstone.campaign.RedstoneLoginCampaignProvider;
import com.oga.app.service.provider.redstone.login.RedstoneLoginProvider;

public class RedstoneDailyBatch extends BatchBase {

	/** 処理件数の上限 */
	private int SUCCESS_LIMIT_COUNT;

	/** エラー件数の上限 */
	private int ERROR_LIMIT_COUNT;

	/** 処理対象 */
	private List<RSManagement> targetList = new ArrayList<RSManagement>();

	/**
	 * コンストラクタ
	 */
	public RedstoneDailyBatch() {
	}

	@Override
	public void pre(String[] args) throws ApplicationException, SystemException {

		SUCCESS_LIMIT_COUNT = Integer.parseInt(OgaProperty.getProperty("success.limit.count", "100"));
		ERROR_LIMIT_COUNT = Integer.parseInt(OgaProperty.getProperty("error.limit.count", "100"));

		List<RSManagement> rsManagementList = RedstoneLoginCampaignProvider.getInstance().fetchRSManagementList();

		String baseDateSql = DateUtil.parseDateStr(DateUtil.getBaseDate(), DateUtil.DATE_FORMAT_YYYYMMDD_2);

		for (RSManagement rsManagement : rsManagementList) {
			if (shouldProcess(rsManagement, baseDateSql)) {
				targetList.add(rsManagement);
			}
		}

		LogUtil.info("[処理対象件数：" + targetList.size() + "]");
	}

	@Override
	public void exec() throws ApplicationException, SystemException {

		int totalErrorCount = 0;

		boolean isBrowserInitialized = false;

		for (int i = 0; i < targetList.size(); i++) {

			RSManagement rsManagement = targetList.get(i);

			// ログ出力用のユーザIDを設定する
			LogUtil.setPropertyUserId(rsManagement.getUserId());

			//////////////////////////////////////////////////////
			// ログイン処理
			//////////////////////////////////////////////////////
			User user = RedstoneLoginCampaignProvider.getInstance().fetchUserInfo(rsManagement.getUserId());

			LoginServiceBean loginServiceBean = null;

			boolean success = false;

			while (!success) {
				loginServiceBean = createLoginServiceBean(user, isBrowserInitialized);

				if (!login(loginServiceBean)) {
					// ログイン認証失敗時はブラウザを再起動するため初期化フラグをfalseに設定する
					isBrowserInitialized = false;
					// エラー件数を加算する
					totalErrorCount++;

					if (totalErrorCount >= ERROR_LIMIT_COUNT) {
						LogUtil.error("エラー件数が上限に達したため強制終了します");
						return;
					}
				} else {
					// ログイン認証が成功したためループから抜ける
					success = true;
				}
			}

			isBrowserInitialized = true;

			//////////////////////////////////////////////////////
			// デイリーリワード
			//////////////////////////////////////////////////////
			if (YesNo.YES.getValue().equals(rsManagement.getDailyRewardFlg())) {
				executeDailyReward(loginServiceBean);
			}

			//////////////////////////////////////////////////////
			// ログインキャンペーン
			//////////////////////////////////////////////////////
			if (YesNo.YES.getValue().equals(rsManagement.getLoginCampaignFlg())) {
				executeLoginCampaign(loginServiceBean);
			}

			//////////////////////////////////////////////////////
			// ルーレット
			//////////////////////////////////////////////////////
			if (YesNo.YES.getValue().equals(rsManagement.getRouletteFlg())) {
				executeRoulette(loginServiceBean);
			}

			//////////////////////////////////////////////////////
			// ログアウト処理
			//////////////////////////////////////////////////////
			logout(loginServiceBean);

			if (i >= SUCCESS_LIMIT_COUNT) {
				LogUtil.info("処理件数が上限に達したため処理終了します");
				return;
			}
		}
	}

	@Override
	public void post() throws ApplicationException, SystemException {
		// WEBブラウザを閉じる
		RedstoneLoginProvider.getInstance().close();
	}

	/**
	 * 処理対象か否か判定する
	 * ・デイリーリワード
	 * ・ログインキャンペーン
	 * ・ルーレット
	 * 
	 * @param rsManagement レッドストーン管理情報
	 * @param baseDateSql 基準日(yyyy-MM-dd形式)
	 * @return
	 */
	private boolean shouldProcess(RSManagement rsManagement, String baseDateSql) {
		if (YesNo.YES.getValue().equals(rsManagement.getDailyRewardFlg())
				&& !baseDateSql.equals(rsManagement.getLastDailyRewardDate())) {
			return true;
		}

		if (YesNo.YES.getValue().equals(rsManagement.getLoginCampaignFlg())
				&& !baseDateSql.equals(rsManagement.getLastLoginCampaignDate())) {
			LoginCampaignDetails details = RedstoneLoginCampaignProvider.getInstance()
					.fetchLoginCampaignDetails(rsManagement.getUserId(), DateUtil.getBaseMonth());

			if (details == null || Integer.parseInt(details.getStage()) < 21) {
				return true;
			}
		}

		if (YesNo.YES.getValue().equals(rsManagement.getRouletteFlg())
				&& !baseDateSql.equals(rsManagement.getLastRouletteDate())) {
			return true;
		}
		return false;
	}

	/**
	 * LoginServiceBeanを構築する
	 * 
	 * @param user ユーザ情報
	 * @param browserInitialized ブラウザ初期化済みか否か
	 * @return LoginServiceBean
	 */
	private LoginServiceBean createLoginServiceBean(User user, boolean browserInitialized) {
		return LoginServiceBean.builder()
				.userId(user.getUserId())
				.password(user.getPassword())
				.isBrowserInitialized(browserInitialized)
				.build();
	}

	/**
	 * ログイン処理のビジネスロジックを呼び出す
	 * 
	 * @param loginServiceBean
	 * @return ログイン認証成功時はTrue、失敗時はFalseを返却する
	 */
	private boolean login(LoginServiceBean loginServiceBean) {
		try {
			RedstoneLoginBusinessLogic.getInstance().execute(loginServiceBean);
			return true;
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
			try {
				RedstoneLogoutBusinessLogic.getInstance().execute(loginServiceBean);
			} catch (Exception ex) {
				LogUtil.error(ex.getMessage(), ex);
			}
			return false;
		}
	}

	/**
	 * デイリーリワード処理のビジネスロジックを呼び出す
	 * 
	 * @param loginServiceBean
	 */
	private void executeDailyReward(LoginServiceBean loginServiceBean) {
		DailyRewardServiceBean dailyRewardServiceBeann = DailyRewardServiceBean.builder()
				.userId(loginServiceBean.getUserId())
				.isLogged(loginServiceBean.isLogged())
				.loginResult(loginServiceBean.getLoginResult())
				.build();

		try {
			RedStoneDailyRewardBusinessLogic.getInstance().execute(dailyRewardServiceBeann);
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
		}
	}

	/**
	 * ログインキャンペーン処理のビジネスロジックを呼び出す
	 * 
	 * @param loginServiceBean
	 */
	private void executeLoginCampaign(LoginServiceBean loginServiceBean) {
		LoginCampaignServiceBean loginCampaignServiceBean = LoginCampaignServiceBean.builder()
				.userId(loginServiceBean.getUserId())
				.isLogged(loginServiceBean.isLogged())
				.loginResult(loginServiceBean.getLoginResult())
				.build();

		try {
			RedStoneLoginCampaignBusinessLogic.getInstance().execute(loginCampaignServiceBean);
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
		}
	}

	/**
	 * ルーレット処理のビジネスロジックを呼び出す
	 * 
	 * @param loginServiceBean
	 */
	private void executeRoulette(LoginServiceBean loginServiceBean) {
	}

	/**
	 * ログアウト処理のビジネスロジックを呼び出す
	 * 
	 */
	private void logout(LoginServiceBean loginServiceBean) {
		try {
			RedstoneLogoutBusinessLogic.getInstance().execute(loginServiceBean);
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
		}
	}

	/**
	 * メイン処理
	 */
	public static void main(String[] args) {
		new RedstoneDailyBatch().run(args);
	}
}
