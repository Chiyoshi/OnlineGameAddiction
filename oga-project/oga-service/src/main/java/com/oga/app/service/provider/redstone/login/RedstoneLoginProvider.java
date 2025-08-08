package com.oga.app.service.provider.redstone.login;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.oga.app.common.utils.CommonUtil;
import com.oga.app.master.provider.MasterRepositoryProvider;
import com.oga.app.service.provider.base.SeleniumProviderBase;
import com.oga.app.webdriver.EdgeDriverFactory;

public class RedstoneLoginProvider extends SeleniumProviderBase {

	/** RedstoneLoginProvider */
	private static RedstoneLoginProvider redstoneLoginProvider;

	/** MasterRepositoryProvider */
	private MasterRepositoryProvider masterRepositoryProvider;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WEBドライバー
	 */
	public RedstoneLoginProvider() {
		this.masterRepositoryProvider = MasterRepositoryProvider.getInstance();
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedstoneLoginProvider getInstance() {
		if (redstoneLoginProvider == null) {
			redstoneLoginProvider = new RedstoneLoginProvider();
		}
		return redstoneLoginProvider;
	}

	public void browserInitializer() {
		// 既にブラウザが開いている場合は閉じる
		if (this.driver != null) {
			close();
		}

		// ブラウザを起動する
		this.driver = new EdgeDriverFactory().create();
	}

	/**
	 * ログイン処理
	 * 
	 * @param userId ユーザID
	 * @param password パスワード
	 */
	public void performLogin(String userId, String password) {
		// レッドストーンAPI（ログイン画面URL）のURLを取得する
		String url = masterRepositoryProvider.getRedstoneApiUrl("redstone.url.login");

		// ログイン画面を開く
		driver.navigate().to(url);
		// ログインボタンが表示されるまで待機する
		waitForElement(By.xpath("//span[text()='ログイン']"));

		// 待機する
		CommonUtil.sleep(3000);

		// ユーザIDを入力したか否か
		boolean isInputedUserId = false;
		// パスワードを入力したか否か
		boolean isInputedPassword = false;

		int index = 0;

		// 要素リスト取得時にIndexOutOfBoundsExceptionが発生する可能性があるため無限ループで処理する
		while (true) {
			// inputタグの要素リストを取得する
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
			CommonUtil.sleep();
			submit(By.tagName("form"));
		}

		// 待機する
		CommonUtil.sleep();
	}

	/**
	 * ログインチェック
	 * 
	 * @return
	 */
	public boolean isLogged() {
		try {
			// ログアウトボタンが表示されるまで待機して
			// タイムアウトが発生した場合はログインエラーと判断する
			waitForElement(By.xpath("//span[text()='ログアウト']"));
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	/**
	 * ログアウト処理
	 */
	public void logout() {
		// 既にログアウトしている場合は何もしない
		if (!isLogged()) {
			return;
		}

		// JavaScriptを使用して要素をクリック
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// spanタグの要素リストを取得する
		List<WebElement> elementList = driver.findElements(By.tagName("span"));

		for (int i = 0; i < elementList.size(); i++) {
			WebElement element = driver.findElements(By.tagName("span")).get(i);

			if ("ログアウト".equals(element.getText())) {
				// ログアウトボタンを押下する
				js.executeScript("arguments[0].click();", element);
				break;
			}
		}

	}

}
