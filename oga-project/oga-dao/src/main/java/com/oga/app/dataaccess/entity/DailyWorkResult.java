package com.oga.app.dataaccess.entity;

/**
 * 日次作業結果情報
 */
public class DailyWorkResult extends BaseEntity {

	/** ユーザID */
	private String userId;

	/** 基準日 */
	private String baseDate;

	/** サービス種別 */
	private String serviceType;

	/** ステータス */
	private String status;

	/** 獲得アイテム */
	private String rewardItem;

	/** 獲得アイテム画像パス */
	private String rewardItemImage;

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
	 * @return serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType セットする serviceType
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status セットする status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return rewardItem
	 */
	public String getRewardItem() {
		return rewardItem;
	}

	/**
	 * @param rewardItem セットする rewardItem
	 */
	public void setRewardItem(String rewardItem) {
		this.rewardItem = rewardItem;
	}

	/**
	 * @return rewardItemImage
	 */
	public String getRewardItemImage() {
		return rewardItemImage;
	}

	/**
	 * @param rewardItemImage セットする rewardItemImage
	 */
	public void setRewardItemImage(String rewardItemImage) {
		this.rewardItemImage = rewardItemImage;
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
