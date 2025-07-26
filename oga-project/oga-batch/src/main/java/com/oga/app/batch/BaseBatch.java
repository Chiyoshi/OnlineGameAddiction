package com.oga.app.batch;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.LogUtil;

public abstract class BaseBatch {

	/**
	 * �o�b�`���s
	 */
	protected void run(String[] args) {
		try {
			// ���O�o��
			System.setProperty("log4j.configurationFile", "log4j2_batch.xml");

			// ������
			LogUtil.info("[����������] [START]");
			init();
			LogUtil.info("[����������] [END]");

			// �O����
			LogUtil.info("[�O����] [START]");
			pre(args);
			LogUtil.info("[�O����] [END]");

			// �又��
			LogUtil.info("[������] [START]");
			exec();
			LogUtil.info("[������] [END]");

		} catch (SystemException e) {
			LogUtil.error(e.getMessage(), e);
		} catch (ApplicationException e) {
			LogUtil.error(e.getMessage(), e);
		} catch (Throwable th) {
			LogUtil.error(th.getMessage(), th);
		} finally {
			try {

				// �㏈��
				LogUtil.info("[�㏈��] [START]");
				post();
				LogUtil.info("[�㏈��] [END]");

			} catch (ApplicationException e) {
				LogUtil.error(e.getMessage(), e);
			} catch (SystemException e) {
				LogUtil.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * ����������
	 * 
	 * @throws ApplicationException 
	 */
	private void init() throws ApplicationException {
//		// �}�X�^�f�[�^�Ƀ}�X�^����ݒ肷��
//		MasterServiceImpl.getInstance().createMasterData();
//		// �}�X�^�f�[�^�ɐݒ����ݒ肷��
//		SettingServiceImpl.getInstance().createMasterData();

//		// WEB�h���C�o�[
//		String webdriver = System.getProperty("webdriver");
//		
//		if (StringUtil.isNullOrEmpty(webdriver)) {
//			throw new ApplicationException("WEB�h���C�o�[�����ϐ��ɐݒ肵�Ă�������");
//		}
//		
//		if (MasterData.contains("webdriver.chrome.driver")) {
//			MasterData.remove("webdriver.chrome.driver");
//		}
//
//		// �}�X�^�f�[�^��WEB�h���C�o�[��ݒ肷��
//		MasterData.put("webdriver.chrome.driver", System.getProperty("webdriver"));
	}

	/**
	 * �O����
	 */
	abstract public void pre(String[] args) throws ApplicationException, SystemException;

	/**
	 * �又��
	 */
	abstract public void exec() throws ApplicationException, SystemException;

	/**
	 * �㏈��
	 */
	abstract public void post() throws ApplicationException, SystemException;

}
