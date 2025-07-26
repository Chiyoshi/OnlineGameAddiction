package com.oga.app.dataaccess.entity;

/**
 * ������ƌ��ʏ��
 */
public class DailyWorkResult extends BaseEntity {

	/** ���[�UID */
	private String userId;

	/** ��� */
	private String baseDate;

	/** �T�[�r�X��� */
	private String serviceType;

	/** �X�e�[�^�X */
	private String status;

	/** �l���A�C�e�� */
	private String rewardItem;

	/** �l���A�C�e���摜�p�X */
	private String rewardItemImage;

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
	 * @return baseDate
	 */
	public String getBaseDate() {
		return baseDate;
	}

	/**
	 * @param baseDate �Z�b�g���� baseDate
	 */
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	/**
	 * @return serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType �Z�b�g���� serviceType
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status �Z�b�g���� status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return rewardItem
	 */
	public String getRewardItem() {
		return rewardItem;
	}

	/**
	 * @param rewardItem �Z�b�g���� rewardItem
	 */
	public void setRewardItem(String rewardItem) {
		this.rewardItem = rewardItem;
	}

	/**
	 * @return rewardItemImage
	 */
	public String getRewardItemImage() {
		return rewardItemImage;
	}

	/**
	 * @param rewardItemImage �Z�b�g���� rewardItemImage
	 */
	public void setRewardItemImage(String rewardItemImage) {
		this.rewardItemImage = rewardItemImage;
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
