package com.oga.app.dataaccess.entity;

/**
 * ���[�U���
 */
public class Calendar extends BaseEntity {

	/** ��� */
	private String baseDate;

	/** �j�� */
	private String dayOfWeek;

	/** ��������e�i���X�t���O */
	private String maintenanceFlg;

	/** ��������e�i���X�J�n���� */
	private String mtStartTime;

	/** ��������e�i���X�I������ */
	private String mtEndTime;

	/**
	 * @return baseDate
	 */
	public String getBaseDate() {
		return baseDate;
	}

	/**
	 * @param baseDate �Z�b�g���� baseDate
	 */
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	/**
	 * @return dayOfWeek
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek �Z�b�g���� dayOfWeek
	 */
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * @return maintenanceFlg
	 */
	public String getMaintenanceFlg() {
		return maintenanceFlg;
	}

	/**
	 * @param maintenanceFlg �Z�b�g���� maintenanceFlg
	 */
	public void setMaintenanceFlg(String maintenanceFlg) {
		this.maintenanceFlg = maintenanceFlg;
	}

	/**
	 * @return mtStartTime
	 */
	public String getMtStartTime() {
		return mtStartTime;
	}

	/**
	 * @param mtStartTime �Z�b�g���� mtStartTime
	 */
	public void setMtStartTime(String mtStartTime) {
		this.mtStartTime = mtStartTime;
	}

	/**
	 * @return mtEndTime
	 */
	public String getMtEndTime() {
		return mtEndTime;
	}

	/**
	 * @param mtEndTime �Z�b�g���� mtEndTime
	 */
	public void setMtEndTime(String mtEndTime) {
		this.mtEndTime = mtEndTime;
	}

}
