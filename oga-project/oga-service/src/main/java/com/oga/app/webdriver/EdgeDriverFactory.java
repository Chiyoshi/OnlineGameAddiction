package com.oga.app.webdriver;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;

import com.oga.app.common.enums.YesNo;
import com.oga.app.common.prop.OgaProperty;
import com.oga.app.webdriver.factory.WebDriverFactory;

public class EdgeDriverFactory implements WebDriverFactory {

	@Override
	public WebDriver create() {
		// WebDriverのパスを明示的に指定（バージョン138対応のmsedgedriver.exe）
		EdgeDriverService edgeDriverService = new EdgeDriverService.Builder()
				.usingDriverExecutable(new File(OgaProperty.getProperty("webdriver.service.edge")))
				.usingAnyFreePort()
				.build();

		EdgeOptions edgeOptions = new EdgeOptions();
		// SSL証明書の警告を無視する
		edgeOptions.setAcceptInsecureCerts(true);
		// 起動時にウィンドウを最大化
		edgeOptions.addArguments("--start-maximized");
		// シークレットモード（InPrivateモード）を有効にする
		edgeOptions.addArguments("-inprivate");

		// ヘッドレスモードで起動する
		if (YesNo.YES.getValue().equals(OgaProperty.getProperty("webdriver.mode.headless", "N"))) {
			edgeOptions.addArguments("--headless=new");
		}

		return new EdgeDriver(edgeDriverService, edgeOptions);
	}
}
