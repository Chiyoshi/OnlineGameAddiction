package com.oga.app.batch;

import java.io.File;
import java.util.List;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.FileUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.Campaign;
import com.oga.app.dataaccess.entity.DailyWork;
import com.oga.app.dataaccess.entity.DailyWorkResult;
import com.oga.app.dataaccess.entity.Master;
import com.oga.app.dataaccess.entity.User;
import com.oga.app.service.provider.CampaignProvider;
import com.oga.app.service.provider.DailyWorkProvider;
import com.oga.app.service.provider.DailyWorkResultProvider;
import com.oga.app.service.provider.MasterProvider;
import com.oga.app.service.provider.UserProvider;

public class ImportCsvFileBatch extends BaseBatch {

	/** CSV�t�@�C����(user.csv) */
	private final String CSV_FILE_USER = "user.csv";

	/** CSV�t�@�C����(dailywork.csv) */
	private final String CSV_FILE_DAILYWORK = "dailywork.csv";

	/** CSV�t�@�C����(dailyworkresult.csv) */
	private final String CSV_FILE_DAILYWORKRESULT = "dailyworkresult.csv";

	/** CSV�t�@�C����(campaign.csv) */
	private final String CSV_FILE_CAMPAIGN = "campaign.csv";

	/** CSV�t�@�C����(master.csv) */
	private final String CSV_FILE_MASTER = "master.csv";

	/** CSV�i�[��f�B���N�g�� */
	private String INPUT_CSV_PATH = null;

	@Override
	public void pre(String[] args) throws ApplicationException, SystemException {
		// ���ϐ�����p�X���擾����
		String inputCsvPath = System.getProperty("input.dir");

		if (StringUtil.isNullOrEmpty(inputCsvPath)) {
			throw new SystemException("���ϐ����ݒ肳��Ă��܂���B�Finput.dir");
		}

		// �f�B���N�g�����݃`�F�b�N
		if (!FileUtil.isExists(inputCsvPath)) {
			throw new ApplicationException("CSV�i�[��̃f�B���N�g�������݂��܂���B�F" + inputCsvPath);
		}

		INPUT_CSV_PATH = inputCsvPath;
	}

	@Override
	public void exec() throws ApplicationException, SystemException {

		// CSV�t�@�C���ꗗ���擾����
		List<File> files = FileUtil.getFileList(INPUT_CSV_PATH);

		for (File file : files) {

			// CSV�t�@�C�������擾����
			String csvFileName = file.getName().toLowerCase();

			// CSV�t�@�C����ǂݍ��� 
			List<String[]> dataList = FileUtil.readCsvFile(file.getAbsolutePath());

			// �t�@�C�����Ƃɏ����𕪊򂷂�
			switch (csvFileName) {
			case CSV_FILE_USER:
				// User�e�[�u����o�^����
				insertUser(dataList);
				break;
			case CSV_FILE_DAILYWORK:
				// DailyWork�e�[�u����o�^����
				insertDailyWork(dataList);
				break;
			case CSV_FILE_DAILYWORKRESULT:
				// DailyWorkResult�e�[�u����o�^����
				insertDailyWorkResult(dataList);
				break;
			case CSV_FILE_CAMPAIGN:
				// Campaign�e�[�u����o�^����
				insertCampaign(dataList);
				break;
			case CSV_FILE_MASTER:
				// Master�e�[�u����o�^����
				insertMaster(dataList);
				break;
			default:
				break;
			}

		}

	}

	@Override
	public void post() throws ApplicationException, SystemException {
	}

	/**
	 * CSV�t�@�C���̓��e��User�e�[�u���ɓo�^����
	 * 
	 * @param dataList �f�[�^���X�g
	 */
	private void insertUser(List<String[]> dataList) {
		// ���[�U�����폜����
		LogUtil.info("[DELETE] [USER] [START]");
		UserProvider.getInstance().deleteUser();
		LogUtil.info("[DELETE] [USER] [END]");

		LogUtil.info("[INSERT] [USER] [START]");

		for (String[] data : dataList) {
			User user = new User();
			user.setUserId(data[0]);
			user.setPassword(data[1]);
			user.setBirthDay(data[2]);
			user.setMailAddress(data[3]);
			user.setGem(data[4]);
			user.setServicePoint(data[5]);
			user.setRedspoint(data[6]);
			//user.setRegistrationDate(data[7]);
			//user.setUpdateDate(data[8]);
			user.setDeleteFlg(data[9]);
			user.setDeleteDate(data[10]);

			LogUtil.info(user.toString());

			// ���[�U����o�^����
			UserProvider.getInstance().insertUser(user);
		}

		LogUtil.info("[INSERT] [USER] [END]");
	}

