package com.oga.app.service.manager;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oga.app.common.enums.YesNo;
import com.oga.app.common.prop.OGAProperty;
import com.oga.app.common.utils.CommonUtil;
import com.oga.app.common.utils.StringUtil;

public class WebDriverManager {

	/** WebDriverManager */
	private static WebDriverManager manger = null;

	/** WebDriver */
	private WebDriver driver = null;

	/** �w�b�h���X���[�h�ŋN�����邩�ۂ�(�����l=N) */
	private String WEBDRIVER_MODE_HEADLESS = "N";

	/** �y�[�W�����[�h�����܂ł̑ҋ@����(�����l=30s) */
	private String WEBDRIVER_TIMEOUT_PAGELOAD = "30";

	/** �ҋ@����(�����l=1500ms) */
	private int WAIT_TIME = 1500;

	/**
	 * �R���X�g���N�^
	 */
	public WebDriverManager() {
		if (this.driver == null) {

			// �v���p�e�B�t�@�C������l���擾����
			String headless = OGAProperty.getProperty("webdriver.mode.headless");
			String timeout = OGAProperty.getProperty("webdriver.timeout.pageload");
			String waitTime = OGAProperty.getProperty("webdriver.sleep.time");

			// �w�b�h���X���[�h�ŋN�����邩�ۂ�
			WEBDRIVER_MODE_HEADLESS = !StringUtil.isNullOrEmpty(headless) ? headless : WEBDRIVER_MODE_HEADLESS;
			// �y�[�W�����[�h�����܂ł̑ҋ@����
			WEBDRIVER_TIMEOUT_PAGELOAD = !StringUtil.isNullOrEmpty(timeout) ? timeout : WEBDRIVER_TIMEOUT_PAGELOAD;
			// �ҋ@����
			WAIT_TIME = !StringUtil.isNullOrEmpty(waitTime) ? Integer.parseInt(waitTime) : WAIT_TIME;

			// ��������
			init();

			// �ҋ@����
			CommonUtil.waitTime(5000);
		}
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized WebDriverManager getInstance() {
		if (manger == null) {
			manger = new WebDriverManager();
		}
		return manger;
	}

	/**
	 * WebDriver���擾����
	 * 
	 * @return driver
	 */
	public WebDriver getWebDriver() {
		return this.driver;
	}

	/**
	 * WebDriver��ݒ肷��
	 * 
	 * @param driver WEB�h���C�o�[
	 */
	public void setWebDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * WEB�u���E�U���N������
	 */
	public void init() {
		// �u���E�U�I�v�V����
		EdgeOptions options = new EdgeOptions();
		
		// SSL�ؖ����̌x���𖳎�����
		options.setAcceptInsecureCerts(true);
		// �N�����ɃE�B���h�E���ő剻
		options.addArguments("--start-maximized");
		// �V�[�N���b�g���[�h�iInPrivate���[�h�j��L���ɂ���
		options.addArguments("-inprivate");
		// �L���b�V���𖳌���
		options.addArguments("--disk-cache-size=0");

//		options.addArguments("--enable-ipv6");
//		options.addArguments("--host-resolver-rules=\"MAP * ~NOTFOUND, EXCLUDE [::]\"");

		// �w�b�h���X���[�h�ŋN������
		if (YesNo.YES.getValue().equals(WEBDRIVER_MODE_HEADLESS)) {
//			options.addArguments("--headless");
			options.addArguments("--headless=new");
			//options.addArguments("--window-size=1920,1080");
		}

		// Edge�u���E�U���N������
		this.driver = new EdgeDriver(options);

		int pageLoadTimeout = Integer.parseInt(WEBDRIVER_TIMEOUT_PAGELOAD);

		// �y�[�W�����S�Ƀ��[�h�����܂ł��ҋ@���鎞�Ԃ�ݒ肷��
		this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(pageLoadTimeout));

		// �w�b�h���X���[�h�ȊO�̏ꍇ�͉�ʃT�C�Y���ő傩����
		//if (YesNo.NO.getValue().equals(WEBDRIVER_MODE_HEADLESS)) {
		//	driver.manage().window().maximize();
		//}
	}

	/**
	 * URL���J��
	 * �w�肵��class�������\�������܂őҋ@����
	 * 
	 * @param url URL
	 * @param locator ��ʕ\���̑ҋ@����
	 */
	public void open(String url, By locator) {
		// ��ʑJ�ڂ���
		driver.navigate().to(url);

		// ��ʕ\���ҋ@
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		// �w�肵��class���\�������܂őҋ@����
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * WEB�u���E�U�����
	 */
	public void close() {
		if (this.driver != null) {
			this.driver.quit();
		}
		manger = null;
	}

	/**
	 * �ҋ@����
	 */
	public void sleep() {
		CommonUtil.waitTime(WAIT_TIME);
	}

	/**
	 * Javascript�𗘗p���ă{�^������������
	 * �w�肵���^�O�̑Ώۑ����ɒl�����݂���ꍇ�ɉ�������
	 * 
	 * @param findElemntTagName �^�O
	 * @param findAttributeName ���� 
	 * @param values �l
	 * @return True�F�����AFalse�F�񉟉�
	 */
	public boolean click(String findElemntTagName, String findAttributeName, String... values) {
		// �{�^�������������ۂ�
		boolean isClicked = false;

		// JavaScript���g�p���ėv�f���N���b�N
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int index = 0;

		while (true) {
			// �w�肵���^�O�̗v�f���X�g���擾����
			// �v�f���X�g�̍Ď擾����IndexOutOfBoundsException����������\�������邽�ߎ擾������
			List<WebElement> elementList = driver.findElements(By.tagName(findElemntTagName));

			if (index > elementList.size() - 1) {
				break;
			}

			// �v�f���擾����
			WebElement element = elementList.get(index);

			for (String value : values) {
				// �w�肵�������ɒl�����݂���ꍇ
				if (element.getAttribute(findAttributeName).contains(value)) {
					// �{�^������������
					js.executeScript("arguments[0].click();", element);
					isClicked = true;
				}
			}

			if (isClicked) {
				break;
			}

			index++;
		}

		return isClicked;
	}

	public boolean find(String findElemntTagName, String findAttributeName, String adjust, String value) {
		return false;
	}

	public String getText(String findElemntTagName, String findAttributeName, String adjust, String value) {
		return null;
	}

}
