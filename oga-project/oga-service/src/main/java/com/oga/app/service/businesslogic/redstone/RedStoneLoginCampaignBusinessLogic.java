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

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.Status;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.DateUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.dataaccess.dao.CampaignDao;
import com.oga.app.dataaccess.dao.DailyWorkDao;
import com.oga.app.dataaccess.dao.DailyWorkResultDao;
import com.oga.app.dataaccess.entity.Campaign;
import com.oga.app.dataaccess.entity.DailyWork;
import com.oga.app.dataaccess.entity.DailyWorkResult;
import com.oga.app.service.businesslogic.BaseBusinessLogic;
import com.oga.app.service.manager.MasterDataManager;
import com.oga.app.service.manager.WebDriverManager;
import com.oga.app.service.servicebeans.DailyWorkServiceBean;

/**
 * ���O�C���L�����y�[������
 */
public class RedStoneLoginCampaignBusinessLogic extends BaseBusinessLogic<DailyWorkServiceBean> {

	/** ���O�C���L�����y�[������ */
	private static RedStoneLoginCampaignBusinessLogic redstoneLoginBusinessLogic;

	/** �������ServiceBean */
	private DailyWorkServiceBean dailyWorkServiceBean;

	/** �Ώۃ��O�C���L�����y�[����� */
	private Campaign campaign;

	/** ������Ə�� */
	private DailyWork dailyWork;

	/** ���O�C���L�����y�[����ʂ̃}�b�v1�̃}�X�� */
	private int REDSTONE_LOGINCAMPAIGN_MAP1_NUM = 0;

	/** ���O�C���L�����y�[����ʂ̃}�b�v2�̃}�X�� */
	private int REDSTONE_LOGINCAMPAIGN_MAP2_NUM = 0;

	/** ���O�C���L�����y�[����ʂ̃}�b�v3�̃}�X�� */
	private int REDSTONE_LOGINCAMPAIGN_MAP3_NUM = 0;

	/** ���O�C���L�����y�[����ʂ�URL */
	private String REDSTONE_LOGINCAMPAIGN_URL = null;

	/** ���O�C���L�����y�[����ʂ̒ʏ�}�X�̃{�^��1 */
	private String REDSTONE_LOGINCAMPAIGN_NORMAL_BTN1 = null;

	/** ���O�C���L�����y�[����ʂ̒ʏ�}�X�̃{�^��2 */
	private String REDSTONE_LOGINCAMPAIGN_NORMAL_BTN2 = null;

	/** ���O�C���L�����y�[����ʂ̃R���v���[�g�}�X�̃{�^��1 */
	private String REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN1 = null;

