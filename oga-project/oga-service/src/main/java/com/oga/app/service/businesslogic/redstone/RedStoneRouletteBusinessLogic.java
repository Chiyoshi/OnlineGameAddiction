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
 * ���[���b�g����
 */
public class RedStoneRouletteBusinessLogic extends BaseBusinessLogic<DailyWorkServiceBean> {

	/** ���[���b�g���� */
	private static RedStoneRouletteBusinessLogic redStoneRouletteBusinessLogic;

	/** �������ServiceBean */
	private DailyWorkServiceBean dailyWorkServiceBean;

	/** ������Ə�� */
	private DailyWork dailyWork;

	/** ���[���b�g��ʂ�URL */
	private String REDSTONE_ROULETTE_URL = null;

	/** ���[���b�g��ʂ̖{���̃R�C���l���̃{�^��1 */
	private String REDSTONE_ROULETTE_TODAY_COINL_BTN1 = null;

	/** ���[���b�g��ʂ̖{���̃R�C���l���̃{�^��2 */
	private String REDSTONE_ROULETTE_TODAY_COINL_BTN2 = null;

	/** ���[���b�g��ʂ̃��[���b�g�J�n�{�^��1 */
	private String REDSTONE_ROULETTE_START_BTN1 = null;

	/** ���[���b�g��ʂ̃��[���b�g�J�n�{�^��2 */
	private String REDSTONE_ROULETTE_START_BTN2 = null;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param driver WebDriver
	 */
	public RedStoneRouletteBusinessLogic() {
		// �}�X�^���
		MasterDataManager master = MasterDataManager.getInstance();

		REDSTONE_ROULETTE_URL = master.get("webpage.url.redstone.roulette");
		REDSTONE_ROULETTE_TODAY_COINL_BTN1 = master.get("redstone.button.roulette.today.coin1");
		REDSTONE_ROULETTE_TODAY_COINL_BTN2 = master.get("redstone.button.roulette.today.coin2");
		REDSTONE_ROULETTE_START_BTN1 = master.get("redstone.button.roulette.start1");
		REDSTONE_ROULETTE_START_BTN2 = master.get("redstone.button.roulette.start2");
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized RedStoneRouletteBusinessLogic getInstance() {
		if (redStoneRouletteBusinessLogic == null) {
			redStoneRouletteBusinessLogic = new RedStoneRouletteBusinessLogic();
		}
		return redStoneRouletteBusinessLogic;
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

		// ���[���b�g�̃L�����y�[�������擾����
		List<Campaign> campaignList = CampaignDao.getInstance().findByCampaignDate(
				ServiceType.ROULETTE.getValue(),
				this.dailyWorkServiceBean.getBaseDate());

		// �L�����y�[����񂪓o�^����Ă��Ȃ��ꍇ�̓G���[�Ƃ���
		if (campaignList.size() == 0) {
			throw new ApplicationException("���[���b�g�̃L�����y�[����񂪓o�^����Ă��Ȃ����A�܂��́A�Ώۊ��ԊO�ł��B");
		}
		// �L�����y�[����񂪂Q�ȏ㑶�݂���ꍇ�͑z��O�̂��߃G���[�Ƃ���
		else if (campaignList.size() > 1) {
			throw new ApplicationException("���[���b�g�̑Ώۊ��Ԃ��d�����ēo�^����Ă��܂��B");
		}

		// ������Ə����擾����
		this.dailyWork = DailyWorkDao.getInstance().findByPKey(this.dailyWorkServiceBean.getUserId());

		// �f�[�^�����݂��Ȃ��ꍇ�͑z��O�̂��߃G���[�Ƃ���
		if (this.dailyWork == null) {
			throw new SystemException("������Ə�񂪎擾�ł��܂���B�f�[�^�s�������������Ă��܂��B");
		}
	}

	/**
	 * ���[���b�g����
	 * <pre>
	 * �ȉ��̋Ɩ��������s���B
	 * �E���[���b�g�t���O���`�F�b�N����
	 * �E������Ə�񌋉ʂ��擾����
	 * �EWEB�h���C�o�[���擾���� 
	 * �E���[���b�g��ʂɑJ�ڂ���
	 * �E�������̃R�C���l���̎��{������`�F�b�N����
	 * �E�{���̃R�C�����l������
	 * �E������ƌ��ʏ��(�R�C���l��)��o�^�܂��͍X�V����
	 * �E�������̃��[���b�g�̎��{������`�F�b�N����
	 * �E���݂ۗ̕L�R�C�������擾����
	 * �E���[���b�g���J�n����
	 * �E������ƌ��ʏ��(���[���b�g)��o�^�܂��͍X�V����
	 * </pre>
	 */
	@Override
	protected void doBusinessLogic() throws SystemException, ApplicationException {

		LogUtil.info("[���[���b�g����] [START]");

		// ���[���b�g�t���O���uN�v�̏ꍇ�͎��{���Ȃ�
		if (YesNo.NO.getValue().equals(this.dailyWork.getRouletteFlg())) {
			LogUtil.info("���[���b�g�t���O���uN�v�̏ꍇ�̓X�L�b�v���܂��B");
			return;
		}

		// ������ƌ��ʏ����擾����
		DailyWorkResult dailyWorkResult = DailyWorkResultDao.getInstance().findByPKey(
				dailyWorkServiceBean.getUserId(), dailyWorkServiceBean.getBaseDate(),
				ServiceType.ROULETTE.getValue());

		if (dailyWorkResult != null && Status.SUCCESS.getValue().equals(dailyWorkResult.getStatus())) {
			LogUtil.info("�{���̃��[���b�g�͎��{�ς݂̂��߃X�L�b�v���܂��B");
			return;
		}

		// �ҋ@����
		WebDriverManager.getInstance().sleep();

		// WEB�h���C�o�[���擾����
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// ��ʕ\���ҋ@
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// ���݂�URL�����[���b�g��URL�ȊO�̏ꍇ
		if (!driver.getCurrentUrl().equals(REDSTONE_ROULETTE_URL)) {
			// ���[���b�g��ʂɑJ�ڂ���
			driver.navigate().to(REDSTONE_ROULETTE_URL);

			try {
				// �w�肵���e�L�X�g���\�������܂őҋ@����
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mycoin")));
			} catch (TimeoutException e) {
				throw new ApplicationException("���[���b�g��ʂɑJ�ڂł��܂���ł����B");
			}
		}

		// �ҋ@����
		WebDriverManager.getInstance().sleep();

		//////////////////////////////////////////
		// �{���̃R�C���l��
		//////////////////////////////////////////
		// ���݂ۗ̕L�R�C�������擾����
		String curentHoldCoin = getTodayCoin();

		//////////////////////////////////////////
		// ���[���b�g�J�n
		//////////////////////////////////////////
		// �ۗL�R�C������0���̏ꍇ�̓X�L�b�v����
		if (curentHoldCoin.equals("0")) {
			LogUtil.info("���݂ۗ̕L�R�C������0���̂��߃X�L�b�v���܂��B");
			return;
		}

		// ���[���b�g�J�n
		roulette(dailyWorkResult);

		// �ҋ@����
		WebDriverManager.getInstance().sleep();

		LogUtil.info("[���[���b�g����] [END]");
	}

	/**
	 * �{���̃R�C�����l������
	 * ���݂ۗ̕L�R�C������ԋp����
	 * 
	 * @return �ۗL�R�C���� 
	 * @throws ApplicationException
	 */
	private String getTodayCoin() throws ApplicationException {

		LogUtil.info("[���[���b�g����] [�R�C���l��] [START]");

		// �{���̃R�C�����l������
		boolean isClicked = WebDriverManager.getInstance().click("div", "style",
				REDSTONE_ROULETTE_TODAY_COINL_BTN1, REDSTONE_ROULETTE_TODAY_COINL_BTN2);

		if (!isClicked) {
			throw new ApplicationException("�{���̃R�C�����l�����邱�Ƃ��ł��܂���ł����B");
		}

		// ���݂ۗ̕L�R�C�������擾����
		String curentHoldCoin = getCurentHoldCoin();

		LogUtil.info("[���[���b�g����] [�R�C���l��] [END]");

		return curentHoldCoin;
	}

	/**
	 * ���݂ۗ̕L�R�C�������擾����
	 * 
	 * @return �ۗL�R�C���� 
	 */
	private String getCurentHoldCoin() {
		// ���݂ۗ̕L�R�C����
		String curentHoldCoin = "0";

		// WEB�h���C�o�[���擾����
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		int index = 0;

		while (true) {
			// div�^�O�̗v�f���X�g���擾����
			// �v�f���X�g�̍Ď擾����IndexOutOfBoundsException����������\�������邽�ߎ擾������
			List<WebElement> elementList = driver.findElements(By.tagName("div"));

			if (index > elementList.size() - 1) {
				break;
			}

			// �v�f���擾����
			WebElement element = elementList.get(index);

			if (element.getAttribute("class").contains("mycoin")) {
				// div�^�O����span�^�O�̃e�L�X�g���擾����
				element = element.findElement(By.tagName("span"));
				curentHoldCoin = element.getText();
			}

			index++;
		}

		LogUtil.info("[���[���b�g����] [�R�C���l��] [�ۗL�R�C�����F" + curentHoldCoin + "]");

		return curentHoldCoin;
	}

	/**
	 * ���[���b�g�J�n
	 * 
	 * @param dailyWorkResult
	 * @throws ApplicationException
	 */
	private void roulette(DailyWorkResult dailyWorkResult) throws ApplicationException {

		LogUtil.info("[���[���b�g����] [���[���b�g�J�n] [START]");

		boolean isUpdate = true;

		// �{�����̓�����Ə�񂪑��݂��Ȃ��ꍇ�͐�������
		if (dailyWorkResult == null) {
			dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(this.dailyWorkServiceBean.getUserId());
			dailyWorkResult.setBaseDate(this.dailyWorkServiceBean.getBaseDate());
			dailyWorkResult.setServiceType(ServiceType.ROULETTE.getValue());
			dailyWorkResult.setDeleteFlg(YesNo.NO.getValue());
			isUpdate = false;
		}

		// WEB�h���C�o�[���擾����
		WebDriver driver = WebDriverManager.getInstance().getWebDriver();

		// ��ʕ\���ҋ@
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// ���[���b�g�J�n����������
			boolean isClicked = WebDriverManager.getInstance().click("div", "style",
					REDSTONE_ROULETTE_START_BTN1, REDSTONE_ROULETTE_START_BTN2);

			if (!isClicked) {
				throw new ApplicationException("���[���b�g�J�n�������ł��܂���ł����B");
			}

			try {
				// �w�肵��class�v�f���\�������܂őҋ@����
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("item-text")));
			} catch (TimeoutException e) {
				throw new ApplicationException("���[���b�g�J�n�������ł��܂���ł����B");
			}

			int index = 0;

			while (true) {
				// div�^�O�̗v�f���X�g���擾����
				// �v�f���X�g�̍Ď擾����IndexOutOfBoundsException����������\�������邽�ߎ擾������
				List<WebElement> elementList = driver.findElements(By.tagName("div"));

				if (index > elementList.size() - 1) {
					break;
				}

				// �v�f���擾����
				WebElement element = elementList.get(index);
				if (element.getAttribute("class").contains("item-text")) {
					// // �l���A�C�e�����擾����
					String rewardItem = element.getText();
					// �l���A�C�e����ݒ肷��
					dailyWorkResult.setRewardItem(rewardItem);

					break;
				}

				index++;
			}

			LogUtil.info("[���[���b�g����] [���[���b�g�J�n] [�l���A�C�e���F" + dailyWorkResult.getRewardItem() + "]");

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

		LogUtil.info("[���[���b�g����] [���[���b�g�J�n] [END]");
	}
}
