package com.oga.app.dataaccess.entity;

/**
 * ユーザ情報
 */
public class User extends BaseEntity {

	/** ユーザID */
	private String userId;

	/** パスワード */
	private String password;

	/** 生年月日 */
	private String birthDay;

	/** メールアドレス */
	private String mailAddress;

	/** GEM */
	private String gem;

	/** サービスポイント */
	private String servicePoint;

	/** RED's point */
	private String redspoint;

	/** 登録日 */
	private String registrationDate;

	/** 更新日 */
	private String updateDate;

	/** 削除フラグ */
	private String deleteFlg;

	/** 削除日 */
	private String deleteDate;

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId セットする userId
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
	 * @param password セットする password
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
	 * @param birthDay セットする birthDay
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
	 * @param mailAddress セットする mailAddress
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
	 * @param gem セットする gem
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
	 * @param servicePoint セットする servicePoint
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
	 * @param redspoint セットする redspoint
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
	 * @param registrationDate セットする registrationDate
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
	 * @param updateDate セットする updateDate
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
	 * @param deleteFlg セットする deleteFlg
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
	 * @param deleteDate セットする deleteDate
	 */
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}
}
