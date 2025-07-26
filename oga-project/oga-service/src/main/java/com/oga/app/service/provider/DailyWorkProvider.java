package com.oga.app.service.provider;

import java.util.ArrayList;
import java.util.List;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.dataaccess.dao.DailyWorkDao;
import com.oga.app.dataaccess.entity.DailyWork;

/**
 * ������Ə��v���p�C�_
 */
public class DailyWorkProvider {

	/** ������Ə��v���p�C�_ */
	private static DailyWorkProvider dailyWorkProvider;

	/**
	 * �R���X�g���N�^
	 */
	public DailyWorkProvider() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized DailyWorkProvider getInstance() {
		if (dailyWorkProvider == null) {
			dailyWorkProvider = new DailyWorkProvider();
		}
		return dailyWorkProvider;
	}

	/**
	 * ������Ƃ̑Ώێ҃��X�g���擾����
	 * 
	 * @return
	 * @throws SystemException 
	 */
	public List<DailyWork> getTargetDailyWorklList(String baseDate) throws ApplicationException, SystemException {

		List<DailyWork> targetDailyWorkList = new ArrayList<>();

		// �L�����y�[���̊J�Ï󋵂��擾
		CampaignProvider campaignProvider = CampaignProvider.getInstance();
		boolean isNowLoginCampaign = campaignProvider.isNowCampaign(ServiceType.LOGINCAMPAIGN.getValue(), baseDate);
		boolean isNowRoulette = campaignProvider.isNowCampaign(ServiceType.ROULETTE.getValue(), baseDate);

		// ������Ə����擾
		List<DailyWork> dailyWorksList = this.getDailyWork();
		DailyWorkResultProvider resultProvider = DailyWorkResultProvider.getInstance();

		// ������Ƃ̏����Ώێ҂��擾����
		for (DailyWork dailyWork : dailyWorksList) {
			 // ������
			boolean shouldExecute = false;

			// ���O�C���L�����y�[�����J�Ò��A���A���O�C���L�����y�[���t���O���uY�v�̏ꍇ
			if (isNowLoginCampaign && YesNo.YES.getValue().equals(dailyWork.getLoginCampaignFlg())) {
				try {
					shouldExecute = !resultProvider.isLimitLoginCampaign(dailyWork.getUserId(), baseDate);
				} catch (ApplicationException e) {
					throw e;
				}

				shouldExecute = shouldExecute && resultProvider.isExecutionNeeded(dailyWork.getUserId(), baseDate,
						ServiceType.LOGINCAMPAIGN.getValue());
			}

			// ���[���b�g���J�Ò��A���A���[���b�g�t���O���uY�v�̏ꍇ
			if (isNowRoulette && YesNo.YES.getValue().equals(dailyWork.getRouletteFlg())) {
				shouldExecute = resultProvider.isExecutionNeeded(dailyWork.getUserId(), baseDate,
						ServiceType.ROULETTE.getValue());
			}

			// �f�C���[�����[�h�t���O���uY�v�̏ꍇ
			if (YesNo.YES.getValue().equals(dailyWork.getDailyRewardFlg())) {
				shouldExecute = resultProvider.isExecutionNeeded(dailyWork.getUserId(), baseDate,
						ServiceType.DAILYREWARD.getValue());
			}

			if (shouldExecute) {
				targetDailyWorkList.add(dailyWork);
			}
		}

		return targetDailyWorkList;
	}

	/**
	 * ������Ə����擾����
	 * @return
	 */
	public DailyWork getDailyWork(String userId) {
		return DailyWorkDao.getInstance().findByPKey(userId);
	}

	/**
	 * ������Ə����擾����
	 * @return
	 */
	public List<DailyWork> getDailyWork() {
		return DailyWorkDao.getInstance().findAllWithUser();
	}

	/**
	 * ������Ə���o�^����
	 * 
	 * @param userId ���[�UID
	 */
	public void insertDailyWork(DailyWork dailyWork) {
		DailyWorkDao.getInstance().insert(dailyWork);
	}

	/**
	 * �w�肵��������Ə����폜����
	 * 
	 * @param userId ���[�UID
	 */
	public void deleteDailyWork(String userId) {
		DailyWorkDao.getInstance().delete(userId);
	}

	/**
	 * ������Ə������ׂč폜����
	 */
	public void deleteDailyWork() {
		DailyWorkDao.getInstance().delete();
	}
}