	/** ���O�C���L�����y�[����ʂ̃R���v���[�g�}�X�̃{�^��2 */
	private String REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN2 = null;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param driver WebDriver
	 */
	public RedStoneLoginCampaignBusinessLogic() {
		// �}�X�^���
		MasterDataManager master = MasterDataManager.getInstance();

		REDSTONE_LOGINCAMPAIGN_MAP1_NUM = Integer.parseInt(master.get("redstone.logincampaign.map1.num"));
		REDSTONE_LOGINCAMPAIGN_MAP2_NUM = Integer.parseInt(master.get("redstone.logincampaign.map2.num"));
		REDSTONE_LOGINCAMPAIGN_MAP3_NUM = Integer.parseInt(master.get("redstone.logincampaign.map3.num"));

		REDSTONE_LOGINCAMPAIGN_URL = master.get("webpage.url.redstone.logincampaign");
		REDSTONE_LOGINCAMPAIGN_NORMAL_BTN1 = master.get("redstone.button.logincampaign.click1");
		REDSTONE_LOGINCAMPAIGN_NORMAL_BTN2 = master.get("redstone.button.logincampaign.click2");
		REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN1 = master.get("redstone.button.logincampaign.complete1");
		REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN2 = master.get("redstone.button.logincampaign.complete2");
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized RedStoneLoginCampaignBusinessLogic getInstance() {
		if (redstoneLoginBusinessLogic == null) {
			redstoneLoginBusinessLogic = new RedStoneLoginCampaignBusinessLogic();
		}
		return redstoneLoginBusinessLogic;
	}

	@Override
	protected void createServiceBean(DailyWorkServiceBean serviceBean) {
		this.dailyWorkServiceBean = serviceBean;

		// �����ݒ肷��
		this.dailyWorkServiceBean.setBaseDate(DateUtil.getBaseDate());
	}

	/**
	 * �f�[�^�擾����у`�F�b�N����
	 * <pre>
	 * �ȉ��̃f�[�^�擾����у`�F�b�N�������s���B
	 * �E���O�C����ԃ`�F�b�N
	 * �E�L�����y�[�������擾����B
	 * �E�L�����y�[�����`�F�b�N�B
	 * �E�L�����y�[������ݒ肷��B
	 * �E������Ə����擾����B
	 * �E������Ə��̕s�����`�F�b�N
	 * </pre>
	 * @throws SystemException 
	 */
	@Override
	protected void checkSelectData() throws ApplicationException, SystemException {

		// ���O�C����ԃ`�F�b�N
		if (!this.dailyWorkServiceBean.isLogged()) {
			throw new ApplicationException("���b�h�X�g�[���Ƀ��O�C�����Ă��Ȃ����ߏ������I�����܂��B");
		}

		// ���O�C���L�����y�[���̃L�����y�[�������擾����
		List<Campaign> campaignList = CampaignDao.getInstance().findByCampaignDate(
				ServiceType.LOGINCAMPAIGN.getValue(),
				this.dailyWorkServiceBean.getBaseDate());

		// �L�����y�[����񂪓o�^����Ă��Ȃ��ꍇ�̓G���[�Ƃ���
		if (campaignList.size() == 0) {
			throw new ApplicationException("���O�C���L�����y�[���̃L�����y�[����񂪓o�^����Ă��Ȃ����A�܂��́A�Ώۊ��ԊO�ł��B");
		}
		// �L�����y�[����񂪂Q�ȏ㑶�݂���ꍇ�͑z��O�̂��߃G���[�Ƃ���
		else if (campaignList.size() > 1) {
			throw new ApplicationException("���O�C���L�����y�[���̑Ώۊ��Ԃ��d�����ēo�^����Ă��܂��B");
		}

		// �L�����y�[������ݒ肷��
		this.campaign = campaignList.get(0);

		// ������Ə����擾����
		this.dailyWork = DailyWorkDao.getInstance().findByPKey(this.dailyWorkServiceBean.getUserId());

		// �f�[�^�����݂��Ȃ��ꍇ�͑z��O�̂��߃G���[�Ƃ���
		if (this.dailyWork == null) {
			throw new SystemException("������Ə�񂪎擾�ł��܂���B�f�[�^�s�������������Ă��܂��B");
		}
	}

	/**
	 * ���O�C���L�����y�[������
	 * <pre>
	 * �ȉ��̋Ɩ��������s���B
	 * �E���O�C���L�����y�[���t���O���`�F�b�N����
	 * �E������Ə�񌋉ʂ��擾����
	 * �E�������̎��{������`�F�b�N����
	 * �E���O�C���L�����y�[�����ԓ��̎��{�񐔂��`�F�b�N����
	 * �EWEB�h���C�o�[���擾���� 
	 * �E���O�C���L�����y�[����ʂɑJ�ڂ���
	 * �E�ʏ�}�X����������
	 * �E������ƌ��ʏ��(�ʏ�)��o�^�܂��͍X�V����
	 * �E�R���v���[�g�}�X����������
	 * �E������ƌ��ʏ��(�R���v���[�g)��o�^�܂��͍X�V����
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {

		LogUtil.info("[���O�C���L�����y�[������] [START]");

		// ���O�C���L�����y�[���t���O���uN�v�̏ꍇ�͎��{���Ȃ�
		if (YesNo.NO.getValue().equals(this.dailyWork.getLoginCampaignFlg())) {
			LogUtil.info("���O�C���L�����y�[���t���O���uN�v�̏ꍇ�̓X�L�b�v���܂��B");
			return;
		}

		// ���O�C���L�����y�[�����ԓ��̎��{�񐔂��擾����
		int loginCampaignExecCount = DailyWorkResultDao.getInstance().countByTargetDate(
				dailyWorkServiceBean.getUserId(),
				this.campaign.getStartDate(), this.campaign.getEndDate(), ServiceType.LOGINCAMPAIGN.getValue(),
				Status.SUCCESS.getValue());

		// �������̃��O�C���L�����y�[�������ׂĎ��{�ς݂̏ꍇ�͎��{���Ȃ�
		if (loginCampaignExecCount >= (REDSTONE_LOGINCAMPAIGN_MAP1_NUM + REDSTONE_LOGINCAMPAIGN_MAP2_NUM
				+ REDSTONE_LOGINCAMPAIGN_MAP3_NUM)) {
			DailyWorkResult dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(this.dailyWorkServiceBean.getUserId());
			dailyWorkResult.setBaseDate(this.dailyWorkServiceBean.getBaseDate());
			dailyWorkResult.setServiceType(ServiceType.LOGINCAMPAIGN.getValue());
			dailyWorkResult.setStatus(Status.SKIP.getValue());
			dailyWorkResult.setDeleteFlg(YesNo.NO.getValue());
			DailyWorkResultDao.getInstance().insert(dailyWorkResult);

			LogUtil.info("�������̃��O�C���L�����y�[�������ׂĎ��{�ς݂̏ꍇ�̓X�L�b�v���܂��B");
			return;
		}

		// ������ƌ��ʏ����擾����
		DailyWorkResult dailyWorkResult = DailyWorkResultDao.getInstance().findByPKey(
				dailyWorkServiceBean.getUserId(), dailyWorkServiceBean.getBaseDate(),
				ServiceType.LOGINCAMPAIGN.getValue());

		// ���ɖ{�����̃��O�C���L�����y�[�������{�ς݂̏ꍇ�͎��{���Ȃ�
		if (dailyWorkResult != null && Status.SUCCESS.getValue().equals(dailyWorkResult.getStatus())) {
			LogUtil.info("�{���̃��O�C���L�����y�[���͎��{�ς݂̂��߃X�L�b�v���܂��B");
			return;
		}

		// �ҋ@����
		WebDriverManager.getInstance().sleep();

		// WEB�h���C�o�[���擾����
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// ��ʕ\���ҋ@
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// ���݂�URL�����O�C���L�����y�[����URL�ȊO�̏ꍇ
		if (!driver.getCurrentUrl().equals(REDSTONE_LOGINCAMPAIGN_URL)) {
			// ���O�C���L�����y�[����ʂɑJ�ڂ���
			driver.navigate().to(REDSTONE_LOGINCAMPAIGN_URL);

			try {
				// �w�肵���e�L�X�g���\�������܂őҋ@����
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='���ӎ���']")));
			} catch (TimeoutException e) {
				throw new ApplicationException("���O�C���L�����y�[����ʂɑJ�ڂł��܂���ł����B");
			}
		}

		//////////////////////////////////////////
		// �ʏ�}�X
		//////////////////////////////////////////
		LogUtil.info("[���O�C���L�����y�[������] [" + (loginCampaignExecCount + 1) + "����]");

		// �ʏ탍�O�C���L�����y�[��
		loginCampaign(dailyWorkResult);

		//////////////////////////////////////////
		// �R���v���[�g�}�X
		//////////////////////////////////////////
		// ������ƌ��ʏ����擾����
		DailyWorkResult completeDailyWorkResult = DailyWorkResultDao.getInstance().findByPKey(
				dailyWorkServiceBean.getUserId(), dailyWorkServiceBean.getBaseDate(),
				ServiceType.LOGINCAMPAIGN_COMPLETE.getValue());

		// �R���v���[�g���O�C���L�����y�[��
		completeLoginCampaign(completeDailyWorkResult);

		// �ҋ@����
		WebDriverManager.getInstance().sleep();

		LogUtil.info("[���O�C���L�����y�[������] [END]");
	}

	/**
	 * �ʏ�}�X�̃��O�C���L�����y�[��
	 * 
	 * @param dailyWorkResult
	 * @throws ApplicationException
	 */
	private void loginCampaign(DailyWorkResult dailyWorkResult) throws ApplicationException {

		LogUtil.info("[���O�C���L�����y�[������] [�ʏ�}�X] [START]");

		boolean isUpdate = true;

		// �{�����̓�����Ə�񂪑��݂��Ȃ��ꍇ�͐�������
		if (dailyWorkResult == null) {
			dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(this.dailyWorkServiceBean.getUserId());
			dailyWorkResult.setBaseDate(this.dailyWorkServiceBean.getBaseDate());
			dailyWorkResult.setServiceType(ServiceType.LOGINCAMPAIGN.getValue());
			dailyWorkResult.setDeleteFlg(YesNo.NO.getValue());
			isUpdate = false;
		}

		// WEB�h���C�o�[���擾����
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// ��ʕ\���ҋ@
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// ���O�C���L�����y�[���̒ʏ�}�X����������
			boolean isClicked = WebDriverManager.getInstance().click("div", "style",
					REDSTONE_LOGINCAMPAIGN_NORMAL_BTN1, REDSTONE_LOGINCAMPAIGN_NORMAL_BTN2);

			if (!isClicked) {
				throw new ApplicationException("���O�C���L�����y�[���̒ʏ�}�X�������ł��܂���ł����B");
			}

			try {
				// �w�肵���e�L�X�g���\�������܂őҋ@����
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='����']")));
			} catch (TimeoutException e) {
				throw new ApplicationException("���O�C���L�����y�[���̒ʏ�}�X�������ł��܂���ł����B");
			}

			int index = 0;

			// �l���A�C�e�����擾����
			while (true) {
				// td�^�O�̗v�f���X�g���擾����
				// �v�f���X�g�̍Ď擾����IndexOutOfBoundsException����������\�������邽�ߎ擾������
				List<WebElement> elementList = driver.findElements(By.tagName("td"));

				if (index > elementList.size() - 1) {
					break;
				}

				// �v�f���擾����
				WebElement element = elementList.get(index);

				if (element.getAttribute("class").contains("imgbox")) {
					String rewardItem = driver.findElements(By.tagName("td")).get(index + 1).getText();

					// �l���A�C�e����ݒ肷��
					dailyWorkResult.setRewardItem(rewardItem);

					LogUtil.info("[���O�C���L�����y�[������] [�ʏ�}�X] [�l���A�C�e���F" + dailyWorkResult.getRewardItem() + "]");
					break;
				}

				index++;
			}

			// �l���A�C�e�����擾�ł��Ȃ������ꍇ
			if (dailyWorkResult.getRewardItem() == null) {
				index = 0;

				while (true) {
					// div�^�O�̗v�f���X�g���擾����
					// �v�f���X�g�̍Ď擾����IndexOutOfBoundsException����������\�������邽�ߎ擾������
					List<WebElement> elementList = driver.findElements(By.tagName("div"));

					if (index > elementList.size() - 1) {
						break;
					}

					// �v�f���擾����
					WebElement element = elementList.get(index);

					if (element.getText().equals("���łɊl��������V�ł��B")) {
						LogUtil.info("[���O�C���L�����y�[������] [�ʏ�}�X] [�l���A�C�e���F���Ɋl���ς�]");
						break;
					}

					index++;
				}
			}

			// ����p�^�[��
			// �X�e�[�^�X�ɐ���I����ݒ肷��
			dailyWorkResult.setStatus(Status.SUCCESS.getValue());

			if (!isUpdate) {
				DailyWorkResultDao.getInstance().insert(dailyWorkResult);
			} else {
				DailyWorkResultDao.getInstance().update(dailyWorkResult);
			}

		} catch (Exception e) {
			// �ُ�p�^�[��
			// �X�e�[�^�X�Ɉُ�I����ݒ肷��
			dailyWorkResult.setStatus(Status.ERROR.getValue());

			if (!isUpdate) {
				DailyWorkResultDao.getInstance().insert(dailyWorkResult);
			} else {
				DailyWorkResultDao.getInstance().update(dailyWorkResult);
			}

			throw e;
		}

