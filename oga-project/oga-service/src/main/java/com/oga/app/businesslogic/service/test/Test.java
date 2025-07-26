package com.oga.app.businesslogic.service.test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v138.network.Network;
import org.openqa.selenium.devtools.v138.network.model.Request;
import org.openqa.selenium.devtools.v138.network.model.RequestId;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;

public class Test {

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

		WebDriver driver = new EdgeDriver(service, edgeOptions);

		DevTools devTools = ((HasDevTools) driver).getDevTools();
		devTools.createSession();

		// ネットワーク監視を有効化
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.clearBrowserCache());
		devTools.send(Network.setCacheDisabled(true));


		Map<RequestId, Request> requestMap = new LinkedHashMap<RequestId, Request>();

		devTools.addListener(Network.requestWillBeSent(), event -> {
			Request request = event.getRequest();
			if (request.getUrl().contains("https://api.redstoneonline.jp/mbs/authenticate")) {
				if (!request.getMethod().equals("OPTIONS")) {
					requestMap.put(event.getRequestId(), request);
				}
			}
		});

//		ExecutorService executor = Executors.newCachedThreadPool();
//		
//		devTools.addListener(Network.responseReceived(), responseReceived -> {
//			RequestId requestId = responseReceived.getRequestId();
//			System.out.println("● RequestId : " + requestId);
//			if (requestMap.containsKey(requestId)) {
//				executor.submit(() -> {
//					try {
//						// レスポンス取得処理（非同期実行）
//						Network.GetResponseBodyResponse body = devTools.send(Network.getResponseBody(requestId));
//						System.out.println("------------------------------------------");
//						System.out.println("● RequestId : " + requestId);
//						System.out.println("● レスポンス : " + body.getBody());
//					} catch (Exception e) {
//						System.err.println("● レスポンス取得失敗 : " + e.getMessage());
//					}
//				});
//			}
//
//		});

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

		for (Entry<RequestId, Request> entry : requestMap.entrySet()) {
			RequestId requestId = entry.getKey();
			Request request = entry.getValue();

			System.out.println("------------------------------------------");
			System.out.println("■ RequestId : " + requestId);
			System.out.println("■ URL : " + request.getUrl());
			System.out.println("■ Method : " + request.getMethod());
			System.out.println("■ PostData : " + request.getPostData().orElse("no data"));

			for (Map.Entry<String, Object> header : request.getHeaders().entrySet()) {
				System.out.println("■ " + header.getKey() + " : " + header.getValue());
			}
			try {
				Network.GetResponseBodyResponse body = devTools.send(Network.getResponseBody(requestId));
				System.out.println("■Response : " + body.getBody());
			} catch(Exception e) {
				System.out.println("■Response : 取得失敗");
			}
		}
		System.out.println("------------------------------------------");

		devTools.close();
		driver.quit();
		
		

	}
}
