package com.oga.app.service.businesslogic.redstone;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.Status;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.DateUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.dataaccess.dao.CampaignDao;
import com.oga.app.dataaccess.dao.DailyWorkDao;
import com.oga.app.dataaccess.dao.DailyWorkResultDao;
import com.oga.app.dataaccess.entity.Campaign;
import com.oga.app.dataaccess.entity.DailyWork;
import com.oga.app.dataaccess.entity.DailyWorkResult;
import com.oga.app.service.businesslogic.BaseBusinessLogic;
import com.oga.app.service.manager.MasterDataManager;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.servicebeans.DailyWorkServiceBean;

/**
 * ログインキャンペーン処理
 */
public class RedStoneLoginCampaignBusinessLogic extends BaseBusinessLogic<DailyWorkServiceBean> {

	/** ログインキャンペーン処理 */
	private static RedStoneLoginCampaignBusinessLogic redstoneLoginBusinessLogic;

	/** 日次作業ServiceBean */
	private DailyWorkServiceBean dailyWorkServiceBean;

	/** 対象ログインキャンペーン情報 */
	private Campaign campaign;

	/** 日次作業情報 */
	private DailyWork dailyWork;

	/** ログインキャンペーン画面のマップ1のマス数 */
	private int REDSTONE_LOGINCAMPAIGN_MAP1_NUM = 0;

	/** ログインキャンペーン画面のマップ2のマス数 */
	private int REDSTONE_LOGINCAMPAIGN_MAP2_NUM = 0;

	/** ログインキャンペーン画面のマップ3のマス数 */
	private int REDSTONE_LOGINCAMPAIGN_MAP3_NUM = 0;

	/** ログインキャンペーン画面のURL */
	private String REDSTONE_LOGINCAMPAIGN_URL = null;

	/** ログインキャンペーン画面の通常マスのボタン1 */
	private String REDSTONE_LOGINCAMPAIGN_NORMAL_BTN1 = null;

	/** ログインキャンペーン画面の通常マスのボタン2 */
	private String REDSTONE_LOGINCAMPAIGN_NORMAL_BTN2 = null;

	/** ログインキャンペーン画面のコンプリートマスのボタン1 */
	private String REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN1 = null;

