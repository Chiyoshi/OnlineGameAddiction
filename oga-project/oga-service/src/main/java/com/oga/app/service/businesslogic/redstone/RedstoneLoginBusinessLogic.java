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

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.common.utils.StringUtil;
import com.oga.app.service.businesslogic.BaseBusinessLogic;
import com.oga.app.service.manager.MasterDataManager;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.servicebeans.LoginServiceBean;

/**
 * ログイン処理
 */
public class RedstoneLoginBusinessLogic extends BaseBusinessLogic<LoginServiceBean> {

	/** ログイン処理 */
	private static RedstoneLoginBusinessLogic redstoneLoginBusinessLogic;

	/** ログインServiceBean */
	private LoginServiceBean loginServiceBean;

	/** レッドストーンTOP画面のURL */
	private String REDSTONE_TOP_URL = null;

	/** レッドストーンログイン画面のURL */
	private String REDSTONE_LOGIN_URL = null;

	/** レッドストーンのログインキャンペーン画面のURL */
	private String REDSTONE_LOGINCAMPAIGN_URL = null;

	/** レッドストーンのログインキャンペーンへダイレクトログインするログイン画面のURL */
	private String REDSTONE_DIRECTLOGIN_LOGINCAMPAIGN_URL = null;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WebDriver
	 */
	public RedstoneLoginBusinessLogic() {
		// マスタ情報
		MasterDataManager master = MasterDataManager.getInstance();

		REDSTONE_TOP_URL = master.get("webpage.url.redstone");
		REDSTONE_LOGIN_URL = master.get("webpage.url.redstone.login");
		REDSTONE_LOGINCAMPAIGN_URL = master.get("webpage.url.redstone.logincampaign");
		REDSTONE_DIRECTLOGIN_LOGINCAMPAIGN_URL = master.get("webpage.url.redstone.directlogin.logincampaign");
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
	 * (1) 空チェック
	 *     ・ユーザID
	 *     ・パスワード 
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
	 * (1) サービス種別ごとのログイン定義を設定する
	 * (2) WEBドライバーを取得する 
	 * (3) ログイン画面に遷移する
	 * (4) ログイン情報を入力する
	 * (5) ログインボタンを押下する
	 * (6) ログイン後の画面に遷移しない場合は例外を発生させる
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {
		LogUtil.info("[ログイン処理] [START]");

		// サービス種別ごとにURLを設定する
		switch (this.loginServiceBean.getServiceType()) {
		// ログインキャンペーンのダイレクトログイン
		case LOGINCAMPAIGN:
			// ログインURLを設定する
			this.loginServiceBean.setUrl(REDSTONE_DIRECTLOGIN_LOGINCAMPAIGN_URL);
			// ログイン後のURLを設定する
			this.loginServiceBean.setLoginAfterUrl(REDSTONE_LOGINCAMPAIGN_URL);
			// ログイン時の画面表示待機に指定する文字列を設定する
			this.loginServiceBean.setWaitUntilTextDisplayed("//div[text()='注意事項']");
			break;

		// 通常ログイン
		default:
			// ログインURLを設定する
			this.loginServiceBean.setUrl(REDSTONE_LOGIN_URL);
			// ログイン後のURLを設定する
			this.loginServiceBean.setLoginAfterUrl(REDSTONE_TOP_URL);
			// ログイン時の画面表示待機に指定する文字列を設定する
			this.loginServiceBean.setWaitUntilTextDisplayed("//span[text()='ログアウト']");
			break;
		}

		// ログイン処理
		login(this.loginServiceBean.getUrl(), this.loginServiceBean.getUserId(), this.loginServiceBean.getPassword());

		// 待機する
		WebDriverManager.getInstance().sleep();

		LogUtil.info("[ログイン処理] [ログインチェック] [START]");

		// ログインチェック
		if (!isLogged(this.loginServiceBean.getLoginAfterUrl(), this.loginServiceBean.getWaitUntilTextDisplayed())) {
			// 未ログインフラグを設定する
			this.loginServiceBean.setLogged(true);

			throw new ApplicationException("ログイン処理に失敗しました");
		}

		// ログイン済みフラグを設定する
		this.loginServiceBean.setLogged(true);

		LogUtil.info("[ログイン処理] [ログインチェック] [END]");

		//////////////////////////////////////////
		// 規約変更についての同意
		//////////////////////////////////////////
		//		// 待機する
		//		CommonUtil.waitTime(3000);
		//
		//		boolean isAgree = false;
		//		// spanタグの要素リストを取得する
		//		elementList = driver.findElements(By.tagName("span"));
		//
		//		// 取得したアイテム名を取得する
		//		for (int i = 0; i < elementList.size(); i++) {
		//			WebElement element = driver.findElements(By.tagName("span")).get(i);
		//
		//			// 「全て同意します」チェックボックスを押下する
		//			if ("全て同意します。".equals(element.getText())) {
		//				element.click();
		//				isAgree = true;
		//				LogUtil.info("規約変更に同意しました");
		//			}
		//
		//			// 確認ボタンを押下する
		//			if (isAgree && "確認".equals(element.getText())) {
		//				// 「全て同意します」チェックボックスを押下する
		//				JavascriptExecutor js = (JavascriptExecutor) driver;
		//				js.executeScript("arguments[0].click();", element);
		//
		//				// 待機する
		//				CommonUtil.sleep();
		//			}
		//		}

		LogUtil.info("[ログイン処理] [END]");
	}

	/**
	 * ログイン処理
	 * 
	 * @param url ログインURL
	 * @param userId ユーザID
	 * @param password パスワード
	 * @throws ApplicationException
	 */
	private void login(String url, String userId, String password) throws ApplicationException {

		// WEBドライバーを取得する
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// 画面表示待機
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// ログイン画面に遷移する
		driver.navigate().to(url);

		// 指定したテキストが表示されるまで待機する
		By spanLocator = By.xpath("//span[text()='ログイン']");
		wait.until(ExpectedConditions.visibilityOfElementLocated(spanLocator));

		// ユーザIDを入力したか否か
		boolean isInputedUserId = false;

		// パスワードを入力したか否か
		boolean isInputedPassword = false;

		int index = 0;

		while (true) {
			// inputタグの要素リストを取得する
			// 要素リストの再取得時にIndexOutOfBoundsExceptionが発生する可能性があるため取得し直す
			List<WebElement> elementList = driver.findElements(By.tagName("input"));

			if (index > elementList.size() - 1) {
				break;
			}

			// 要素を取得する
			WebElement element = elementList.get(index);

			// ユーザIDを入力する
			if ("username".equals(element.getAttribute("autocomplete"))) {
				element.sendKeys(userId);
				isInputedUserId = true;
			}

			// パスワードを入力する
			if ("パスワード".equals(element.getAttribute("placeholder"))) {
				element.sendKeys(password);
				isInputedPassword = true;
			}

			index++;
		}

		// ユーザIDおよびパスワードが入力された場合にログインボタンを押下する
		if (isInputedUserId == true && isInputedPassword == true) {
			// 待機する
			WebDriverManager.getInstance().sleep();

			// ボタン押下
			driver.findElement(By.tagName("form")).submit();
		}
	}

	/**
	 * ログインチェック
	 * 
	 * @param afterLoginUrl ログイン後のURL
	 * @param waitUntilTextDisplayed 画面表示待機に指定する文字列
	 * @return
	 */
	private boolean isLogged(String afterLoginUrl, String waitUntilTextDisplayed) {

		// WEBドライバーを取得する
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// 画面表示待機
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// 現在のURLとログイン後のURLが一致していない場合は画面遷移する。
		// ログイン後にアップデート情報のページに自動的に遷移するのを防ぐ
		//		if (driver.getCurrentUrl().equals(afterLoginUrl)) {
		//			driver.navigate().to(afterLoginUrl);
		//		}

		try {
			// 指定したテキストが表示されるまで待機する
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitUntilTextDisplayed)));
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}
}
