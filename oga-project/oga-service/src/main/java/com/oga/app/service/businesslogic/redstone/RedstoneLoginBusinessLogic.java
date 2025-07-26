package com.oga.app.service.businesslogic.redstone;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.common.utils.StringUtil;
import com.oga.app.service.businesslogic.BaseBusinessLogic;
import com.oga.app.service.manager.MasterDataManager;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.servicebeans.LoginServiceBean;

/**
 * ���O�C������
 */
public class RedstoneLoginBusinessLogic extends BaseBusinessLogic<LoginServiceBean> {

	/** ���O�C������ */
	private static RedstoneLoginBusinessLogic redstoneLoginBusinessLogic;

	/** ���O�C��ServiceBean */
	private LoginServiceBean loginServiceBean;

	/** ���b�h�X�g�[��TOP��ʂ�URL */
	private String REDSTONE_TOP_URL = null;

	/** ���b�h�X�g�[�����O�C����ʂ�URL */
	private String REDSTONE_LOGIN_URL = null;

	/** ���b�h�X�g�[���̃��O�C���L�����y�[����ʂ�URL */
	private String REDSTONE_LOGINCAMPAIGN_URL = null;

	/** ���b�h�X�g�[���̃��O�C���L�����y�[���փ_�C���N�g���O�C�����郍�O�C����ʂ�URL */
	private String REDSTONE_DIRECTLOGIN_LOGINCAMPAIGN_URL = null;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param driver WebDriver
	 */
	public RedstoneLoginBusinessLogic() {
		// �}�X�^���
		MasterDataManager master = MasterDataManager.getInstance();

		REDSTONE_TOP_URL = master.get("webpage.url.redstone");
		REDSTONE_LOGIN_URL = master.get("webpage.url.redstone.login");
		REDSTONE_LOGINCAMPAIGN_URL = master.get("webpage.url.redstone.logincampaign");
		REDSTONE_DIRECTLOGIN_LOGINCAMPAIGN_URL = master.get("webpage.url.redstone.directlogin.logincampaign");
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized RedstoneLoginBusinessLogic getInstance() {
		if (redstoneLoginBusinessLogic == null) {
			redstoneLoginBusinessLogic = new RedstoneLoginBusinessLogic();
		}
		return redstoneLoginBusinessLogic;
	}

	@Override
	protected void createServiceBean(LoginServiceBean serviceBean) {
		this.loginServiceBean = serviceBean;
	}

	/**
	 * �f�[�^�擾����у`�F�b�N����
	 * <pre>
	 * (1) ��`�F�b�N
	 *     �E���[�UID
	 *     �E�p�X���[�h 
	 * </pre>
	 */
	@Override
	protected void checkSelectData() throws ApplicationException {

		// ���̓`�F�b�N
		// ���[�UID����̏ꍇ�̓G���[
		if (StringUtil.isNullOrEmpty(loginServiceBean.getUserId())) {
			throw new ApplicationException("���[�UID�����͂���Ă��܂���");
		}

		// �p�X���[�h����̏ꍇ�̓G���[
		if (StringUtil.isNullOrEmpty(loginServiceBean.getPassword())) {
			throw new ApplicationException("�p�X���[�h�����͂���Ă��܂���");
		}
	}

	/**
	 * ���O�C�������̃r�W�l�X���W�b�N
	 * <pre>
	 * (1) �T�[�r�X��ʂ��Ƃ̃��O�C����`��ݒ肷��
	 * (2) WEB�h���C�o�[���擾���� 
	 * (3) ���O�C����ʂɑJ�ڂ���
	 * (4) ���O�C��������͂���
	 * (5) ���O�C���{�^������������
	 * (6) ���O�C����̉�ʂɑJ�ڂ��Ȃ��ꍇ�͗�O�𔭐�������
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {
		LogUtil.info("[���O�C������] [START]");

		// �T�[�r�X��ʂ��Ƃ�URL��ݒ肷��
		switch (this.loginServiceBean.getServiceType()) {
		// ���O�C���L�����y�[���̃_�C���N�g���O�C��
		case LOGINCAMPAIGN:
			// ���O�C��URL��ݒ肷��
			this.loginServiceBean.setUrl(REDSTONE_DIRECTLOGIN_LOGINCAMPAIGN_URL);
			// ���O�C�����URL��ݒ肷��
			this.loginServiceBean.setLoginAfterUrl(REDSTONE_LOGINCAMPAIGN_URL);
			// ���O�C�����̉�ʕ\���ҋ@�Ɏw�肷�镶�����ݒ肷��
			this.loginServiceBean.setWaitUntilTextDisplayed("//div[text()='���ӎ���']");
			break;

		// �ʏ탍�O�C��
		default:
			// ���O�C��URL��ݒ肷��
			this.loginServiceBean.setUrl(REDSTONE_LOGIN_URL);
			// ���O�C�����URL��ݒ肷��
			this.loginServiceBean.setLoginAfterUrl(REDSTONE_TOP_URL);
			// ���O�C�����̉�ʕ\���ҋ@�Ɏw�肷�镶�����ݒ肷��
			this.loginServiceBean.setWaitUntilTextDisplayed("//span[text()='���O�A�E�g']");
			break;
		}

		// ���O�C������
		login(this.loginServiceBean.getUrl(), this.loginServiceBean.getUserId(), this.loginServiceBean.getPassword());

		// �ҋ@����
		WebDriverManager.getInstance().sleep();

		LogUtil.info("[���O�C������] [���O�C���`�F�b�N] [START]");

		// ���O�C���`�F�b�N
		if (!isLogged(this.loginServiceBean.getLoginAfterUrl(), this.loginServiceBean.getWaitUntilTextDisplayed())) {
			// �����O�C���t���O��ݒ肷��
			this.loginServiceBean.setLogged(true);

			throw new ApplicationException("���O�C�������Ɏ��s���܂���");
		}

		// ���O�C���ς݃t���O��ݒ肷��
		this.loginServiceBean.setLogged(true);

		LogUtil.info("[���O�C������] [���O�C���`�F�b�N] [END]");

		//////////////////////////////////////////
		// �K��ύX�ɂ��Ă̓���
		//////////////////////////////////////////
		//		// �ҋ@����
		//		CommonUtil.waitTime(3000);
		//
		//		boolean isAgree = false;
		//		// span�^�O�̗v�f���X�g���擾����
		//		elementList = driver.findElements(By.tagName("span"));
		//
		//		// �擾�����A�C�e�������擾����
		//		for (int i = 0; i < elementList.size(); i++) {
		//			WebElement element = driver.findElements(By.tagName("span")).get(i);
		//
		//			// �u�S�ē��ӂ��܂��v�`�F�b�N�{�b�N�X����������
		//			if ("�S�ē��ӂ��܂��B".equals(element.getText())) {
		//				element.click();
		//				isAgree = true;
		//				LogUtil.info("�K��ύX�ɓ��ӂ��܂���");
		//			}
		//
		//			// �m�F�{�^������������
		//			if (isAgree && "�m�F".equals(element.getText())) {
		//				// �u�S�ē��ӂ��܂��v�`�F�b�N�{�b�N�X����������
		//				JavascriptExecutor js = (JavascriptExecutor) driver;
		//				js.executeScript("arguments[0].click();", element);
		//
		//				// �ҋ@����
		//				CommonUtil.sleep();
		//			}
		//		}

		LogUtil.info("[���O�C������] [END]");
	}

	/**
	 * ���O�C������
	 * 
	 * @param url ���O�C��URL
	 * @param userId ���[�UID
	 * @param password �p�X���[�h
	 * @throws ApplicationException
	 */
	private void login(String url, String userId, String password) throws ApplicationException {

		// WEB�h���C�o�[���擾����
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// ��ʕ\���ҋ@
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// ���O�C����ʂɑJ�ڂ���
		driver.navigate().to(url);

		// �w�肵���e�L�X�g���\�������܂őҋ@����
		By spanLocator = By.xpath("//span[text()='���O�C��']");
		wait.until(ExpectedConditions.visibilityOfElementLocated(spanLocator));

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
				element.sendKeys(userId);
				isInputedUserId = true;
			}

			// �p�X���[�h����͂���
			if ("�p�X���[�h".equals(element.getAttribute("placeholder"))) {
				element.sendKeys(password);
				isInputedPassword = true;
			}

			index++;
		}

