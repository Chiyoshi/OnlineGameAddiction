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

		// WebDriver�̃p�X�𖾎��I�Ɏw��i�o�[�W����138�Ή���msedgedriver.exe�j
		EdgeDriverService service = new EdgeDriverService.Builder()
				.usingDriverExecutable(
						new File("C:\\dev\\rs_tools\\OnlineGameAddiction\\bin\\webdriver\\msedgedriver.exe"))
				.usingAnyFreePort()
				.build();

		EdgeOptions edgeOptions = new EdgeOptions();
		// SSL�ؖ����̌x���𖳎�����
		edgeOptions.setAcceptInsecureCerts(true);
		// �N�����ɃE�B���h�E�T�C�Y���ő剻����
		edgeOptions.addArguments("--start-maximized");
		// �V�[�N���b�g���[�h��L���ɂ���
		edgeOptions.addArguments("-inprivate");

		WebDriver driver = new EdgeDriver(service, edgeOptions);

		DevTools devTools = ((HasDevTools) driver).getDevTools();
		devTools.createSession();

		// �l�b�g���[�N�Ď���L����
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
//			System.out.println("�� RequestId : " + requestId);
//			if (requestMap.containsKey(requestId)) {
//				executor.submit(() -> {
//					try {
//						// ���X�|���X�擾�����i�񓯊����s�j
//						Network.GetResponseBodyResponse body = devTools.send(Network.getResponseBody(requestId));
//						System.out.println("------------------------------------------");
//						System.out.println("�� RequestId : " + requestId);
//						System.out.println("�� ���X�|���X : " + body.getBody());
//					} catch (Exception e) {
//						System.err.println("�� ���X�|���X�擾���s : " + e.getMessage());
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

		// ���[�UID����͂������ۂ�
		boolean isInputedUserId = false;

		// �p�X���[�h����͂������ۂ�
		boolean isInputedPassword = false;

		int index = 0;

		while (true) {
			// input�^�O�̗v�f���X�g���擾����
			// �v�f���X�g�̍Ď擾����IndexOutOfBoundsException����������\�������邽�ߎ擾������
			List<WebElement> elementList = driver.findElements(By.tagName("input"));

			if (index > elementList.size() - 1) {
				break;
			}

			// �v�f���擾����
			WebElement element = elementList.get(index);

			// ���[�UID����͂���
			if ("username".equals(element.getAttribute("autocomplete"))) {
				element.sendKeys("chiyoshi004");
				isInputedUserId = true;
			}

			// �p�X���[�h����͂���
			if ("�p�X���[�h".equals(element.getAttribute("placeholder"))) {
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
		
		// ���[�UID����уp�X���[�h�����͂��ꂽ�ꍇ�Ƀ��O�C���{�^������������
		if (isInputedUserId == true && isInputedPassword == true) {

			// �{�^������
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
			System.out.println("�� RequestId : " + requestId);
			System.out.println("�� URL : " + request.getUrl());
			System.out.println("�� Method : " + request.getMethod());
			System.out.println("�� PostData : " + request.getPostData().orElse("no data"));

			for (Map.Entry<String, Object> header : request.getHeaders().entrySet()) {
				System.out.println("�� " + header.getKey() + " : " + header.getValue());
			}
			try {
				Network.GetResponseBodyResponse body = devTools.send(Network.getResponseBody(requestId));
				System.out.println("��Response : " + body.getBody());
			} catch(Exception e) {
				System.out.println("��Response : �擾���s");
			}
		}
		System.out.println("------------------------------------------");

		devTools.close();
		driver.quit();
		
		

	}
}