	/**
	 * CSV�t�@�C���̓��e��DailyWork�e�[�u���ɓo�^����
	 * 
	 * @param dataList �f�[�^���X�g
	 */
	private void insertDailyWork(List<String[]> dataList) {
		// ������Ə����폜����
		LogUtil.info("[DELETE] [DAILYWORK] [START]");
		DailyWorkProvider.getInstance().deleteDailyWork();
		LogUtil.info("[DELETE] [DAILYWORK] [END]");

		LogUtil.info("[INSERT] [DAILYWORK] [START]");

		for (String[] data : dataList) {
			DailyWork dailyWork = new DailyWork();
			dailyWork.setUserId(data[0]);
			dailyWork.setLoginCampaignFlg(data[1]);
			dailyWork.setDailyRewardFlg(data[2]);
			dailyWork.setRouletteFlg(data[3]);
			dailyWork.setLastLoginCampaignDate(data[4]);
			dailyWork.setLastDailyRewardDate(data[5]);
			dailyWork.setLastRouletteDate(data[6]);
			//dailyWork.setRegistrationDate(data[7]);
			//dailyWork.setUpdateDate(data[8]);
			dailyWork.setDeleteFlg(data[9]);
			dailyWork.setDeleteDate(data[10]);

			LogUtil.info(dailyWork.toString());

			// ������Ə���o�^����
			DailyWorkProvider.getInstance().insertDailyWork(dailyWork);
		}

		LogUtil.info("[INSERT] [DAILYWORK] [END]");
	}

	/**
	 * CSV�t�@�C���̓��e��DailyWorkResult�e�[�u���ɓo�^����
	 * 
	 * @param dataList �f�[�^���X�g
	 */
	private void insertDailyWorkResult(List<String[]> dataList) {
		// ������ƌ��ʂ��폜����
		LogUtil.info("[DELETE] [DAILYWORKRESULT] [START]");
		DailyWorkResultProvider.getInstance().deleteDailyWorkResult();
		LogUtil.info("[DELETE] [DAILYWORKRESULT] [END]");

		LogUtil.info("[INSERT] [DAILYWORKRESULT] [START]");

		for (String[] data : dataList) {
			DailyWorkResult dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(data[0]);
			dailyWorkResult.setBaseDate(data[1]);
			dailyWorkResult.setServiceType(data[2]);
			dailyWorkResult.setStatus(data[3]);
			dailyWorkResult.setRewardItem(data[4]);
			dailyWorkResult.setRewardItemImage(data[5]);
			//dailyWorkResult.setRegistrationDate(data[6]);
			//dailyWorkResult.setUpdateDate(data[7]);
			dailyWorkResult.setDeleteFlg(data[8]);
			dailyWorkResult.setDeleteDate(data[9]);

			LogUtil.info(dailyWorkResult.toString());

			// ������ƌ��ʂ�o�^����
			DailyWorkResultProvider.getInstance().insertDailyWorkResult(dailyWorkResult);
		}

		LogUtil.info("[INSERT] [DAILYWORKRESULT] [END]");
	}

	/**
	 * CSV�t�@�C���̓��e��Campaign�e�[�u���ɓo�^����
	 * 
	 * @param dataList �f�[�^���X�g
	 */
	private void insertCampaign(List<String[]> dataList) {
		// �L�����y�[�������폜����
		LogUtil.info("[DELETE] [CAMPAIGN] [START]");
		CampaignProvider.getInstance().deleteCampaign();
		LogUtil.info("[DELETE] [CAMPAIGN] [END]");

		LogUtil.info("[INSERT] [CAMPAIGN] [START]");

		for (String[] data : dataList) {
			Campaign campaign = new Campaign();
			campaign.setCampaignId(data[0]);
			campaign.setCampaignType(data[1]);
			campaign.setCampaignName(data[2]);
			campaign.setStartDate(data[3]);
			campaign.setEndDate(data[4]);
			//campaign.setRegistrationDate(data[5]);
			//campaign.setUpdateDate(data[6]);
			campaign.setDeleteFlg(data[7]);
			campaign.setDeleteDate(data[8]);

			LogUtil.info(campaign.toString());

			// ���[�U����o�^����
			CampaignProvider.getInstance().insertCampaign(campaign);
		}

		LogUtil.info("[INSERT] [CAMPAIGN] [END]");
	}

	/**
	 * CSV�t�@�C���̓��e��Master�e�[�u���ɓo�^����
	 * 
	 * @param dataList �f�[�^���X�g
	 */
	private void insertMaster(List<String[]> dataList) {
		// �}�X�^�����폜����
		LogUtil.info("[DELETE] [MASTER] [START]");
		MasterProvider.getInstance().deleteMaster();
		LogUtil.info("[DELETE] [MASTER] [END]");

		LogUtil.info("[INSERT] [MASTER] [START]");

		for (String[] data : dataList) {
			Master master = new Master();
			master.setKey(data[0]);
			master.setValue(data[1]);
			master.setExplanation(data[2]);
			master.setOrder(Integer.parseInt(data[3]));
			//master.setRegistrationDate(data[4]);
			//master.setUpdateDate(data[5]);
			master.setDeleteFlg(data[6]);
			master.setDeleteDate(data[7]);

			LogUtil.info(master.toString());

			// �}�X�^����o�^����
			MasterProvider.getInstance().insertMaster(master);
		}

		LogUtil.info("[INSERT] [MASTER] [END]");
	}

	public static void main(String[] args) {
		new ImportCsvFileBatch().run(args);
	}

}
