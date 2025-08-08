package com.oga.app.common.utils;

public class StringUtil {

	/**
	 * 文字列がnullまたは空かどうかをチェックする
	 * 
	 * @param str 文字列
	 * @return True：空、False：空以外
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * 文字列がnullまたは空かどうかをチェックする
	 * 
	 * @param str 文字列
	 * @return True：空白のみ、False：空白以外
	 */
	public static boolean isNullOrWhitespace(String str) {
		return str == null || str.trim().isEmpty();
	}

}
