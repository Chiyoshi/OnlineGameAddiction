package com.oga.app.dataaccess.entity;

/**
 * 設定情報Entity
 */
public class Setting extends BaseEntity {

	/** 設定キー */
	private String key;

	/** 値 */
	private String value;

	/** 説明 */
	private String explanation;

	/** 表示順序 */
	private int order;

	/** 登録日 */
	private String registrationDate;

	/** 更新日 */
	private String updateDate;

	/** 削除フラグ */
	private String deleteFlg;

	/** 削除日 */
	private String deleteDate;

	/**
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key セットする key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value セットする value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return explanation
	 */
	public String getExplanation() {
		return explanation;
	}

	/**
	 * @param explanation セットする explanation
	 */
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	/**
	 * @return order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order セットする order
	 */
	public void setOrder(int order) {
		this.order = order;
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
