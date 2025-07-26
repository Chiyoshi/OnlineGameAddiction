package com.oga.app.dataaccess.entity;

/**
 * 日次作業情報
 */
public class DailyWork extends BaseEntity {

	/** ユーザID */
	private String userId;

	/** ログインキャンペーンフラグ */
	private String loginCampaignFlg;

	/** DailyRewardフラグ */
	private String dailyRewardFlg;

	/** ルーレットフラグ */
	private String rouletteFlg;

	/** 最終ログインキャンペーン実行日 */
	private String lastLoginCampaignDate;

	/** 最終DailyReward実行日 */
	private String lastDailyRewardDate;

	/** 最終ルーレット実行日 */
	private String lastRouletteDate;

	/** 登録日 */
	private String registrationDate;

	/** 更新日 */
	private String updateDate;

	/** 削除フラグ */
	private String deleteFlg;

	/** 削除日 */
	private String deleteDate;

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId セットする userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return loginCampaignFlg
	 */
	public String getLoginCampaignFlg() {
		return loginCampaignFlg;
	}

	/**
	 * @param loginCampaignFlg セットする loginCampaignFlg
	 */
	public void setLoginCampaignFlg(String loginCampaignFlg) {
		this.loginCampaignFlg = loginCampaignFlg;
	}

	/**
	 * @return dailyRewardFlg
	 */
	public String getDailyRewardFlg() {
		return dailyRewardFlg;
	}

	/**
	 * @param dailyRewardFlg セットする dailyRewardFlg
	 */
	public void setDailyRewardFlg(String dailyRewardFlg) {
		this.dailyRewardFlg = dailyRewardFlg;
	}

	/**
	 * @return rouletteFlg
	 */
	public String getRouletteFlg() {
		return rouletteFlg;
	}

	/**
	 * @param rouletteFlg セットする rouletteFlg
	 */
	public void setRouletteFlg(String rouletteFlg) {
		this.rouletteFlg = rouletteFlg;
	}

	/**
	 * @return lastLoginCampaignDate
	 */
	public String getLastLoginCampaignDate() {
		return lastLoginCampaignDate;
	}

	/**
	 * @param lastLoginCampaignDate セットする lastLoginCampaignDate
	 */
	public void setLastLoginCampaignDate(String lastLoginCampaignDate) {
		this.lastLoginCampaignDate = lastLoginCampaignDate;
	}

	/**
	 * @return lastDailyRewardDate
	 */
	public String getLastDailyRewardDate() {
		return lastDailyRewardDate;
	}

	/**
	 * @param lastDailyRewardDate セットする lastDailyRewardDate
	 */
	public void setLastDailyRewardDate(String lastDailyRewardDate) {
		this.lastDailyRewardDate = lastDailyRewardDate;
	}

	/**
	 * @return lastRouletteDate
	 */
	public String getLastRouletteDate() {
		return lastRouletteDate;
	}

	/**
	 * @param lastRouletteDate セットする lastRouletteDate
	 */
	public void setLastRouletteDate(String lastRouletteDate) {
		this.lastRouletteDate = lastRouletteDate;
	}

	/**
	 * @return registrationDate
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate セットする registrationDate
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate セットする updateDate
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return deleteFlg
	 */
	public String getDeleteFlg() {
		return deleteFlg;
	}

	/**
	 * @param deleteFlg セットする deleteFlg
	 */
	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	/**
	 * @return deleteDate
	 */
	public String getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @param deleteDate セットする deleteDate
	 */
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}

}
