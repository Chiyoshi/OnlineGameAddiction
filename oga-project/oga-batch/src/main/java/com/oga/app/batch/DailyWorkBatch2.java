package com.oga.app.batch;

import java.util.ArrayList;
import java.util.List;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.Status;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.DateUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.dataaccess.dao.UserDao;
import com.oga.app.dataaccess.entity.DailyWork;
import com.oga.app.dataaccess.entity.DailyWorkResult;
import com.oga.app.dataaccess.entity.User;
import com.oga.app.service.businesslogic.redstone.RedStoneLoginCampaignBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedStoneRouletteBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedstoneLoginBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedstoneLogoutBusinessLogic;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.provider.DailyWorkProvider;
import com.oga.app.service.provider.DailyWorkResultProvider;
import com.oga.app.service.provider.UserProvider;
import com.oga.app.service.servicebeans.DailyWorkServiceBean;
import com.oga.app.service.servicebeans.LoginServiceBean;

public class DailyWorkBatch2 extends BaseBatch {

	/** 処理対象 */
	private List<DailyWork> targetDailyWorkList = new ArrayList<DailyWork>();

	/**
	 * コンストラクタ
	 */
	public DailyWorkBatch2() {
	}

	@Override
	public void pre(String[] args) throws ApplicationException, SystemException {

		// 基準日を取得する
		String baseDate = DateUtil.getBaseDate();

		// ユーザ情報を取得する
		List<User> userList = UserProvider.getInstance().getUserList();

		// 日次作業結果
		DailyWorkResult dailyWorkResult = null;

		// 日次作業を実施するか否か
		boolean isExecuted = false;

		// 日次作業を実施済みのユーザは実施しないよう処理対象から除く
		for (User user : userList) {

			// 日次作業情報を取得する
			DailyWork dailyWork = DailyWorkProvider.getInstance().getDailyWork(user.getUserId());

			if (dailyWork == null) {
				throw new SystemException("日次作業情報が取得できません。データ不整合が発生しています。");
			}

			// ログインキャンペーン
			if (YesNo.YES.getValue().equals(dailyWork.getLoginCampaignFlg())) {
				// ログインキャンペーンの日次作業結果を取得する
				dailyWorkResult = DailyWorkResultProvider.getInstance().getDailyWorkResult(dailyWork.getUserId(),
						baseDate, ServiceType.LOGINCAMPAIGN.getValue());

				if (dailyWorkResult == null || Status.ERROR.getValue().equals(dailyWorkResult.getStatus())) {
					isExecuted = true;
				}
			}

			// デイリーリワード
			if (YesNo.YES.getValue().equals(dailyWork.getDailyRewardFlg())) {
				// デイリーリワードの日次作業結果を取得する
				dailyWorkResult = DailyWorkResultProvider.getInstance().getDailyWorkResult(dailyWork.getUserId(),
						baseDate, ServiceType.DAILYREWARD.getValue());

				if (dailyWorkResult == null || Status.ERROR.getValue().equals(dailyWorkResult.getStatus())) {
					//					isExecuted = true;
				}
			}

			// ルーレット
			if (YesNo.YES.getValue().equals(dailyWork.getRouletteFlg())) {
				// ルーレットの日次作業結果を取得する
				dailyWorkResult = DailyWorkResultProvider.getInstance().getDailyWorkResult(dailyWork.getUserId(),
						baseDate, ServiceType.ROULETTE.getValue());

				if (dailyWorkResult == null || Status.ERROR.getValue().equals(dailyWorkResult.getStatus())) {
					isExecuted = true;
				}
			}

			// 処理対象リストに追加する
			if (isExecuted) {
				this.targetDailyWorkList.add(dailyWork);
			}

			// 初期化
			dailyWorkResult = null;
			isExecuted = false;
		}

		LogUtil.info("[処理対象件数：" + this.targetDailyWorkList.size() + "]");
	}

	@Override
	public void exec() throws ApplicationException, SystemException {

		for (DailyWork dailyWork : this.targetDailyWorkList) {

			// ログ出力用のユーザIDを設定する
			LogUtil.setPropertyUserId(dailyWork.getUserId());

			//////////////////////////////////////////////////////
			// ログイン処理
			//////////////////////////////////////////////////////
			// ユーザ情報を取得する
			User user = UserDao.getInstance().findByPKey(dailyWork.getUserId());

			LoginServiceBean loginServiceBean = new LoginServiceBean();
			loginServiceBean.setUserId(user.getUserId());
			loginServiceBean.setPassword(user.getPassword());
			loginServiceBean.setServiceType(ServiceType.NORMAL_LOGIN);

			try {
				// ビジネスロジックを呼び出す
				RedstoneLoginBusinessLogic.getInstance().execute(loginServiceBean);
			} catch (Exception e) {
				LogUtil.error(e.getMessage(), e);

				try {
					// ログイン済みの場合はあるため念のためログアウトしておく
					// ビジネスロジックを呼び出す
					RedstoneLogoutBusinessLogic.getInstance().execute(loginServiceBean);
				} catch (Exception ex) {
					LogUtil.error(ex.getMessage(), ex);
				}

				continue;
			}

			//////////////////////////////////////////////////////
			// ログインキャンペーン
			//////////////////////////////////////////////////////
			if (YesNo.YES.getValue().equals(dailyWork.getLoginCampaignFlg())) {
				DailyWorkServiceBean dailyWorkServiceBean = new DailyWorkServiceBean();
				dailyWorkServiceBean.setUserId(user.getUserId());
				dailyWorkServiceBean.setLogged(loginServiceBean.isLogged());

				try {
					// ビジネスロジックを呼び出す
					RedStoneLoginCampaignBusinessLogic.getInstance().execute(dailyWorkServiceBean);
				} catch (Exception e) {
					LogUtil.error(e.getMessage(), e);
				}
			}

			//////////////////////////////////////////////////////
			// デイリーリワード
			//////////////////////////////////////////////////////
			// 未実装

			//////////////////////////////////////////////////////
			// ルーレット
			//////////////////////////////////////////////////////
			if (YesNo.YES.getValue().equals(dailyWork.getRouletteFlg())) {
				DailyWorkServiceBean dailyWorkServiceBean = new DailyWorkServiceBean();
				dailyWorkServiceBean.setUserId(user.getUserId());
				dailyWorkServiceBean.setLogged(loginServiceBean.isLogged());

				try {
					// ビジネスロジックを呼び出す
					RedStoneRouletteBusinessLogic.getInstance().execute(dailyWorkServiceBean);
				} catch (Exception e) {
					LogUtil.error(e.getMessage(), e);
				}
			}

			//////////////////////////////////////////////////////
			// ログアウト処理
			//////////////////////////////////////////////////////
			try {
				// ビジネスロジックを呼び出す
				RedstoneLogoutBusinessLogic.getInstance().execute(loginServiceBean);
			} catch (Exception e) {
				LogUtil.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void post() throws ApplicationException, SystemException {
		// WEBブラウザを閉じる
		WebDriverManager.getInstance().close();
	}

	/**
	 * メイン処理
	 */
	public static void main(String[] args) {
		new DailyWorkBatch2().run(args);
	}
}
