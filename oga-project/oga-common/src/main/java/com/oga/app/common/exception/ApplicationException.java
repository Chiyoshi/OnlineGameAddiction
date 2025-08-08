package com.oga.app.common.exception;

/**
 * アプリケーションエラー
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * 
	 * @param message エラーメッセージ
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param cause エラー
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param message エラーメッセージ
	 * @param cause   エラー
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}
