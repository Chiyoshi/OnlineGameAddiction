package com.oga.app.webdriver;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.service.DriverService;

import com.oga.app.webdriver.factory.WebDriverFactory;

public class EdgeDriverFactory implements WebDriverFactory<EdgeOptions> {

	private static final String DRIVER_PROPERTY_KEY = "webdriver.service.edge";

	@Override
	public WebDriver create() {
		EdgeOptions options = (EdgeOptions) buildOptions();
		return new EdgeDriver((EdgeDriverService) createDriverService(), options);
	}

	/**
	 * WebDriverのパスを明示的に指定（バージョン138対応のmsedgedriver.exe）
	 */
	@Override
	public DriverService createDriverService() {
		return new EdgeDriverService.Builder()
				.usingDriverExecutable(new File(resolveWebDriverPath(DRIVER_PROPERTY_KEY)))
				.usingAnyFreePort()
				.build();
	}

	@Override
	public EdgeOptions buildOptions() {
		EdgeOptions options = new EdgeOptions();
		// SSL証明書の警告を無視する
		options.setAcceptInsecureCerts(true);
		// 起動時にウィンドウを最大化
		options.addArguments("--start-maximized");
		// シークレットモード（InPrivateモード）を有効にする
		options.addArguments("-inprivate");

		if (isHeadlessModeEnabled()) {
			options.addArguments("--headless=new");
		}
		return options;
	}
}
