package com.oga.app.dataaccess.entity;

/**
 * ������Ə��
 */
public class DailyWork extends BaseEntity {

	/** ���[�UID */
	private String userId;

	/** ���O�C���L�����y�[���t���O */
	private String loginCampaignFlg;

	/** DailyReward�t���O */
	private String dailyRewardFlg;

	/** ���[���b�g�t���O */
	private String rouletteFlg;

	/** �ŏI���O�C���L�����y�[�����s�� */
	private String lastLoginCampaignDate;

	/** �ŏIDailyReward���s�� */
	private String lastDailyRewardDate;

	/** �ŏI���[���b�g���s�� */
	private String lastRouletteDate;

	/** �o�^�� */
	private String registrationDate;

	/** �X�V�� */
	private String updateDate;

	/** �폜�t���O */
	private String deleteFlg;

	/** �폜�� */
	private String deleteDate;

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId �Z�b�g���� userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return loginCampaignFlg
	 */
	public String getLoginCampaignFlg() {
		return loginCampaignFlg;
	}

	/**
	 * @param loginCampaignFlg �Z�b�g���� loginCampaignFlg
	 */
	public void setLoginCampaignFlg(String loginCampaignFlg) {
		this.loginCampaignFlg = loginCampaignFlg;
	}

	/**
	 * @return dailyRewardFlg
	 */
	public String getDailyRewardFlg() {
		return dailyRewardFlg;
	}

	/**
	 * @param dailyRewardFlg �Z�b�g���� dailyRewardFlg
	 */
	public void setDailyRewardFlg(String dailyRewardFlg) {
		this.dailyRewardFlg = dailyRewardFlg;
	}

	/**
	 * @return rouletteFlg
	 */
	public String getRouletteFlg() {
		return rouletteFlg;
	}

	/**
	 * @param rouletteFlg �Z�b�g���� rouletteFlg
	 */
	public void setRouletteFlg(String rouletteFlg) {
		this.rouletteFlg = rouletteFlg;
	}

	/**
	 * @return lastLoginCampaignDate
	 */
	public String getLastLoginCampaignDate() {
		return lastLoginCampaignDate;
	}

	/**
	 * @param lastLoginCampaignDate �Z�b�g���� lastLoginCampaignDate
	 */
	public void setLastLoginCampaignDate(String lastLoginCampaignDate) {
		this.lastLoginCampaignDate = lastLoginCampaignDate;
	}

	/**
	 * @return lastDailyRewardDate
	 */
	public String getLastDailyRewardDate() {
		return lastDailyRewardDate;
	}

	/**
	 * @param lastDailyRewardDate �Z�b�g���� lastDailyRewardDate
	 */
	public void setLastDailyRewardDate(String lastDailyRewardDate) {
		this.lastDailyRewardDate = lastDailyRewardDate;
	}

	/**
	 * @return lastRouletteDate
	 */
	public String getLastRouletteDate() {
		return lastRouletteDate;
	}

	/**
	 * @param lastRouletteDate �Z�b�g���� lastRouletteDate
	 */
	public void setLastRouletteDate(String lastRouletteDate) {
		this.lastRouletteDate = lastRouletteDate;
	}

	/**
	 * @return registrationDate
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate �Z�b�g���� registrationDate
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate �Z�b�g���� updateDate
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return deleteFlg
	 */
	public String getDeleteFlg() {
		return deleteFlg;
	}

	/**
	 * @param deleteFlg �Z�b�g���� deleteFlg
	 */
	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	/**
	 * @return deleteDate
	 */
	public String getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @param deleteDate �Z�b�g���� deleteDate
	 */
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}

}