		LogUtil.info("[���O�C���L�����y�[������] [�ʏ�}�X] [END]");
	}

	/**
	 * �R���v���[�g�}�X�̃��O�C���L�����y�[��
	 * 
	 * @param dailyWorkResult
	 * @throws ApplicationException
	 */
	private void completeLoginCampaign(DailyWorkResult dailyWorkResult) throws ApplicationException {

		LogUtil.info("[���O�C���L�����y�[������] [�R���v���[�g�}�X] [START]");

		boolean isUpdate = true;

		// �{�����̓�����Ə�񂪑��݂��Ȃ��ꍇ�͐�������
		if (dailyWorkResult == null) {
			dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(this.dailyWorkServiceBean.getUserId());
			dailyWorkResult.setBaseDate(this.dailyWorkServiceBean.getBaseDate());
			dailyWorkResult.setServiceType(ServiceType.LOGINCAMPAIGN_COMPLETE.getValue());
			dailyWorkResult.setDeleteFlg(YesNo.NO.getValue());
			isUpdate = false;
		}

		// WEB�h���C�o�[���擾����
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// ��ʕ\���ҋ@
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// ���O�C���L�����y�[���̃R���v���[�g�}�X����������
			boolean isClicked = WebDriverManager.getInstance().click("div", "style",
					REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN1, REDSTONE_LOGINCAMPAIGN_COMPLETE_BTN2);

			if (!isClicked) {
				throw new ApplicationException("���O�C���L�����y�[���̃R���v���[�g�}�X�������ł��܂���ł����B");
			}

			try {
				// �w�肵���e�L�X�g���\�������܂őҋ@����
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='����']")));
			} catch (TimeoutException e) {
				throw new ApplicationException("���O�C���L�����y�[���̃R���v���[�g�}�X�������ł��܂���ł����B");
			}

			// �ҋ@����
			WebDriverManager.getInstance().sleep();

			int index = 0;

			while (true) {
				// span�^�O�̗v�f���X�g���擾����
				// �v�f���X�g�̍Ď擾����IndexOutOfBoundsException����������\�������邽�ߎ擾������
				List<WebElement> elementList = driver.findElements(By.tagName("span"));

				if (index > elementList.size() - 1) {
					break;
				}

				// �v�f���擾����
				WebElement element = elementList.get(index);

				if (element.getText().contains("���ڂ܂Ŋ������Ă��܂���B")) {
					LogUtil.info("[���O�C���L�����y�[������] [�R���v���[�g�}�X] [���B��]");
					LogUtil.info("[���O�C���L�����y�[������] [�R���v���[�g�}�X] [END]");
					return;
				}

				index++;
			}

			// �l���A�C�e�����擾����
			// td�^�O�̗v�f���J��Ԃ�
			//			for (int i = 0; i < driver.findElements(By.tagName("td")).size(); i++) {
			//				WebElement element = driver.findElements(By.tagName("td")).get(i);
			//
			//				if (element.getAttribute("class").contains("imgbox")) {
			//					String rewardItem = driver.findElements(By.tagName("td")).get(i + 1).getText();
			//					// �l���A�C�e����ݒ肷��
			//					dailyWorkResult.setRewardItem(rewardItem);
			//					LogUtil.info("�l���A�C�e���F" + dailyWorkResult.getRewardItem());
			//					break;
			//				}
			//			}

			// ����p�^�[��
			// �X�e�[�^�X�ɐ���I����ݒ肷��
			dailyWorkResult.setStatus(Status.SUCCESS.getValue());

			if (!isUpdate) {
				DailyWorkResultDao.getInstance().insert(dailyWorkResult);
			} else {
				DailyWorkResultDao.getInstance().update(dailyWorkResult);
			}

		} catch (Exception e) {
			// �ُ�p�^�[��
			// �X�e�[�^�X�Ɉُ�I����ݒ肷��
			dailyWorkResult.setStatus(Status.ERROR.getValue());

			if (!isUpdate) {
				DailyWorkResultDao.getInstance().insert(dailyWorkResult);
			} else {
				DailyWorkResultDao.getInstance().update(dailyWorkResult);
			}

			throw e;
		}

		LogUtil.info("[���O�C���L�����y�[������] [�R���v���[�g�}�X] [END]");
	}
}
