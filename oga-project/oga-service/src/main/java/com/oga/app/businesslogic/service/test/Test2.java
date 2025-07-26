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

		// �v���L�V�̋N��
		BrowserMobProxy proxy = new BrowserMobProxyServer();
		proxy.start(0); // �����|�[�g�I��

		proxy.setHarCaptureTypes(
				EnumSet.of(
						CaptureType.REQUEST_CONTENT,
						CaptureType.RESPONSE_CONTENT,
						CaptureType.REQUEST_HEADERS,
						CaptureType.RESPONSE_HEADERS,
						CaptureType.REQUEST_COOKIES));

		// HAR�iHTTP Archive�j���L�^�J�n
		proxy.newHar("example");

		// Selenium�Ƀv���L�V��ݒ�
		org.openqa.selenium.Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
		edgeOptions.setProxy(seleniumProxy);

		WebDriver driver = new EdgeDriver(service, edgeOptions);

		// �񓯊��X���b�h�Ń��X�|���X�擾
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			try {
				Thread.sleep(5000); // �y�[�W�ǂݍ��ݑ҂�
				proxy.getHar().getLog().getEntries().forEach(entry -> {
					if (entry.getRequest().getUrl().contains("api.redstoneonline")) {
						System.out.println("Request URL: " + entry.getRequest().getUrl());
						System.out.println("PostData: " + entry.getRequest().getPostData());
						System.out.println("Response Status: " + entry.getResponse().getStatus());
						System.out.println("Response Body Size: " + entry.getResponse().getBodySize());
						// ���X�|���X�̖{�����擾
						String responseBody = entry.getResponse().getContent().getText();
						System.out.println("���X�|���XBody: " + responseBody);

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

		// �I�������i�K�؂ȃ^�C�~���O�Łj
		executor.shutdown();
		driver.quit();
		proxy.stop();

	}
}