	/** ログインキャンペーン画面のコンプリートマスのボタン2 */
	private String REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN2 = null;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WebDriver
	 */
	public RedStoneLoginCampaignBusinessLogic() {
		// マスタ情報
		MasterDataManager master = MasterDataManager.getInstance();

		REDSTONE_LOGINCAMPAIGN_MAP1_NUM = Integer.parseInt(master.get("redstone.logincampaign.map1.num"));
		REDSTONE_LOGINCAMPAIGN_MAP2_NUM = Integer.parseInt(master.get("redstone.logincampaign.map2.num"));
		REDSTONE_LOGINCAMPAIGN_MAP3_NUM = Integer.parseInt(master.get("redstone.logincampaign.map3.num"));

		REDSTONE_LOGINCAMPAIGN_URL = master.get("webpage.url.redstone.logincampaign");
		REDSTONE_LOGINCAMPAIGN_NORMAL_BTN1 = master.get("redstone.button.logincampaign.click1");
		REDSTONE_LOGINCAMPAIGN_NORMAL_BTN2 = master.get("redstone.button.logincampaign.click2");
		REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN1 = master.get("redstone.button.logincampaign.complete1");
		REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN2 = master.get("redstone.button.logincampaign.complete2");
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedStoneLoginCampaignBusinessLogic getInstance() {
		if (redstoneLoginBusinessLogic == null) {
			redstoneLoginBusinessLogic = new RedStoneLoginCampaignBusinessLogic();
		}
		return redstoneLoginBusinessLogic;
	}

	@Override
	protected void createServiceBean(DailyWorkServiceBean serviceBean) {
		this.dailyWorkServiceBean = serviceBean;

		// 基準日を設定する
		this.dailyWorkServiceBean.setBaseDate(DateUtil.getBaseDate());
	}

	/**
	 * データ取得およびチェック処理
	 * <pre>
	 * 以下のデータ取得およびチェック処理を行う。
	 * ・ログイン状態チェック
	 * ・キャンペーン情報を取得する。
	 * ・キャンペーン情報チェック。
	 * ・キャンペーン情報を設定する。
	 * ・日次作業情報を取得する。
	 * ・日次作業情報の不整合チェック
	 * </pre>
	 * @throws SystemException 
	 */
	@Override
	protected void checkSelectData() throws ApplicationException, SystemException {

		// ログイン状態チェック
		if (!this.dailyWorkServiceBean.isLogged()) {
			throw new ApplicationException("レッドストーンにログインしていないため処理を終了します。");
		}

		// ログインキャンペーンのキャンペーン情報を取得する
		List<Campaign> campaignList = CampaignDao.getInstance().findByCampaignDate(
				ServiceType.LOGINCAMPAIGN.getValue(),
				this.dailyWorkServiceBean.getBaseDate());

		// キャンペーン情報が登録されていない場合はエラーとする
		if (campaignList.size() == 0) {
			throw new ApplicationException("ログインキャンペーンのキャンペーン情報が登録されていないか、または、対象期間外です。");
		}
		// キャンペーン情報が２つ以上存在する場合は想定外のためエラーとする
		else if (campaignList.size() > 1) {
			throw new ApplicationException("ログインキャンペーンの対象期間が重複して登録されています。");
		}

		// キャンペーン情報を設定する
		this.campaign = campaignList.get(0);

		// 日次作業情報を取得する
		this.dailyWork = DailyWorkDao.getInstance().findByPKey(this.dailyWorkServiceBean.getUserId());

		// データが存在しない場合は想定外のためエラーとする
		if (this.dailyWork == null) {
			throw new SystemException("日次作業情報が取得できません。データ不整合が発生しています。");
		}
	}

	/**
	 * ログインキャンペーン処理
	 * <pre>
	 * 以下の業務処理を行う。
	 * ・ログインキャンペーンフラグをチェックする
	 * ・日次作業情報結果を取得する
	 * ・当日分の実施判定をチェックする
	 * ・ログインキャンペーン期間内の実施回数をチェックする
	 * ・WEBドライバーを取得する 
	 * ・ログインキャンペーン画面に遷移する
	 * ・通常マスを押下する
	 * ・日次作業結果情報(通常)を登録または更新する
	 * ・コンプリートマスを押下する
	 * ・日次作業結果情報(コンプリート)を登録または更新する
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {

		LogUtil.info("[ログインキャンペーン処理] [START]");

		// ログインキャンペーンフラグが「N」の場合は実施しない
		if (YesNo.NO.getValue().equals(this.dailyWork.getLoginCampaignFlg())) {
			LogUtil.info("ログインキャンペーンフラグが「N」の場合はスキップします。");
			return;
		}

		// ログインキャンペーン期間内の実施回数を取得する
		int loginCampaignExecCount = DailyWorkResultDao.getInstance().countByTargetDate(
				dailyWorkServiceBean.getUserId(),
				this.campaign.getStartDate(), this.campaign.getEndDate(), ServiceType.LOGINCAMPAIGN.getValue(),
				Status.SUCCESS.getValue());

		// 当月分のログインキャンペーンをすべて実施済みの場合は実施しない
		if (loginCampaignExecCount >= (REDSTONE_LOGINCAMPAIGN_MAP1_NUM + REDSTONE_LOGINCAMPAIGN_MAP2_NUM
				+ REDSTONE_LOGINCAMPAIGN_MAP3_NUM)) {
			DailyWorkResult dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(this.dailyWorkServiceBean.getUserId());
			dailyWorkResult.setBaseDate(this.dailyWorkServiceBean.getBaseDate());
			dailyWorkResult.setServiceType(ServiceType.LOGINCAMPAIGN.getValue());
			dailyWorkResult.setStatus(Status.SKIP.getValue());
			dailyWorkResult.setDeleteFlg(YesNo.NO.getValue());
			DailyWorkResultDao.getInstance().insert(dailyWorkResult);

			LogUtil.info("当月分のログインキャンペーンをすべて実施済みの場合はスキップします。");
			return;
		}

		// 日次作業結果情報を取得する
		DailyWorkResult dailyWorkResult = DailyWorkResultDao.getInstance().findByPKey(
				dailyWorkServiceBean.getUserId(), dailyWorkServiceBean.getBaseDate(),
				ServiceType.LOGINCAMPAIGN.getValue());

		// 既に本日分のログインキャンペーンを実施済みの場合は実施しない
		if (dailyWorkResult != null && Status.SUCCESS.getValue().equals(dailyWorkResult.getStatus())) {
			LogUtil.info("本日のログインキャンペーンは実施済みのためスキップします。");
			return;
		}

		// 待機する
		WebDriverManager.getInstance().sleep();

		// WEBドライバーを取得する
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// 画面表示待機
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// 現在のURLがログインキャンペーンのURL以外の場合
		if (!driver.getCurrentUrl().equals(REDSTONE_LOGINCAMPAIGN_URL)) {
			// ログインキャンペーン画面に遷移する
			driver.navigate().to(REDSTONE_LOGINCAMPAIGN_URL);

			try {
				// 指定したテキストが表示されるまで待機する
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='注意事項']")));
			} catch (TimeoutException e) {
				throw new ApplicationException("ログインキャンペーン画面に遷移できませんでした。");
			}
		}

		//////////////////////////////////////////
		// 通常マス
		//////////////////////////////////////////
		LogUtil.info("[ログインキャンペーン処理] [" + (loginCampaignExecCount + 1) + "日目]");

		// 通常ログインキャンペーン
		loginCampaign(dailyWorkResult);

		//////////////////////////////////////////
		// コンプリートマス
		//////////////////////////////////////////
		// 日次作業結果情報を取得する
		DailyWorkResult completeDailyWorkResult = DailyWorkResultDao.getInstance().findByPKey(
				dailyWorkServiceBean.getUserId(), dailyWorkServiceBean.getBaseDate(),
				ServiceType.LOGINCAMPAIGN_COMPLETE.getValue());

		// コンプリートログインキャンペーン
		completeLoginCampaign(completeDailyWorkResult);

		// 待機する
		WebDriverManager.getInstance().sleep();

		LogUtil.info("[ログインキャンペーン処理] [END]");
	}

	/**
	 * 通常マスのログインキャンペーン
	 * 
	 * @param dailyWorkResult
	 * @throws ApplicationException
	 */
	private void loginCampaign(DailyWorkResult dailyWorkResult) throws ApplicationException {

		LogUtil.info("[ログインキャンペーン処理] [通常マス] [START]");

		boolean isUpdate = true;

		// 本日分の日次作業情報が存在しない場合は生成する
		if (dailyWorkResult == null) {
			dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(this.dailyWorkServiceBean.getUserId());
			dailyWorkResult.setBaseDate(this.dailyWorkServiceBean.getBaseDate());
			dailyWorkResult.setServiceType(ServiceType.LOGINCAMPAIGN.getValue());
			dailyWorkResult.setDeleteFlg(YesNo.NO.getValue());
			isUpdate = false;
		}

		// WEBドライバーを取得する
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// 画面表示待機
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// ログインキャンペーンの通常マスを押下する
			boolean isClicked = WebDriverManager.getInstance().click("div", "style",
					REDSTONE_LOGINCAMPAIGN_NORMAL_BTN1, REDSTONE_LOGINCAMPAIGN_NORMAL_BTN2);

			if (!isClicked) {
				throw new ApplicationException("ログインキャンペーンの通常マスを押下できませんでした。");
			}

			try {
				// 指定したテキストが表示されるまで待機する
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='閉じる']")));
			} catch (TimeoutException e) {
				throw new ApplicationException("ログインキャンペーンの通常マスを押下できませんでした。");
			}

			int index = 0;

			// 獲得アイテムを取得する
			while (true) {
				// tdタグの要素リストを取得する
				// 要素リストの再取得時にIndexOutOfBoundsExceptionが発生する可能性があるため取得し直す
				List<WebElement> elementList = driver.findElements(By.tagName("td"));

				if (index > elementList.size() - 1) {
					break;
				}

				// 要素を取得する
				WebElement element = elementList.get(index);

				if (element.getAttribute("class").contains("imgbox")) {
					String rewardItem = driver.findElements(By.tagName("td")).get(index + 1).getText();

					// 獲得アイテムを設定する
					dailyWorkResult.setRewardItem(rewardItem);

					LogUtil.info("[ログインキャンペーン処理] [通常マス] [獲得アイテム：" + dailyWorkResult.getRewardItem() + "]");
					break;
				}

				index++;
			}

			// 獲得アイテムが取得できなかった場合
			if (dailyWorkResult.getRewardItem() == null) {
				index = 0;

				while (true) {
					// divタグの要素リストを取得する
					// 要素リストの再取得時にIndexOutOfBoundsExceptionが発生する可能性があるため取得し直す
					List<WebElement> elementList = driver.findElements(By.tagName("div"));

					if (index > elementList.size() - 1) {
						break;
					}

					// 要素を取得する
					WebElement element = elementList.get(index);

					if (element.getText().equals("すでに獲得した報酬です。")) {
						LogUtil.info("[ログインキャンペーン処理] [通常マス] [獲得アイテム：既に獲得済み]");
						break;
					}

					index++;
				}
			}

			// 正常パターン
			// ステータスに正常終了を設定する
			dailyWorkResult.setStatus(Status.SUCCESS.getValue());

			if (!isUpdate) {
				DailyWorkResultDao.getInstance().insert(dailyWorkResult);
			} else {
				DailyWorkResultDao.getInstance().update(dailyWorkResult);
			}

		} catch (Exception e) {
			// 異常パターン
			// ステータスに異常終了を設定する
			dailyWorkResult.setStatus(Status.ERROR.getValue());

			if (!isUpdate) {
				DailyWorkResultDao.getInstance().insert(dailyWorkResult);
			} else {
				DailyWorkResultDao.getInstance().update(dailyWorkResult);
			}

			throw e;
		}

