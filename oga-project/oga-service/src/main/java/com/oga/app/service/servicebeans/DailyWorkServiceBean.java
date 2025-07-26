package com.oga.app.service.servicebeans;

/**
 * ユーザ情報
 */
public class DailyWorkServiceBean extends BaseServiceBean {

	/** ユーザID */
	private String userId;

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
}
