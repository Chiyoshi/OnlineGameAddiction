package com.oga.app.service.provider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.Status;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.dataaccess.dao.DailyWorkResultDao;
import com.oga.app.dataaccess.entity.Campaign;
import com.oga.app.dataaccess.entity.DailyWorkResult;
import com.oga.app.service.manager.MasterDataManager;

/**
 * ������ƌ��ʃv���p�C�_
 */
public class DailyWorkResultProvider {

	/** ������ƌ��ʃv���p�C�_ */
	private static DailyWorkResultProvider dailyWorkResultProvider;

	/**
	 * �R���X�g���N�^
	 */
	public DailyWorkResultProvider() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized DailyWorkResultProvider getInstance() {
		if (dailyWorkResultProvider == null) {
			dailyWorkResultProvider = new DailyWorkResultProvider();
		}
		return dailyWorkResultProvider;
	}

	/**
	 * ������ƌ��ʂ��擾����
	 * 
	 * @param userId ���[�UID
	 * @param targetDate �Ώۓ�
	 * @param serviceType �T�[�r�X���
	 * @return
	 */
	public DailyWorkResult getDailyWorkResult(String userId, String targetDate, String serviceType) {
		return DailyWorkResultDao.getInstance().findByPKey(userId, targetDate, serviceType);
	}

	/**
	 * �Ώۊ��Ԃ��w�肵��������ƌ��ʂ��擾����
	 * 
	 * @param userId ���[�UID
	 * @param targetDateFrom �Ώۓ�From
	 * @param targetDateTo �Ώۓ� To
	 * @param serviceType �T�[�r�X���
	 * @return
	 */
	public List<DailyWorkResult> getDailyWorkResultByTargetDate(String userId, String targetDateFrom,
			String targetDateTo, String serviceType) {
		return DailyWorkResultDao.getInstance().findByTargetDate(userId, targetDateFrom, targetDateTo, serviceType);
	}

	/**
	 * �Ώۓ��t�̓�����Ƃ����{�ς݂��ۂ����f����
	 * 
	 * @param userId ���[�UID
	 * @param targetDate �Ώۓ�
	 * @param serviceType �T�[�r�X���
	 * @return
	 */
	public boolean isExecutionNeeded(String userId, String targetDate, String serviceType) {
		DailyWorkResult dailyWorkResult = this.getDailyWorkResult(userId, targetDate, serviceType);
		return dailyWorkResult == null || Status.ERROR.getValue().equals(dailyWorkResult.getStatus());
	}

	/**
	 * ���O�C���L�����y�[���̎��{�񐔂�����ɒB���Ă��邩�ۂ�
	 * 
	 * @param userId ���[�UID
	 * @param baseDate ���
	 * @return
	 * @throws ApplicationException
	 */
	public boolean isLimitLoginCampaign(String userId, String baseDate) throws ApplicationException {
		// �L�����y�[�������擾����
		Campaign campaign = CampaignProvider.getInstance()
				.getCampaignByTargetDate(ServiceType.LOGINCAMPAIGN.getValue(), baseDate);

		// �L�����y�[�����ԓ��̓�����ƌ��ʂ̌������擾����
		int loginCampaignExecCount = DailyWorkResultDao.getInstance()
				.countByTargetDate(userId, campaign.getStartDate(), campaign.getEndDate(),
						ServiceType.LOGINCAMPAIGN.getValue(), Status.SUCCESS.getValue());

		// ���O�C���L�����y�[���̊e�}�b�v��
		int loginCampaignMapNum1 = Integer
				.parseInt(MasterDataManager.getInstance().get("redstone.logincampaign.map1.num"));
		int loginCampaignMapNum2 = Integer
				.parseInt(MasterDataManager.getInstance().get("redstone.logincampaign.map2.num"));
		int loginCampaignMapNum3 = Integer
				.parseInt(MasterDataManager.getInstance().get("redstone.logincampaign.map3.num"));

		return loginCampaignExecCount >= (loginCampaignMapNum1 + loginCampaignMapNum2 + loginCampaignMapNum3);
	}

	/**
	 * �ΏۃL�����y�[���̊l���A�C�e���̏W�v���ʂ��擾����
	 * 
	 * @param campaign �L�����y�[�����
	 * @param targetRewardItemList �W�v�Ώۂ̊l���A�C�e��
	 * @return �l���A�C�e���W�v���ʃ��X�g
	 */
	public List<Object[]> getRewardItemAggregateList(Campaign campaign, List<String> targetRewardItemList) {

		// �T�[�r�X���
		String serviceType = null;

		// �L�����y�[����ʂ��u1�F���O�C���L�����y�[���v�̏ꍇ
		if (ServiceType.LOGINCAMPAIGN.getValue().equals(campaign.getCampaignType())) {
			serviceType = ServiceType.LOGINCAMPAIGN.getValue();
		}
		// �L�����y�[����ʂ��u4�F���[���b�g�v�̏ꍇ
		else if (ServiceType.ROULETTE.getValue().equals(campaign.getCampaignType())) {
			serviceType = ServiceType.ROULETTE.getValue();
		}

		// �l���A�C�e���̏W�v���ʂ��擾����
		List<LinkedHashMap<String, Object>> rewardItemAggregateList = DailyWorkResultDao.getInstance()
				.findByRewardItemAggregate(
						campaign.getStartDate(), campaign.getEndDate(),
						serviceType, Status.SUCCESS.getValue(), targetRewardItemList);

		// �l���A�C�e���W�v���ʃ��X�g
		List<Object[]> data = new ArrayList<Object[]>();

		for (Map<String, Object> row : rewardItemAggregateList) {
			// �ꎞ�I�ȃ��X�g
			List<Object> tempList = new ArrayList<>();

			for (Map.Entry<String, Object> entry : row.entrySet()) {
				tempList.add(entry.getValue());
			}

			// �z��ɕϊ����Ċi�[����
			data.add(tempList.toArray());
		}

		return data;
	}

	/**
	 * ������ƌ��ʂ�o�^����
	 * 
	 * @param userId ���[�UID
	 */
	public void insertDailyWorkResult(DailyWorkResult dailyWorkResult) {
		DailyWorkResultDao.getInstance().insert(dailyWorkResult);
	}

	/**
	 * �w�肵��������ƌ��ʂ��폜����
	 * 
	 * @param userId ���[�UID
	 */
	public void deleteDailyWorkResult(String userId, String baseDate, String serviceType) {
		DailyWorkResultDao.getInstance().delete(userId, baseDate, serviceType);
	}

	/**
	 * ������ƌ��ʂ����ׂč폜����
	 */
	public void deleteDailyWorkResult() {
		DailyWorkResultDao.getInstance().delete();
	}
}
