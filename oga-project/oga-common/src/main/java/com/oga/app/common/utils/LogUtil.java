package com.oga.app.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ログ出力
 */
public class LogUtil {

	public static void info(String message) {
		Logger logger = LogManager.getLogger(getCallingClassName());
		logger.info(message);
	}

	public static void debug(String message) {
		Logger logger = LogManager.getLogger(getCallingClassName());
		logger.debug(message);
	}

	public static void warn(String message) {
		Logger logger = LogManager.getLogger(getCallingClassName());
		logger.warn(message);
	}

	public static void error(String message) {
		Logger logger = LogManager.getLogger(getCallingClassName());
		logger.error(message);
	}

	public static void error(String message, Throwable throwable) {
		Logger logger = LogManager.getLogger(getCallingClassName());
		logger.error(message, throwable);
	}

	/**
	 * スタックトレースを使用して呼び出し元のクラス名を取得する
	 * 
	 * @return クラス名
	 */
	private static String getCallingClassName() {
		// スタックトレースを取得
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		// スタックトレースのインデックス3には呼び出し元のクラスがある
		return stackTrace[3].getClassName();
	}

	/**
	 * 環境変数にユーザIDを設定する
	 * 
	 * @param userId ユーザID
	 */
	public static void setPropertyUserId(String userId) {
		System.setProperty("userid", userId);
	}

	/**
	 * 環境変数にユーザIDを設定する
	 * 
	 * @param userId ユーザID
	 */
	public static void removePropertyUserId() {
		System.clearProperty("userid");
	}
}
