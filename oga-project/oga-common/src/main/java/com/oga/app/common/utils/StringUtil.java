package com.oga.app.common.utils;

public class StringUtil {

	/**
	 * ������null�܂��͋󂩂ǂ������`�F�b�N����
	 * 
	 * @param str ������
	 * @return True�F��AFalse�F��ȊO
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * ������null�܂��͋󂩂ǂ������`�F�b�N����
	 * 
	 * @param str ������
	 * @return True�F�󔒂̂݁AFalse�F�󔒈ȊO
	 */
	public static boolean isNullOrWhitespace(String str) {
		return str == null || str.trim().isEmpty();
	}

}
