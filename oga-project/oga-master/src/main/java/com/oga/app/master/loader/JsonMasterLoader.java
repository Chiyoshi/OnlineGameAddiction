package com.oga.app.master.loader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oga.app.common.exception.SystemException;

public class JsonMasterLoader {

	/** ObjectMapper */
	private static final ObjectMapper mapper = new ObjectMapper();

	/** キャッシュ用 */
	private static final Map<String, Object> cache = new ConcurrentHashMap<>();

	/**
	 * インターネット上に保存されているマスタ情報を読み取る
	 * 
	 * @param <T> 対象マスタ
	 * @param url URL
	 * @param clazz 対象マスタ
	 * @param useCache キャッシュ利用有無
	 * @return
	 * @throws SystemException 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <T> T loadFromUrl(String url, Class<T> clazz, boolean useCache) throws SystemException {
		// 既に読み込み済みの場合はキャッシュから取得する
		if (useCache && cache.containsKey(url)) {
			return (T) cache.get(url);
		}

		T data = null;
		try {
			data = mapper.readValue(new URL(url), clazz);
		} catch (StreamReadException | DatabindException | MalformedURLException e) {
			throw new SystemException(e.getMessage(), e);
		} catch (IOException e) {
			throw new SystemException(e.getMessage(), e);
		}

		// キャッシュに保存にする
		if (useCache) {
			cache.put(url, data);
		}

		return data;
	}

	/**
	 * 指定したURLをキャッシュからクリアする
	 * @param url URL
	 */
	public static void clearCache(String url) {
		cache.remove(url);
	}

	/**
	 * キャッシュをクリアする
	 */
	public static void clearAllCache() {
		cache.clear();
	}
}
