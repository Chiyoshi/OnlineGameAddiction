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
 * ルーレット処理
 */
public class RedStoneRouletteBusinessLogic extends BaseBusinessLogic<DailyWorkServiceBean> {

	/** ルーレット処理 */
	private static RedStoneRouletteBusinessLogic redStoneRouletteBusinessLogic;

	/** 日次作業ServiceBean */
	private DailyWorkServiceBean dailyWorkServiceBean;

	/** 日次作業情報 */
	private DailyWork dailyWork;

	/** ルーレット画面のURL */
	private String REDSTONE_ROULETTE_URL = null;

	/** ルーレット画面の本日のコイン獲得のボタン1 */
	private String REDSTONE_ROULETTE_TODAY_COINL_BTN1 = null;

	/** ルーレット画面の本日のコイン獲得のボタン2 */
	private String REDSTONE_ROULETTE_TODAY_COINL_BTN2 = null;

	/** ルーレット画面のルーレット開始ボタン1 */
	private String REDSTONE_ROULETTE_START_BTN1 = null;

	/** ルーレット画面のルーレット開始ボタン2 */
	private String REDSTONE_ROULETTE_START_BTN2 = null;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WebDriver
	 */
	public RedStoneRouletteBusinessLogic() {
		// マスタ情報
		MasterDataManager master = MasterDataManager.getInstance();

		REDSTONE_ROULETTE_URL = master.get("webpage.url.redstone.roulette");
		REDSTONE_ROULETTE_TODAY_COINL_BTN1 = master.get("redstone.button.roulette.today.coin1");
		REDSTONE_ROULETTE_TODAY_COINL_BTN2 = master.get("redstone.button.roulette.today.coin2");
		REDSTONE_ROULETTE_START_BTN1 = master.get("redstone.button.roulette.start1");
		REDSTONE_ROULETTE_START_BTN2 = master.get("redstone.button.roulette.start2");
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedStoneRouletteBusinessLogic getInstance() {
		if (redStoneRouletteBusinessLogic == null) {
			redStoneRouletteBusinessLogic = new RedStoneRouletteBusinessLogic();
		}
		return redStoneRouletteBusinessLogic;
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

		// ルーレットのキャンペーン情報を取得する
		List<Campaign> campaignList = CampaignDao.getInstance().findByCampaignDate(
				ServiceType.ROULETTE.getValue(),
				this.dailyWorkServiceBean.getBaseDate());

		// キャンペーン情報が登録されていない場合はエラーとする
		if (campaignList.size() == 0) {
			throw new ApplicationException("ルーレットのキャンペーン情報が登録されていないか、または、対象期間外です。");
		}
		// キャンペーン情報が２つ以上存在する場合は想定外のためエラーとする
		else if (campaignList.size() > 1) {
			throw new ApplicationException("ルーレットの対象期間が重複して登録されています。");
		}

		// 日次作業情報を取得する
		this.dailyWork = DailyWorkDao.getInstance().findByPKey(this.dailyWorkServiceBean.getUserId());

		// データが存在しない場合は想定外のためエラーとする
		if (this.dailyWork == null) {
			throw new SystemException("日次作業情報が取得できません。データ不整合が発生しています。");
		}
	}

	/**
	 * ルーレット処理
	 * <pre>
	 * 以下の業務処理を行う。
	 * ・ルーレットフラグをチェックする
	 * ・日次作業情報結果を取得する
	 * ・WEBドライバーを取得する 
	 * ・ルーレット画面に遷移する
	 * ・当日分のコイン獲得の実施判定をチェックする
	 * ・本日のコインを獲得する
	 * ・日次作業結果情報(コイン獲得)を登録または更新する
	 * ・当日分のルーレットの実施判定をチェックする
	 * ・現在の保有コイン数を取得する
	 * ・ルーレットを開始する
	 * ・日次作業結果情報(ルーレット)を登録または更新する
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {

		LogUtil.info("[ルーレット処理] [START]");

		// ルーレットフラグが「N」の場合は実施しない
		if (YesNo.NO.getValue().equals(this.dailyWork.getRouletteFlg())) {
			LogUtil.info("ルーレットフラグが「N」の場合はスキップします。");
			return;
		}

		// 日次作業結果情報を取得する
		DailyWorkResult dailyWorkResult = DailyWorkResultDao.getInstance().findByPKey(
				dailyWorkServiceBean.getUserId(), dailyWorkServiceBean.getBaseDate(),
				ServiceType.ROULETTE.getValue());

		if (dailyWorkResult != null && Status.SUCCESS.getValue().equals(dailyWorkResult.getStatus())) {
			LogUtil.info("本日のルーレットは実施済みのためスキップします。");
			return;
		}

		// 待機する
		WebDriverManager.getInstance().sleep();

		// WEBドライバーを取得する
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// 画面表示待機
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// 現在のURLがルーレットのURL以外の場合
		if (!driver.getCurrentUrl().equals(REDSTONE_ROULETTE_URL)) {
			// ルーレット画面に遷移する
			driver.navigate().to(REDSTONE_ROULETTE_URL);

			try {
				// 指定したテキストが表示されるまで待機する
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mycoin")));
			} catch (TimeoutException e) {
				throw new ApplicationException("ルーレット画面に遷移できませんでした。");
			}
		}

		// 待機する
		WebDriverManager.getInstance().sleep();

		//////////////////////////////////////////
		// 本日のコイン獲得
		//////////////////////////////////////////
		// 現在の保有コイン数を取得する
		String curentHoldCoin = getTodayCoin();

		//////////////////////////////////////////
		// ルーレット開始
		//////////////////////////////////////////
		// 保有コイン数が0枚の場合はスキップする
		if (curentHoldCoin.equals("0")) {
			LogUtil.info("現在の保有コイン数が0枚のためスキップします。");
			return;
		}

		// ルーレット開始
		roulette(dailyWorkResult);

		// 待機する
		WebDriverManager.getInstance().sleep();

		LogUtil.info("[ルーレット処理] [END]");
	}

	/**
	 * 本日のコインを獲得する
	 * 現在の保有コイン数を返却する
	 * 
	 * @return 保有コイン数 
	 * @throws ApplicationException
	 */
	private String getTodayCoin() throws ApplicationException {

		LogUtil.info("[ルーレット処理] [コイン獲得] [START]");

		// 本日のコインを獲得する
		boolean isClicked = WebDriverManager.getInstance().click("div", "style",
				REDSTONE_ROULETTE_TODAY_COINL_BTN1, REDSTONE_ROULETTE_TODAY_COINL_BTN2);

		if (!isClicked) {
			throw new ApplicationException("本日のコインを獲得することができませんでした。");
		}

		// 現在の保有コイン数を取得する
		String curentHoldCoin = getCurentHoldCoin();

		LogUtil.info("[ルーレット処理] [コイン獲得] [END]");

		return curentHoldCoin;
	}

	/**
	 * 現在の保有コイン数を取得する
	 * 
	 * @return 保有コイン数 
	 */
	private String getCurentHoldCoin() {
		// 現在の保有コイン数
		String curentHoldCoin = "0";

		// WEBドライバーを取得する
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		int index = 0;

		while (true) {
			// divタグの要素リストを取得する
			// 要素リストの再取得時にIndexOutOfBoundsExceptionが発生する可能性があるため取得し直す
			List<WebElement> elementList = driver.findElements(By.tagName("div"));

			if (index > elementList.size() - 1) {
				break;
			}

			// 要素を取得する
			WebElement element = elementList.get(index);

			if (element.getAttribute("class").contains("mycoin")) {
				// divタグ内のspanタグのテキストを取得する
				element = element.findElement(By.tagName("span"));
				curentHoldCoin = element.getText();
			}

			index++;
		}

		LogUtil.info("[ルーレット処理] [コイン獲得] [保有コイン数：" + curentHoldCoin + "]");

		return curentHoldCoin;
	}

	/**
	 * ルーレット開始
	 * 
	 * @param dailyWorkResult
	 * @throws ApplicationException
	 */
	private void roulette(DailyWorkResult dailyWorkResult) throws ApplicationException {

		LogUtil.info("[ルーレット処理] [ルーレット開始] [START]");

		boolean isUpdate = true;

		// 本日分の日次作業情報が存在しない場合は生成する
		if (dailyWorkResult == null) {
			dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(this.dailyWorkServiceBean.getUserId());
			dailyWorkResult.setBaseDate(this.dailyWorkServiceBean.getBaseDate());
			dailyWorkResult.setServiceType(ServiceType.ROULETTE.getValue());
			dailyWorkResult.setDeleteFlg(YesNo.NO.getValue());
			isUpdate = false;
		}

		// WEBドライバーを取得する
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// 画面表示待機
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// ルーレット開始を押下する
			boolean isClicked = WebDriverManager.getInstance().click("div", "style",
					REDSTONE_ROULETTE_START_BTN1, REDSTONE_ROULETTE_START_BTN2);

			if (!isClicked) {
				throw new ApplicationException("ルーレット開始を押下できませんでした。");
			}

			try {
				// 指定したclass要素が表示されるまで待機する
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("item-text")));
			} catch (TimeoutException e) {
				throw new ApplicationException("ルーレット開始を押下できませんでした。");
			}

			int index = 0;

			while (true) {
				// divタグの要素リストを取得する
				// 要素リストの再取得時にIndexOutOfBoundsExceptionが発生する可能性があるため取得し直す
				List<WebElement> elementList = driver.findElements(By.tagName("div"));

				if (index > elementList.size() - 1) {
					break;
				}

				// 要素を取得する
				WebElement element = elementList.get(index);
				if (element.getAttribute("class").contains("item-text")) {
					// // 獲得アイテムを取得する
					String rewardItem = element.getText();
					// 獲得アイテムを設定する
					dailyWorkResult.setRewardItem(rewardItem);

					break;
				}

				index++;
			}

			LogUtil.info("[ルーレット処理] [ルーレット開始] [獲得アイテム：" + dailyWorkResult.getRewardItem() + "]");

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

		LogUtil.info("[ルーレット処理] [ルーレット開始] [END]");
	}
}
