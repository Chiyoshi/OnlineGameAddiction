package com.oga.app.dataaccess.entity;

/**
 * ���[�U���
 */
public class User extends BaseEntity {

	/** ���[�UID */
	private String userId;

	/** �p�X���[�h */
	private String password;

	/** ���N���� */
	private String birthDay;

	/** ���[���A�h���X */
	private String mailAddress;

	/** GEM */
	private String gem;

	/** �T�[�r�X�|�C���g */
	private String servicePoint;

	/** RED's point */
	private String redspoint;

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
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password �Z�b�g���� password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return birthDay
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay �Z�b�g���� birthDay
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * @return mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress �Z�b�g���� mailAddress
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return gem
	 */
	public String getGem() {
		return gem;
	}

	/**
	 * @param gem �Z�b�g���� gem
	 */
	public void setGem(String gem) {
		this.gem = gem;
	}

	/**
	 * @return servicePoint
	 */
	public String getServicePoint() {
		return servicePoint;
	}

	/**
	 * @param servicePoint �Z�b�g���� servicePoint
	 */
	public void setServicePoint(String servicePoint) {
		this.servicePoint = servicePoint;
	}

	/**
	 * @return redspoint
	 */
	public String getRedspoint() {
		return redspoint;
	}

	/**
	 * @param redspoint �Z�b�g���� redspoint
	 */
	public void setRedspoint(String redspoint) {
		this.redspoint = redspoint;
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
