package com.oga.app.dataaccess.entity;

/**
 * ユーザ情報
 */
public class Calendar extends BaseEntity {

	/** 基準日 */
	private String baseDate;

	/** 曜日 */
	private String dayOfWeek;

	/** 定期メンテナンスフラグ */
	private String maintenanceFlg;

	/** 定期メンテナンス開始時間 */
	private String mtStartTime;

	/** 定期メンテナンス終了時間 */
	private String mtEndTime;

	/**
	 * @return baseDate
	 */
	public String getBaseDate() {
		return baseDate;
	}

	/**
	 * @param baseDate セットする baseDate
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
	 * @param dayOfWeek セットする dayOfWeek
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
	 * @param maintenanceFlg セットする maintenanceFlg
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
	 * @param mtStartTime セットする mtStartTime
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
	 * @param mtEndTime セットする mtEndTime
	 */
	public void setMtEndTime(String mtEndTime) {
		this.mtEndTime = mtEndTime;
	}

}