		LogUtil.info("[ログインキャンペーン処理] [通常マス] [END]");
	}

	/**
	 * コンプリートマスのログインキャンペーン
	 * 
	 * @param dailyWorkResult
	 * @throws ApplicationException
	 */
	private void completeLoginCampaign(DailyWorkResult dailyWorkResult) throws ApplicationException {

		LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [START]");

		boolean isUpdate = true;

		// 本日分の日次作業情報が存在しない場合は生成する
		if (dailyWorkResult == null) {
			dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(this.dailyWorkServiceBean.getUserId());
			dailyWorkResult.setBaseDate(this.dailyWorkServiceBean.getBaseDate());
			dailyWorkResult.setServiceType(ServiceType.LOGINCAMPAIGN_COMPLETE.getValue());
			dailyWorkResult.setDeleteFlg(YesNo.NO.getValue());
			isUpdate = false;
		}

		// WEBドライバーを取得する
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// 画面表示待機
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// ログインキャンペーンのコンプリートマスを押下する
			boolean isClicked = WebDriverManager.getInstance().click("div", "style",
					REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN1, REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN2);

			if (!isClicked) {
				throw new ApplicationException("ログインキャンペーンのコンプリートマスを押下できませんでした。");
			}

			try {
				// 指定したテキストが表示されるまで待機する
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='閉じる']")));
			} catch (TimeoutException e) {
				throw new ApplicationException("ログインキャンペーンのコンプリートマスを押下できませんでした。");
			}

			// 待機する
			WebDriverManager.getInstance().sleep();

			int index = 0;

			while (true) {
				// spanタグの要素リストを取得する
				// 要素リストの再取得時にIndexOutOfBoundsExceptionが発生する可能性があるため取得し直す
				List<WebElement> elementList = driver.findElements(By.tagName("span"));

				if (index > elementList.size() - 1) {
					break;
				}

				// 要素を取得する
				WebElement element = elementList.get(index);

				if (element.getText().contains("日目まで完了していません。")) {
					LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [未達成]");
					LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [END]");
					return;
				}

				index++;
			}

			// 獲得アイテムを取得する
			// tdタグの要素分繰り返す
			//			for (int i = 0; i < driver.findElements(By.tagName("td")).size(); i++) {
			//				WebElement element = driver.findElements(By.tagName("td")).get(i);
			//
			//				if (element.getAttribute("class").contains("imgbox")) {
			//					String rewardItem = driver.findElements(By.tagName("td")).get(i + 1).getText();
			//					// 獲得アイテムを設定する
			//					dailyWorkResult.setRewardItem(rewardItem);
			//					LogUtil.info("獲得アイテム：" + dailyWorkResult.getRewardItem());
			//					break;
			//				}
			//			}

			// 正常パターン
			// ステータスに正常終了を設定する
			dailyWorkResult.setStatus(Status.SUCCESS.getValue());

			if (!isUpdate) {
				DailyWorkResultDao.getInstance().insert(dailyWorkResult);
			} else {
				DailyWorkResultDao.getInstance().update(dailyWorkResult);
			}

		} catch (Exception e) {
			// 異常パターン
			// ステータスに異常終了を設定する
			dailyWorkResult.setStatus(Status.ERROR.getValue());

			if (!isUpdate) {
				DailyWorkResultDao.getInstance().insert(dailyWorkResult);
			} else {
				DailyWorkResultDao.getInstance().update(dailyWorkResult);
			}

			throw e;
		}

		LogUtil.info("[ログインキャンペーン処理] [コンプリートマス] [END]");
	}
}
