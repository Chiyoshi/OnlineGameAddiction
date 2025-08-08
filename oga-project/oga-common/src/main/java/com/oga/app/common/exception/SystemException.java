package com.oga.app.common.exception;

public class SystemException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * 
	 * @param message エラーメッセージ
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param cause エラー
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param message エラーメッセージ
	 * @param cause   エラー
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
