package com.oga.app.service.response;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class HttpTransactionLog {

	/** リクエストURL */
	private String requestUrl;

	/** リクエストメソッド */
	private String requestMethod;

	/** リクエストパラメータ */
	private String requestPayload;

	/** リクエストヘッダー */
	private Map<String, Object> requestHeaders;

	/** レスポンス内容 */
	private String responseBody;

	/**
	 * @return requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * @param requestUrl セットする requestUrl
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * @return requestMethod
	 */
	public String getRequestMethod() {
		return requestMethod;
	}

	/**
	 * @param requestMethod セットする requestMethod
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * @return requestPayload
	 */
	public String getRequestPayload() {
		return requestPayload;
	}

	/**
	 * @param requestPayload セットする requestPayload
	 */
	public void setRequestPayload(String requestPayload) {
		this.requestPayload = requestPayload;
	}

	/**
	 * @return headers
	 */
	public Map<String, Object> getRequestHeaders() {
		return requestHeaders;
	}

	/**
	 * @param headers セットする headers
	 */
	public void setRequestHeaders(Map<String, Object> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	/**
	 * @return responseBody
	 */
	public String getResponseBody() {
		return responseBody;
	}

	/**
	 * @param responseBody セットする responseBody
	 */
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	/**
	 * リクエストヘッダー(sec-ch-ua-platform)を取得する
	 * 
	 * @return sec-ch-ua-platform
	 */
	public String getSecChUaPlatform() {
		Object secChUaPlatform = requestHeaders.get("sec-ch-ua-platform");
		return secChUaPlatform != null ? secChUaPlatform.toString() : null;
	}

	/**
	 * リクエストヘッダー(Referer)を取得する
	 * 
	 * @return Referer
	 */
	public String getReferer() {
		Object referer = requestHeaders.get("Referer");
		return referer != null ? referer.toString() : null;
	}

	/**
	 * リクエストヘッダー(sec-ch-ua)を取得する
	 * 
	 * @return sec-ch-ua
	 */
	public String getSecChUa() {
		Object secChUa = requestHeaders.get("sec-ch-ua");
		return secChUa != null ? secChUa.toString() : null;
	}

	/**
	 * リクエストヘッダー(User-Agent)を取得する
	 * 
	 * @return User-Agent
	 */
	public String getUserAgent() {
		Object userAgent = requestHeaders.get("User-Agent");
		return userAgent != null ? userAgent.toString() : null;
	}

	/**
	 * リクエストヘッダー(Access-Control-Allow-Origin)を取得する
	 * 
	 * @return Access-Control-Allow-Origin
	 */
	public String getAccessControlAllowOrigin() {
		Object accessControlAllowOrigin = requestHeaders.get("Access-Control-Allow-Origin");
		return accessControlAllowOrigin != null ? accessControlAllowOrigin.toString() : null;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
