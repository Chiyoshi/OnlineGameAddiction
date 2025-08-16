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

	/** デフォルトエンコード */
	private static String DEFAULT_ENCODE_UTF8 = "UTF-8";

	/** GET */
	private static String REQUEST_METHOD_GET = "GET";

	/** POST */
	private static String REQUEST_METHOD_POST = "POST";

	/**
	 * GETリクエスト 
	 * 
	 * @param urlStr 接続先URL
	 * @return HTMLテキスト
	 */
	public static String sendGet(String urlStr) {
		return request(urlStr, REQUEST_METHOD_GET, null, null);
	}

	/**
	 * POSTリクエスト
	 * 
	 * @param urlStr 接続先URL
	 * @param requestProperties リクエストプロパティ
	 * @param jsonPayload JSONペイロード
	 * @return HTMLテキスト
	 */
	public static String sendPost(String urlStr, Map<String, String> requestProperties, String jsonPayload) {
		return request(urlStr, REQUEST_METHOD_POST, requestProperties, jsonPayload);
	}

	/**
	 * HTTPリクエスト送信
	 * 
	 * @param urlStr 接続先URL
	 * @param requestMethod HTTPリクエストメソッド
	 * @param requestProperties リクエストプロパティ
	 * @param formParams FORMパラメータ
	 * @return HTMLテキスト
	 */
	@SuppressWarnings("deprecation")
	private static String request(String urlStr, String requestMethod, Map<String, String> requestProperties,
			String jsonPayload) {

		StringBuilder sb = new StringBuilder();

		HttpURLConnection con = null;
		BufferedInputStream in = null;

		try {
			URL url = new URL(urlStr);

			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(requestMethod);

			// リクエストプロパティを設定する
			for (Map.Entry<String, String> entry : requestProperties.entrySet()) {
				con.setRequestProperty(entry.getKey(), entry.getValue());
			}

			// JSONペイロード
			if (REQUEST_METHOD_POST.equals(requestMethod) && !StringUtil.isNullOrEmpty(jsonPayload)) {
				con.setDoOutput(true);
				try (OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream())) {
					osw.write(jsonPayload);
				}
			}

			// URL接続
			con.connect();

			// HTTPレスポンスコードを取得する
			int status = con.getResponseCode();

			if (HttpURLConnection.HTTP_OK == status || HttpURLConnection.HTTP_ACCEPTED == status) {
				in = new BufferedInputStream(con.getInputStream());

				// 文字コードを取得する
				String encoding = con.getContentEncoding();

				if (StringUtil.isNullOrEmpty(encoding)) {
					encoding = DEFAULT_ENCODE_UTF8;
				}

				// レスポンスを取得する
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
	 * FORMパラメータ作成
	 * 
	 * @param map FORMパラメータが格納されたMap
	 * @return FORMパラメータの文字列
	 */
	private static String createFormParameter(Map<String, Object> map) {
		return convertMap2KeyValueStr(map, "&");
	}

	/**
	 * Mapに格納されたKey、ValueをKey=Value形式の文字列に変換する
	 * 
	 * @param map FORMパラメータが格納されたMap
	 * @param delimiter 区切り文字
	 * @return FORMパラメータの文字列
	 */
	private static String convertMap2KeyValueStr(Map<String, Object> map, String delimiter) {
		StringBuilder sb = new StringBuilder();
		boolean isFirstParam = true;
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (!isFirstParam) {
				sb.append(delimiter);
			}

			sb.append(entry.getKey()).append("=").append(entry.getValue());
			isFirstParam = false;
		}
		return sb.toString();
	}

}
