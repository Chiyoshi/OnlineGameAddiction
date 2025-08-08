package com.oga.app.master.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oga.app.master.model.Campaign;

public class CampaignDto {

	@JsonProperty("campaign")
	private List<Campaign> campaignList;

	/**
	 * @return campaignList
	 */
	public List<Campaign> getCampaignList() {
		return campaignList;
	}

	/**
	 * @param campaignList セットする campaignList
	 */
	public void setCampaignList(List<Campaign> campaignList) {
		this.campaignList = campaignList;
	}

}