		// ���[�UID����уp�X���[�h�����͂��ꂽ�ꍇ�Ƀ��O�C���{�^������������
		if (isInputedUserId == true && isInputedPassword == true) {
			// �ҋ@����
			WebDriverManager.getInstance().sleep();

			// �{�^������
			driver.findElement(By.tagName("form")).submit();
		}
	}

	/**
	 * ���O�C���`�F�b�N
	 * 
	 * @param afterLoginUrl ���O�C�����URL
	 * @param waitUntilTextDisplayed ��ʕ\���ҋ@�Ɏw�肷�镶����
	 * @return
	 */
	private boolean isLogged(String afterLoginUrl, String waitUntilTextDisplayed) {

		// WEB�h���C�o�[���擾����
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// ��ʕ\���ҋ@
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// ���݂�URL�ƃ��O�C�����URL����v���Ă��Ȃ��ꍇ�͉�ʑJ�ڂ���B
		// ���O�C����ɃA�b�v�f�[�g���̃y�[�W�Ɏ����I�ɑJ�ڂ���̂�h��
		//		if (driver.getCurrentUrl().equals(afterLoginUrl)) {
		//			driver.navigate().to(afterLoginUrl);
		//		}

		try {
			// �w�肵���e�L�X�g���\�������܂őҋ@����
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(waitUntilTextDisplayed)));
		} catch (TimeoutException e) {
			return false;
		}

		return true;
	}
}
