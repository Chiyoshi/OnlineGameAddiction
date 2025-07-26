package com.oga.app.service.servicebeans;

import com.oga.app.common.enums.ServiceType;

/**
 * ���[�U���
 */
public class LoginServiceBean extends BaseServiceBean {

	/** ���[�UID */
	private String userId;

	/** �p�X���[�h */
	private String password;

	/** �T�[�r�X��� */
	private ServiceType serviceType;

	// ���O�C��URL
	private String url;

	// ���O�C�����URL
	private String loginAfterUrl;

	// ���O�C����̉�ʕ\���ҋ@�Ɏw�肷�镶����
	private String waitUntilTextDisplayed;

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
	 * @return serviceType
	 */
	public ServiceType getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType �Z�b�g���� serviceType
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
	 * @param url �Z�b�g���� url
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
	 * @param loginAfterUrl �Z�b�g���� loginAfterUrl
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
	 * @param waitUntilTextDisplayed �Z�b�g���� waitUntilTextDisplayed
	 */
	public void setWaitUntilTextDisplayed(String waitUntilTextDisplayed) {
		this.waitUntilTextDisplayed = waitUntilTextDisplayed;
	}
}
