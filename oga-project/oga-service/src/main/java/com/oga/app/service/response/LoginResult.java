package com.oga.app.service.response;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.oga.app.service.response.redstoneapi.AccessToken;

public class LoginResult {

	/** アクセストークン */
	private AccessToken accessToken;

	/** ログイン時のリクエスト情報 */
	private HttpTransactionLog httpTransactionLog;

	/**
	 * @return accessToken
	 */
	public AccessToken getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken セットする accessToken
	 */
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return httpTransactionLog
	 */
	public HttpTransactionLog getHttpTransactionLog() {
		return httpTransactionLog;
	}

	/**
	 * @param httpTransactionLog セットする httpTransactionLog
	 */
	public void setHttpTransactionLog(HttpTransactionLog httpTransactionLog) {
		this.httpTransactionLog = httpTransactionLog;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
