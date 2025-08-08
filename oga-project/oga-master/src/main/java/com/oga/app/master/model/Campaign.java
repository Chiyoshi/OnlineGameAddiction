package com.oga.app.master.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * キャンペーン情報
 */
public class Campaign {

	/** キャンペーンID */
	@JsonProperty("campaign_id")
	private String campaignId;

	/** キャンペーン名 */
	@JsonProperty("campaign_name")
	private String campaignName;

	/** 開始日 */
	@JsonProperty("start_date")
	private String startDate;

	/** 終了日 */
	@JsonProperty("end_date")
	private String endDate;

	/**
	 * @return campaignId
	 */
	public String getCampaignId() {
		return campaignId;
	}

	/**
	 * @param campaignId セットする campaignId
	 */
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * @return campaignName
	 */
	public String getCampaignName() {
		return campaignName;
	}

	/**
	 * @param campaignName セットする campaignName
	 */
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	/**
	 * @return startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate セットする startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate セットする endDate
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}