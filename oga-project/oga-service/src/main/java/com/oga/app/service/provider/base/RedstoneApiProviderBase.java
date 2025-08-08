package com.oga.app.service.provider.base;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oga.app.common.exception.SystemException;
import com.oga.app.service.response.HttpTransactionLog;
import com.oga.app.service.response.redstoneapi.AccessToken;

public abstract class RedstoneApiProviderBase extends RedstoneProviderBase {

	/**
	 * レッドストーンAPIの戻り値JSONを読み取る
	 * 
	 * @param jsonString Json
	 * @param <T>
	 * @return
	 * @throws SystemException
	 */
	public <T> T loadJsonStringApiResponse(String jsonString, Class<T> valueType) throws SystemException {
		ObjectMapper mapper = new ObjectMapper();
//		SimpleModule module = new SimpleModule();
//		module.addDeserializer(ApiResponse.class, new ApiResponseDeserializer());
//		mapper.registerModule(module);

		T result = null;
		try {
			result = mapper.readValue(jsonString, valueType);
		} catch (JsonProcessingException e) {
			throw new SystemException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * リクエストパラメータ生成
	 * 
	 * @param accessToken アクセストークン
	 * @param httpTransactionLog ログイン時のHTTPリクエスト情報
	 * @return
	 */
	public Map<String, String> createRequestProperties(AccessToken accessToken, HttpTransactionLog httpTransactionLog) {
		Map<String, String> requestProperties = new HashMap<String, String>();

		StringBuilder sb = new StringBuilder();
		sb.append(accessToken.getAccessType()).append(" ").append(accessToken.getAccessToken());

		requestProperties.put("authorization", sb.toString());
		requestProperties.put("Content-Type", "application/json");
		requestProperties.put("User-Agent", httpTransactionLog.getUserAgent());
		requestProperties.put("Referer", httpTransactionLog.getReferer());
		requestProperties.put("sec-ch-ua", httpTransactionLog.getSecChUa());
		requestProperties.put("sec-ch-ua-platform", httpTransactionLog.getSecChUaPlatform());
		requestProperties.put("Access-Control-Allow-Origin", httpTransactionLog.getAccessControlAllowOrigin());

		return requestProperties;
	}
}
