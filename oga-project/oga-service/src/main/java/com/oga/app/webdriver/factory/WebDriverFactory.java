package com.oga.app.webdriver.factory;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.service.DriverService;

import com.oga.app.common.enums.YesNo;
import com.oga.app.common.prop.OgaProperty;
import com.oga.app.common.utils.StringUtil;

public interface WebDriverFactory<T extends MutableCapabilities> {

	/**
	 * WebDriverを生成する
	 */
	public abstract WebDriver create();

	/**
	 * DriverServiceを生成する
	 */
	public abstract DriverService createDriverService();

	/**
	 * Chromium系オプションを構築する（Edge, Chromeなどで共通化可能）
	 */
	public abstract T buildOptions();

	/**
	 * ヘッドレスモードかどうかを判定する
	 */
	public default boolean isHeadlessModeEnabled() {
		return YesNo.YES.getValue().equals(OgaProperty.getProperty("webdriver.mode.headless", "N"));
	}

	/**
	 * WebDriverの実行ファイルパスを取得する
	 */
	public default String resolveWebDriverPath(String propertyKey) {
		String path = System.getProperty("webdriver");
		return StringUtil.isNullOrEmpty(path) ? OgaProperty.getProperty(propertyKey) : path;
	}

	/**
	 * プロキシ設定を構築する（必要に応じてオプションに適用）
	 */
	default Proxy buildProxy() {
		String proxyHost = OgaProperty.getProperty("webdriver.proxy.host");
		String proxyPort = OgaProperty.getProperty("webdriver.proxy.port");

		if (StringUtil.isNullOrEmpty(proxyHost) || StringUtil.isNullOrEmpty(proxyPort)) {
			return null; // プロキシ未設定
		}

		Proxy proxy = new Proxy();
		proxy.setHttpProxy(proxyHost + ":" + proxyPort);
		proxy.setSslProxy(proxyHost + ":" + proxyPort);
		return proxy;
	}

}
