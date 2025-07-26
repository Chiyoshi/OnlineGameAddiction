package com.oga.app.service.provider;

import java.util.List;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.dataaccess.dao.CampaignDao;
import com.oga.app.dataaccess.entity.Campaign;

/**
 * �L�����y�[�����v���p�C�_
 */
public class CampaignProvider {

	/** �L�����y�[�����v���p�C�_ */
	private static CampaignProvider masterProvider;

	/**
	 * �R���X�g���N�^
	 */
	public CampaignProvider() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized CampaignProvider getInstance() {
		if (masterProvider == null) {
			masterProvider = new CampaignProvider();
		}
		return masterProvider;
	}

	/**
	 * �L�����y�[�������擾����
	 * 
	 * @param campaignId �L�����y�[��ID
	 * @return �L�����y�[�����
	 */
	public Campaign getCampaign(String campaignId) {
		return CampaignDao.getInstance().findByPKey(campaignId);
	}

	/**
	 * �L�����y�[����ʁA�Ώۓ����w�肵�ăL�����y�[�������擾����
	 * 
	 * @param campaignType �L�����y�[�����
	 * @param targetDate �Ώۓ�
	 * @return �L�����y�[�����
	 * @throws ApplicationException 
	 */
	public Campaign getCampaignByTargetDate(String campaignType, String targetDate) throws ApplicationException {
		List<Campaign> campaignList = this.getCampaignList(campaignType, targetDate);

		// �L�����y�[����񂪓o�^����Ă��Ȃ��ꍇ�̓G���[�Ƃ���
		if (campaignList == null || campaignList.isEmpty()) {
			throw new ApplicationException("�L�����y�[����񂪖��o�^�A�܂��́A���ԑΏۊO�ł��B");
		}
		// �L�����y�[�����2�ȏ㑶�݂���ꍇ�͑z��O�̂��߃G���[�Ƃ���
		else if (campaignList.size() > 1) {
			throw new ApplicationException("�L�����y�[�����̑Ώۊ��Ԃ��d�����ēo�^����Ă��܂��B");
		}

		return campaignList.get(0);
	}

	/**
	 * �L�����y�[����ʁA�Ώۓ����w�肵�ăL�����y�[�������擾����
	 * 
	 * @param campaignType �L�����y�[�����
	 * @param targetDate �Ώۓ�
	 * @return �L�����y�[�����
	 */
	public List<Campaign> getCampaignList(String campaignType, String targetDate) {
		return CampaignDao.getInstance().findByCampaignDate(campaignType, targetDate);
	}

	/**
	 * �ΏۃL�����y�[�����J�Ò����ۂ����f����
	 * 
	 * @param campaignType �L�����y�[�����
	 * @param targetDate �Ώۓ�
	 * @return
	 */
	public boolean isNowCampaign(String campaignType, String targetDate) {
		List<Campaign> campaignList = this.getCampaignList(campaignType, targetDate);
		return campaignList != null && !campaignList.isEmpty();
	}
	
	/**
	 * �L�����y�[����񃊃X�g���擾����
	 * @return
	 */
	public List<Campaign> getCampaignList() {
		return CampaignDao.getInstance().findAll();
	}

	/**
	 * �L�����y�[������o�^����
	 * 
	 * @param campaign �L�����y�[�����
	 */
	public void insertCampaign(Campaign campaign) {
		CampaignDao.getInstance().insert(campaign);
	}

	/**
	 * �w�肵���L�����y�[�������폜����
	 * 
	 * @param campaignId �L�����y�[��ID
	 */
	public void deleteCampaign(String campaignId) {
		CampaignDao.getInstance().delete(campaignId);
	}

	/**
	 * �L�����y�[���������ׂč폜����
	 */
	public void deleteCampaign() {
		CampaignDao.getInstance().delete();
	}
}
