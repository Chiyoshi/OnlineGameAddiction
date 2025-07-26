package com.oga.app.dataaccess.dto;

/**
 * ルーレット獲得アイテムDTO
 */
public class RouletteRewardItemDto {

	/** ユーザID */
	private String userId;

	/** 獲得アイテム */
	private String rewardItem;

	/** 獲得個数 */
	private int count;

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
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count セットする count
	 */
	public void setCount(int count) {
		this.count = count;
	}
}
