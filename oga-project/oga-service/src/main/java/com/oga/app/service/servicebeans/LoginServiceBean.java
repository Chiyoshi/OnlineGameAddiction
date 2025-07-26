package com.oga.app.service.servicebeans;

import com.oga.app.common.enums.ServiceType;

/**
 * ユーザ情報
 */
public class LoginServiceBean extends BaseServiceBean {

	/** ユーザID */
	private String userId;

	/** パスワード */
	private String password;

	/** サービス種別 */
	private ServiceType serviceType;

	// ログインURL
	private String url;

	// ログイン後のURL
	private String loginAfterUrl;

	// ログイン後の画面表示待機に指定する文字列
	private String waitUntilTextDisplayed;

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
	 * @return serviceType
	 */
	public ServiceType getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType セットする serviceType
	 */
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url セットする url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return loginAfterUrl
	 */
	public String getLoginAfterUrl() {
		return loginAfterUrl;
	}

	/**
	 * @param loginAfterUrl セットする loginAfterUrl
	 */
	public void setLoginAfterUrl(String loginAfterUrl) {
		this.loginAfterUrl = loginAfterUrl;
	}

	/**
	 * @return waitUntilTextDisplayed
	 */
	public String getWaitUntilTextDisplayed() {
		return waitUntilTextDisplayed;
	}

	/**
	 * @param waitUntilTextDisplayed セットする waitUntilTextDisplayed
	 */
	public void setWaitUntilTextDisplayed(String waitUntilTextDisplayed) {
		this.waitUntilTextDisplayed = waitUntilTextDisplayed;
	}
}
