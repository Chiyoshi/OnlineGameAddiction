package com.oga.app.dataaccess.entity;

/**
 * �ݒ���Entity
 */
public class Setting extends BaseEntity {

	/** �ݒ�L�[ */
	private String key;

	/** �l */
	private String value;

	/** ���� */
	private String explanation;

	/** �\������ */
	private int order;

	/** �o�^�� */
	private String registrationDate;

	/** �X�V�� */
	private String updateDate;

	/** �폜�t���O */
	private String deleteFlg;

	/** �폜�� */
	private String deleteDate;

	/**
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key �Z�b�g���� key
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
	 * @param value �Z�b�g���� value
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
	 * @param explanation �Z�b�g���� explanation
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
	 * @param order �Z�b�g���� order
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
