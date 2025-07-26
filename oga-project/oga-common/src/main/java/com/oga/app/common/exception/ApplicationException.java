package com.oga.app.common.exception;

/**
 * �A�v���P�[�V�����G���[
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param message �G���[���b�Z�[�W
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param cause �G���[
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param message �G���[���b�Z�[�W
	 * @param cause   �G���[
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}
