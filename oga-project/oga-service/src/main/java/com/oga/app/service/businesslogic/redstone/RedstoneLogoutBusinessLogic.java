package com.oga.app.service.businesslogic.redstone;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.service.businesslogic.BaseBusinessLogic;
import com.oga.app.service.manager.MasterDataManager;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.servicebeans.LoginServiceBean;

/**
 * ログアウト処理
 */
public class RedstoneLogoutBusinessLogic extends BaseBusinessLogic<LoginServiceBean> {

	/** ログアウト処理 */
	private static RedstoneLogoutBusinessLogic redstoneLogoutBusinessLogic;

	/** ServiceBean */
	private LoginServiceBean loginServiceBean;

	/** レッドストーンTOP画面のURL */
	private String REDSTONE_TOP_URL = null;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WebDriver
	 */
	public RedstoneLogoutBusinessLogic() {
		// マスタ情報
		MasterDataManager master = MasterDataManager.getInstance();

		REDSTONE_TOP_URL = master.get("webpage.url.redstone");
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedstoneLogoutBusinessLogic getInstance() {
		if (redstoneLogoutBusinessLogic == null) {
			redstoneLogoutBusinessLogic = new RedstoneLogoutBusinessLogic();
		}
		return redstoneLogoutBusinessLogic;
	}

	@Override
	protected void createServiceBean(LoginServiceBean serviceBean) {
		this.loginServiceBean = serviceBean;
	}

	/**
	 * データ取得およびチェック処理
	 * <pre>
	 * 何もしない
	 * </pre>
	 */
	@Override
	protected void checkSelectData() throws ApplicationException {
		// 何もしない
	}

	/**
	 * ログアウト処理
	 * <pre>
	 * (1) WEBドライバーを取得する。 
	 * (2) ログインチェックする。
	 * (3) ログアウトボタンを押下する。
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {

		LogUtil.info("[ログアウト処理] [START]");

		// ログアウト済みの場合はスキップする
		if (!this.loginServiceBean.isLogged()) {
			LogUtil.info("既にログアウト済みのためスキップします");
			return;
		}

		// WEBドライバーを取得する
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// 画面表示待機
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// トップページ画面に遷移する
		driver.navigate().to(REDSTONE_TOP_URL);

		try {
			// 指定したテキストが表示されるまで待機する
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='ログアウト']")));
		} catch (TimeoutException e) {
			e.printStackTrace();
			LogUtil.info("既にログアウト済みのためスキップします");
			return;
		}

		try {
			// JavaScriptを使用して要素をクリック
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// inputタグの要素リストを取得する
			List<WebElement> elementList = driver.findElements(By.tagName("span"));

			for (int i = 0; i < elementList.size(); i++) {
				WebElement element = driver.findElements(By.tagName("span")).get(i);

				if ("ログアウト".equals(element.getText())) {
					// ログアウトボタンを押下する
					js.executeScript("arguments[0].click();", element);
					break;
				}
			}

		} catch (Exception e) {
			throw new ApplicationException("ログアウト処理に失敗しました", e);
		}

		LogUtil.info("[ログアウト処理] [END]");
	}
}
