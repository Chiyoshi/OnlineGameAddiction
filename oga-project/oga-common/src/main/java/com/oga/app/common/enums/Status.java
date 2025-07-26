package com.oga.app.common.enums;

public enum Status {

	/** ����I�� */
	SUCCESS("����I��", "0"),
	/** �ُ�I�� */
	ERROR("�ُ�I��", "1"),
	/** �X�L�b�v */
	SKIP("�X�L�b�v", "2");

	private final String name;

	private final String value;

	private Status(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	/**
	 * �X�e�[�^�X�R�[�h�_�������擾����
	 * 
	 * @param code �X�e�[�^�X�R�[�h 
	 * @return
	 */
	public static String getName(String value) {
		for (Status status : Status.values()) {
			if (value == status.getValue()) {
				return status.getName();
			}
		}
		return "";
	}
}
