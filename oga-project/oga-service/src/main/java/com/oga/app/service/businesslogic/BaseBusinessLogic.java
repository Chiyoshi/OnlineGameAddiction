package com.oga.app.service.businesslogic;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;

public abstract class BaseBusinessLogic<T> {

	// �g�����U�N�V�����Ǘ��p�̃��\�b�h
	public void execute(T param) throws Exception {

		try {
			// �g�����U�N�V�����̊J�n
			beginTransaction();

			// ServiceBean��ݒ肷��
			createServiceBean(param);

			// �f�[�^�擾����у`�F�b�N����
			checkSelectData();

			// �r�W�l�X���W�b�N�̏���
			doBusinessLogic();

			// �g�����U�N�V�����̃R�~�b�g
			commitTransaction();
		} catch (ApplicationException e) {
			// �G���[�n���h�����O
			handleError(e);

			// �g�����U�N�V�����̃��[���o�b�N
			rollbackTransaction();

			throw e;
		} catch (SystemException e) {
			throw e;
		}
	}

	/**
	 * ServiceBean��ݒ肷��
	 * �e�r�W�l�X���W�b�N�N���X���������ׂ����\�b�h
	 * 
	 * @param param serviceBean
	 * @throws ApplicationException
	 */
	protected abstract void createServiceBean(T param) throws ApplicationException;

	/**
	 * �f�[�^�擾����у`�F�b�N����
	 * �e�r�W�l�X���W�b�N�N���X���������ׂ����\�b�h
	 */
	protected abstract void checkSelectData() throws ApplicationException, SystemException;

	/**
	 * �r�W�l�X���W�b�N
	 * �e�r�W�l�X���W�b�N�N���X���������ׂ����\�b�h
	 */
	protected abstract void doBusinessLogic() throws ApplicationException, SystemException;

	/**
	 * �g�����U�N�V�����J�n
	 */
	protected void beginTransaction() {
		// �g�����U�N�V�����J�n����
//		System.out.println("Transaction started.");
	}

	/**
	 * �g�����U�N�V�����R�~�b�g
	 */
	protected void commitTransaction() {
		// �R�~�b�g����
//		System.out.println("Transaction committed.");
	}

	/**
	 * �g�����U�N�V�������[���o�b�N
	 */
	protected void rollbackTransaction() {
		// ���[���o�b�N����
//		System.out.println("Transaction rolled back.");
	}

	/**
	 * �G���[�n���h�����O���� 
	 * @param e
	 */
	protected void handleError(Exception e) {
		// ���O�o�͂�G���[�n���h�����O
//		System.err.println("Error occurred: " + e.getMessage());
//		e.printStackTrace();
	}
}
