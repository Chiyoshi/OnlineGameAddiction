package com.oga.app.dataaccess.entity;

/**
 * ���샍�O
 */
public class OperationLog {

	/** ID */
	private String id;

	/** ����R�[�h */
	private String operationCode;

	/** ������t */
	private String operationDate;

	/** ���O���x�� */
	private String level;

	/** ���O���b�Z�[�W */
	private String message;

	/** ���[�UID */
	private String userId;

	/** �o�^�� */
	private String registrationDate;

	/** �X�V�� */
	private String updateDate;

	/** �폜�t���O */
	private String deleteFlg;

	/** �폜�� */
	private String deleteDate;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id �Z�b�g���� id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return operationCode
	 */
	public String getOperationCode() {
		return operationCode;
	}

	/**
	 * @param operationCode �Z�b�g���� operationCode
	 */
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	/**
	 * @return operationDate
	 */
	public String getOperationDate() {
		return operationDate;
	}

	/**
	 * @param operationDate �Z�b�g���� operationDate
	 */
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	/**
	 * @return level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level �Z�b�g���� level
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message �Z�b�g���� message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

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
