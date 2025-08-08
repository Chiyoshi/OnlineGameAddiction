package com.oga.app.service.provider.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oga.app.common.prop.OgaProperty;

public abstract class SeleniumProviderBase {

	/** WEBドライバー */
	public WebDriver driver = null;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WEBドライバー
	 */
	public SeleniumProviderBase(WebDriver driver) {
		this.driver = driver;
	}

	public SeleniumProviderBase() {
	}

	/**
	 * WEBドライバーを取得する
	 * 
	 * @return WEBドライバー
	 */
	public WebDriver getWebDriver() {
		return this.driver;
	}

	/**
	 * 指定したセレクタを押下する
	 * 
	 * @param selector セレクタ
	 */
	public void click(By selector) {
		driver.findElement(selector).click();
	}

	/**
	 * 指定したセレクタを押下する
	 * 
	 * @param selector セレクタ
	 */
	public void submit(By selector) {
		driver.findElement(selector).submit();
	}

	/**
	 * 指定したセレクタのテキストを取得する
	 * 
	 * @param selector セレクタ
	 * @return テキスト
	 */
	public String getText(By selector) {
		return driver.findElement(selector).getText();
	}

	/**
	 * 閉じる
	 */
	public void close() {
		if (driver != null) {
			driver.close();
		}
	}

	/**
	 * 指定したセレクタが表示されるまで待機する
	 * 
	 * @param selector セレクタ
	 * @param timeout タイムアウト
	 */
	public void waitForElement(By selector) {
		int timeout = Integer.parseInt(OgaProperty.getProperty("webdriver.timeout.pageload", "30"));
		new WebDriverWait(driver, Duration.ofSeconds(timeout))
				.until(ExpectedConditions.visibilityOfElementLocated(selector));
	}
}
