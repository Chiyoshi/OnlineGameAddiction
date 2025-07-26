package com.oga.app.batch;

import java.util.ArrayList;
import java.util.List;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.prop.OGAProperty;
import com.oga.app.common.utils.DateUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.dataaccess.dao.UserDao;
import com.oga.app.dataaccess.entity.DailyWork;
import com.oga.app.dataaccess.entity.User;
import com.oga.app.service.businesslogic.redstone.RedStoneLoginCampaignBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedStoneRouletteBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedstoneLoginBusinessLogic;
import com.oga.app.service.businesslogic.redstone.RedstoneLogoutBusinessLogic;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.provider.DailyWorkProvider;
import com.oga.app.service.servicebeans.DailyWorkServiceBean;
import com.oga.app.service.servicebeans.LoginServiceBean;

public class DailyWorkBatch extends BaseBatch {

	/** 処理対象 */
	private List<DailyWork> targetDailyWorkList = new ArrayList<DailyWork>();

	/** ログインエラーを許容する件数(初期値：2) */
	private int LOGIN_ERROR_ALLOW_COUNT = 2;

	/**
	 * コンストラクタ
	 */
	public DailyWorkBatch() {
	}

	@Override
	public void pre(String[] args) throws ApplicationException, SystemException {

		// ログインエラーを許容する件数を設定する
		this.LOGIN_ERROR_ALLOW_COUNT = Integer.parseInt(OGAProperty.getProperty("login.error.allow.count"));
		
		// 基準日を取得する
		String baseDate = DateUtil.getBaseDate();

		// 日次作業の対象者を取得する
		this.targetDailyWorkList = DailyWorkProvider.getInstance().getTargetDailyWorklList(baseDate);

		LogUtil.info("[処理対象件数：" + this.targetDailyWorkList.size() + "]");
	}

	@Override
	public void exec() throws ApplicationException, SystemException {

		// ログインエラー件数
		int loginErrorCount = 0;
		
		for (DailyWork dailyWork : this.targetDailyWorkList) {

			// ログインエラーが連続した場合は一旦ブラウザを閉じてキャッシュをクリアする
			if (this.LOGIN_ERROR_ALLOW_COUNT <= loginErrorCount) {
				WebDriverManager.getInstance().close();

				// ログインエラー件数を初期化する
				loginErrorCount = 0;
			}

			// WebDriverManagerを生成してブラウザを開く
			WebDriverManager.getInstance();

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

				//try {
				//	// ログイン済みの場合は念のためログアウトしておく
				//	// ビジネスロジックを呼び出す
				//	RedstoneLogoutBusinessLogic.getInstance().execute(loginServiceBean);
				//} catch (Exception ex) {
				//	LogUtil.error(ex.getMessage(), ex);
				//}

				loginErrorCount++;

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

			// ログインエラー件数を初期化する
			loginErrorCount = 0;
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
		new DailyWorkBatch().run(args);
	}
}
