package com.oga.app.dataaccess.dto;

/**
 * ���[���b�g�l���A�C�e��DTO
 */
public class RouletteRewardItemDto {

	/** ���[�UID */
	private String userId;

	/** �l���A�C�e�� */
	private String rewardItem;

	/** �l���� */
	private int count;

	/**
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId �Z�b�g���� userId
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
	 * @param rewardItem �Z�b�g���� rewardItem
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
	 * @param count �Z�b�g���� count
	 */
	public void setCount(int count) {
		this.count = count;
	}
}
