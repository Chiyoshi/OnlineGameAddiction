package com.oga.app.service.businesslogic.redstone;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.service.businesslogic.BaseBusinessLogic;
import com.oga.app.service.manager.MasterDataManager;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.servicebeans.LoginServiceBean;

/**
 * ���O�A�E�g����
 */
public class RedstoneLogoutBusinessLogic extends BaseBusinessLogic<LoginServiceBean> {

	/** ���O�A�E�g���� */
	private static RedstoneLogoutBusinessLogic redstoneLogoutBusinessLogic;

	/** ServiceBean */
	private LoginServiceBean loginServiceBean;

	/** ���b�h�X�g�[��TOP��ʂ�URL */
	private String REDSTONE_TOP_URL = null;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param driver WebDriver
	 */
	public RedstoneLogoutBusinessLogic() {
		// �}�X�^���
		MasterDataManager master = MasterDataManager.getInstance();

		REDSTONE_TOP_URL = master.get("webpage.url.redstone");
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized RedstoneLogoutBusinessLogic getInstance() {
		if (redstoneLogoutBusinessLogic == null) {
			redstoneLogoutBusinessLogic = new RedstoneLogoutBusinessLogic();
		}
		return redstoneLogoutBusinessLogic;
	}

	@Override
	protected void createServiceBean(LoginServiceBean serviceBean) {
		this.loginServiceBean = serviceBean;
	}

	/**
	 * �f�[�^�擾����у`�F�b�N����
	 * <pre>
	 * �������Ȃ�
	 * </pre>
	 */
	@Override
	protected void checkSelectData() throws ApplicationException {
		// �������Ȃ�
	}

	/**
	 * ���O�A�E�g����
	 * <pre>
	 * (1) WEB�h���C�o�[���擾����B 
	 * (2) ���O�C���`�F�b�N����B
	 * (3) ���O�A�E�g�{�^������������B
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {

		LogUtil.info("[���O�A�E�g����] [START]");

		// ���O�A�E�g�ς݂̏ꍇ�̓X�L�b�v����
		if (!this.loginServiceBean.isLogged()) {
			LogUtil.info("���Ƀ��O�A�E�g�ς݂̂��߃X�L�b�v���܂�");
			return;
		}

		// WEB�h���C�o�[���擾����
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// ��ʕ\���ҋ@
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// �g�b�v�y�[�W��ʂɑJ�ڂ���
		driver.navigate().to(REDSTONE_TOP_URL);

		try {
			// �w�肵���e�L�X�g���\�������܂őҋ@����
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='���O�A�E�g']")));
		} catch (TimeoutException e) {
			e.printStackTrace();
			LogUtil.info("���Ƀ��O�A�E�g�ς݂̂��߃X�L�b�v���܂�");
			return;
		}

		try {
			// JavaScript���g�p���ėv�f���N���b�N
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// input�^�O�̗v�f���X�g���擾����
			List<WebElement> elementList = driver.findElements(By.tagName("span"));

			for (int i = 0; i < elementList.size(); i++) {
				WebElement element = driver.findElements(By.tagName("span")).get(i);

				if ("���O�A�E�g".equals(element.getText())) {
					// ���O�A�E�g�{�^������������
					js.executeScript("arguments[0].click();", element);
					break;
				}
			}

		} catch (Exception e) {
			throw new ApplicationException("���O�A�E�g�����Ɏ��s���܂���", e);
		}

		LogUtil.info("[���O�A�E�g����] [END]");
	}
}
