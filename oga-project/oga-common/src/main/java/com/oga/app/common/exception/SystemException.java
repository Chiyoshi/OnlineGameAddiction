package com.oga.app.common.exception;

public class SystemException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param message �G���[���b�Z�[�W
	 */
	public SystemException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param cause �G���[
	 */
	public SystemException(Throwable cause) {
		super(cause);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param message �G���[���b�Z�[�W
	 * @param cause   �G���[
	 */
	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
