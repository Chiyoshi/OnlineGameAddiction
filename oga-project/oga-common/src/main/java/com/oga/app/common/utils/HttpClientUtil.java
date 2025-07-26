package com.oga.app.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class HttpClientUtil {

	/** �f�t�H���g�G���R�[�h */
	private static String DEFAULT_ENCODE_UTF8 = "UTF-8";

	/** GET */
	private static String REQUEST_METHOD_GET = "GET";

	/** POST */
	private static String REQUEST_METHOD_POST = "POST";

	/**
	 * GET���N�G�X�g 
	 * 
	 * @param urlStr �ڑ���URL
	 * @return HTML�e�L�X�g
	 */
	public static String sendGet(String urlStr) {
		return request(urlStr, REQUEST_METHOD_GET, null, null);
	}

	/**
	 * POST���N�G�X�g
	 * 
	 * @param urlStr �ڑ���URL
	 * @param requestProperties ���N�G�X�g�v���p�e�B
	 * @param formParams FORM�p�����[�^
	 * @return HTML�e�L�X�g
	 */
	public static String sendPost(String urlStr, Map<String, String> requestProperties, Map<String, String> formParams) {
		return request(urlStr, REQUEST_METHOD_POST, requestProperties, formParams);
	}

	/**
	 * HTTP���N�G�X�g���M
	 * 
	 * @param urlStr �ڑ���URL
	 * @param requestMethod HTTP���N�G�X�g���\�b�h
	 * @param requestProperties ���N�G�X�g�v���p�e�B
	 * @param formParams FORM�p�����[�^
	 * @return HTML�e�L�X�g
	 */
	@SuppressWarnings("deprecation")
	private static String request(String urlStr, String requestMethod, Map<String, String> requestProperties, Map<String, String> formParams) {

		StringBuilder sb = new StringBuilder();

		HttpURLConnection con = null;
		BufferedInputStream in = null;

		try {
			URL url = new URL(urlStr);

			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(requestMethod);

			// ���N�G�X�g�v���p�e�B��ݒ肷��
			for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
				con.setRequestProperty(entry.getKey(), entry.getValue());
			}

			// FORM�p�����[�^��ݒ肷��
			if (REQUEST_METHOD_POST.equals(requestMethod) && formParams != null) {
				// URL�ڑ��Ƀf�[�^���������݂ł���悤�ɂ���
				con.setDoOutput(true);
				// �f�[�^��������
				try (OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream())) {
					osw.write(createFormParameter(formParams));
				}
			}

			// URL�ڑ�
			con.connect();

			// HTTP���X�|���X�R�[�h���擾����
			int status = con.getResponseCode();

			if (HttpURLConnection.HTTP_OK == status || HttpURLConnection.HTTP_ACCEPTED == status) {
				in = new BufferedInputStream(con.getInputStream());

				// �����R�[�h���擾����
				String encoding = con.getContentEncoding();

				if (StringUtil.isNullOrEmpty(encoding)) {
					encoding = DEFAULT_ENCODE_UTF8;
				}

				// ���X�|���X���擾����
				try (BufferedReader br = new BufferedReader(new InputStreamReader(in, encoding))) {
					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
				}

			} else {
				LogUtil.error("Http request error. response code = " + status);
			}

		} catch (ProtocolException e) {
			LogUtil.error(e.getMessage(), e);
		} catch (MalformedURLException e) {
			LogUtil.error(e.getMessage(), e);
		} catch (IOException e) {
			LogUtil.error(e.getMessage(), e);
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}

		return sb.toString();
	}

	/**
	 * FORM�p�����[�^�쐬
	 * 
	 * @param map FORM�p�����[�^���i�[���ꂽMap
	 * @return FORM�p�����[�^�̕�����
	 */
	private static String createFormParameter(Map<String, String> map) {
		return convertMap2KeyValueStr(map, "&");
	}

	/**
	 * Map�Ɋi�[���ꂽKey�AValue��Key=Value�`���̕�����ɕϊ�����
	 * 
	 * @param map FORM�p�����[�^���i�[���ꂽMap
	 * @param delimiter ��؂蕶��
	 * @return FORM�p�����[�^�̕�����
	 */
	private static String convertMap2KeyValueStr(Map<String, String> map, String delimiter) {
		StringBuilder sb = new StringBuilder();
		boolean isFirstParam = true;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (!isFirstParam) {
				sb.append(delimiter);
			}

			sb.append(entry.getKey()).append("=").append(entry.getValue());
			isFirstParam = false;
		}
		return sb.toString();
	}

}
