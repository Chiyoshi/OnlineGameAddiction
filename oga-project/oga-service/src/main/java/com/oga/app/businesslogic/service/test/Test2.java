package com.oga.app.businesslogic.service.test;

import java.io.File;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;

public class Test2 {

	public static void main(String[] args) {

		// WebDriverのパスを明示的に指定（バージョン138対応のmsedgedriver.exe）
		EdgeDriverService service = new EdgeDriverService.Builder()
				.usingDriverExecutable(
						new File("C:\\dev\\rs_tools\\OnlineGameAddiction\\bin\\webdriver\\msedgedriver.exe"))
				.usingAnyFreePort()
				.build();

		EdgeOptions edgeOptions = new EdgeOptions();
		// SSL証明書の警告を無視する
		edgeOptions.setAcceptInsecureCerts(true);
		// 起動時にウィンドウサイズを最大化する
		edgeOptions.addArguments("--start-maximized");
		// シークレットモードを有効にする
		edgeOptions.addArguments("-inprivate");

		// プロキシの起動
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		proxy.start(0); // 自動ポート選択

		proxy.setHarCaptureTypes(
				EnumSet.of(
						CaptureType.REQUEST_CONTENT,
						CaptureType.RESPONSE_CONTENT,
						CaptureType.REQUEST_HEADERS,
						CaptureType.RESPONSE_HEADERS,
						CaptureType.REQUEST_COOKIES));

		// HAR（HTTP Archive）を記録開始
		proxy.newHar("example");

		// Seleniumにプロキシを設定
		org.openqa.selenium.Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
		edgeOptions.setProxy(seleniumProxy);

		WebDriver driver = new EdgeDriver(service, edgeOptions);

		// 非同期スレッドでレスポンス取得
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			try {
				Thread.sleep(5000); // ページ読み込み待ち
				proxy.getHar().getLog().getEntries().forEach(entry -> {
					if (entry.getRequest().getUrl().contains("api.redstoneonline")) {
						System.out.println("Request URL: " + entry.getRequest().getUrl());
						System.out.println("PostData: " + entry.getRequest().getPostData());
						System.out.println("Response Status: " + entry.getResponse().getStatus());
						System.out.println("Response Body Size: " + entry.getResponse().getBodySize());
						// レスポンスの本文を取得
						String responseBody = entry.getResponse().getContent().getText();
						System.out.println("レスポンスBody: " + responseBody);

					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		driver.navigate().to("https://www.redstoneonline.jp/signin/1?returnurl=https%3A%2F%2Fwww.redstoneonline.jp%2F");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
				element.sendKeys("chiyoshi004");
				isInputedUserId = true;
			}

			// パスワードを入力する
			if ("パスワード".equals(element.getAttribute("placeholder"))) {
				element.sendKeys("yY15103681@");
				isInputedPassword = true;
			}

			index++;
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// ユーザIDおよびパスワードが入力された場合にログインボタンを押下する
		if (isInputedUserId == true && isInputedPassword == true) {

			// ボタン押下
			driver.findElement(By.tagName("form")).submit();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 終了処理（適切なタイミングで）
		executor.shutdown();
		driver.quit();
		proxy.stop();

	}
}
