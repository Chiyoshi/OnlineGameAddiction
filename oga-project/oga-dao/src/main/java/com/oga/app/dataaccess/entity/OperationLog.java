package com.oga.app.dataaccess.entity;

/**
 * 操作ログ
 */
public class OperationLog {

	/** ID */
	private String id;

	/** 操作者 */
	private String operator;

	/** 操作コード */
	private String operationCode;

	/** 操作日時 */
	private String operationDateTime;

	/** ログレベル */
	private String level;

	/** ログメッセージ */
	private String message;

	/** ユーザID */
	private String userId;

	/** 登録日 */
	private String registrationDate;

	/** 更新日 */
	private String updateDate;

	/** 削除フラグ */
	private String deleteFlg;

	/** 削除日 */
	private String deleteDate;

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id セットする id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator セットする operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return operationCode
	 */
	public String getOperationCode() {
		return operationCode;
	}

	/**
	 * @param operationCode セットする operationCode
	 */
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	/**
	 * @return operationDateTime
	 */
	public String getOperationDateTime() {
		return operationDateTime;
	}

	/**
	 * @param operationDateTime セットする operationDateTime
	 */
	public void setOperationDateTime(String operationDateTime) {
		this.operationDateTime = operationDateTime;
	}

	/**
	 * @return level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level セットする level
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
	 * @param message セットする message
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
	 * @param userId セットする userId
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
