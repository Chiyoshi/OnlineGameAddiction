package com.oga.app.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ���O�o��
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
	 * �X�^�b�N�g���[�X���g�p���ČĂяo�����̃N���X�����擾����
	 * 
	 * @return �N���X��
	 */
	private static String getCallingClassName() {
		// �X�^�b�N�g���[�X���擾
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		// �X�^�b�N�g���[�X�̃C���f�b�N�X3�ɂ͌Ăяo�����̃N���X������
		return stackTrace[3].getClassName();
	}

	/**
	 * ���ϐ��Ƀ��[�UID��ݒ肷��
	 * 
	 * @param userId ���[�UID
	 */
	public static void setPropertyUserId(String userId) {
		System.setProperty("userid", userId);
	}

	/**
	 * ���ϐ��Ƀ��[�UID��ݒ肷��
	 * 
	 * @param userId ���[�UID
	 */
	public static void removePropertyUserId() {
		System.clearProperty("userid");
	}
}
