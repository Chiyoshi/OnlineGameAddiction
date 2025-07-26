package com.oga.app.service.manager;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oga.app.common.enums.YesNo;
import com.oga.app.common.prop.OGAProperty;
import com.oga.app.common.utils.CommonUtil;
import com.oga.app.common.utils.StringUtil;

public class WebDriverManager {

	/** WebDriverManager */
	private static WebDriverManager manger = null;

	/** WebDriver */
	private WebDriver driver = null;

	/** ヘッドレスモードで起動するか否か(初期値=N) */
	private String WEBDRIVER_MODE_HEADLESS = "N";

	/** ページがロードされるまでの待機時間(初期値=30s) */
	private String WEBDRIVER_TIMEOUT_PAGELOAD = "30";

	/** 待機時間(初期値=1500ms) */
	private int WAIT_TIME = 1500;

	/**
	 * コンストラクタ
	 */
	public WebDriverManager() {
		if (this.driver == null) {

			// プロパティファイルから値を取得する
			String headless = OGAProperty.getProperty("webdriver.mode.headless");
			String timeout = OGAProperty.getProperty("webdriver.timeout.pageload");
			String waitTime = OGAProperty.getProperty("webdriver.sleep.time");

			// ヘッドレスモードで起動するか否か
			WEBDRIVER_MODE_HEADLESS = !StringUtil.isNullOrEmpty(headless) ? headless : WEBDRIVER_MODE_HEADLESS;
			// ページがロードされるまでの待機時間
			WEBDRIVER_TIMEOUT_PAGELOAD = !StringUtil.isNullOrEmpty(timeout) ? timeout : WEBDRIVER_TIMEOUT_PAGELOAD;
			// 待機時間
			WAIT_TIME = !StringUtil.isNullOrEmpty(waitTime) ? Integer.parseInt(waitTime) : WAIT_TIME;

			// 初期処理
			init();

			// 待機する
			CommonUtil.waitTime(5000);
		}
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized WebDriverManager getInstance() {
		if (manger == null) {
			manger = new WebDriverManager();
		}
		return manger;
	}

	/**
	 * WebDriverを取得する
	 * 
	 * @return driver
	 */
	public WebDriver getWebDriver() {
		return this.driver;
	}

	/**
	 * WebDriverを設定する
	 * 
	 * @param driver WEBドライバー
	 */
	public void setWebDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * WEBブラウザを起動する
	 */
	public void init() {
		// ブラウザオプション
		EdgeOptions options = new EdgeOptions();
		
		// SSL証明書の警告を無視する
		options.setAcceptInsecureCerts(true);
		// 起動時にウィンドウを最大化
		options.addArguments("--start-maximized");
		// シークレットモード（InPrivateモード）を有効にする
		options.addArguments("-inprivate");
		// キャッシュを無効化
		options.addArguments("--disk-cache-size=0");

//		options.addArguments("--enable-ipv6");
//		options.addArguments("--host-resolver-rules=\"MAP * ~NOTFOUND, EXCLUDE [::]\"");

		// ヘッドレスモードで起動する
		if (YesNo.YES.getValue().equals(WEBDRIVER_MODE_HEADLESS)) {
//			options.addArguments("--headless");
			options.addArguments("--headless=new");
			//options.addArguments("--window-size=1920,1080");
		}

		// Edgeブラウザを起動する
		this.driver = new EdgeDriver(options);

		int pageLoadTimeout = Integer.parseInt(WEBDRIVER_TIMEOUT_PAGELOAD);

		// ページが完全にロードされるまでい待機する時間を設定する
		this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(pageLoadTimeout));

		// ヘッドレスモード以外の場合は画面サイズを最大かする
		//if (YesNo.NO.getValue().equals(WEBDRIVER_MODE_HEADLESS)) {
		//	driver.manage().window().maximize();
		//}
	}

	/**
	 * URLを開く
	 * 指定したclass属性が表示されるまで待機する
	 * 
	 * @param url URL
	 * @param locator 画面表示の待機条件
	 */
	public void open(String url, By locator) {
		// 画面遷移する
		driver.navigate().to(url);

		// 画面表示待機
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		// 指定したclassが表示されるまで待機する
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * WEBブラウザを閉じる
	 */
	public void close() {
		if (this.driver != null) {
			this.driver.quit();
		}
		manger = null;
	}

	/**
	 * 待機時間
	 */
	public void sleep() {
		CommonUtil.waitTime(WAIT_TIME);
	}

	/**
	 * Javascriptを利用してボタンを押下する
	 * 指定したタグの対象属性に値が存在する場合に押下する
	 * 
	 * @param findElemntTagName タグ
	 * @param findAttributeName 属性 
	 * @param values 値
	 * @return True：押下、False：非押下
	 */
	public boolean click(String findElemntTagName, String findAttributeName, String... values) {
		// ボタン押下したか否か
		boolean isClicked = false;

		// JavaScriptを使用して要素をクリック
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int index = 0;

		while (true) {
			// 指定したタグの要素リストを取得する
			// 要素リストの再取得時にIndexOutOfBoundsExceptionが発生する可能性があるため取得し直す
			List<WebElement> elementList = driver.findElements(By.tagName(findElemntTagName));

			if (index > elementList.size() - 1) {
				break;
			}

			// 要素を取得する
			WebElement element = elementList.get(index);

			for (String value : values) {
				// 指定した属性に値が存在する場合
				if (element.getAttribute(findAttributeName).contains(value)) {
					// ボタンを押下する
					js.executeScript("arguments[0].click();", element);
					isClicked = true;
				}
			}

			if (isClicked) {
				break;
			}

			index++;
		}

		return isClicked;
	}

	public boolean find(String findElemntTagName, String findAttributeName, String adjust, String value) {
		return false;
	}

	public String getText(String findElemntTagName, String findAttributeName, String adjust, String value) {
		return null;
	}

}
