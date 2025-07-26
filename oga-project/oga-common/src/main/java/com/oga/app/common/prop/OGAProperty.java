package com.oga.app.common.prop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.oga.app.common.utils.StringUtil;

/**
 * プロパティファイル(oga.properties)
 *
 */
public class OGAProperty {

	private static final Properties properties;

	/**
	 * コンストラクタ
	 */
	private OGAProperty() {
	}

	static {
		// プロパティファイルのパスを取得する
		String file = System.getProperty("prop.file");

		if (StringUtil.isNullOrEmpty(file)) {
			throw new RuntimeException("プロパティファイル(oga.properties)が設定されていません。");
		}

		properties = new Properties();

		try {
			// プロパティファイルを読み込む
			properties.load(Files.newBufferedReader(Paths.get(file), StandardCharsets.UTF_8));
		} catch (IOException e) {
			// ファイル読み込みに失敗
			throw new RuntimeException(String.format("プロパティファイルの読み込みに失敗しました。ファイル名:%s", file), e);
		}
	}

	/**
	 * プロパティ値を取得する
	 *
	 * @param key キー
	 * @return 値
	 */
	public static String getProperty(final String key) {
		return getProperty(key, "");
	}

	/**
	 * プロパティ値を取得する
	 *
	 * @param key          キー
	 * @param defaultValue デフォルト値
	 * @return キーが存在しない場合、デフォルト値 存在する場合、値
	 */
	public static String getProperty(final String key, final String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
}
