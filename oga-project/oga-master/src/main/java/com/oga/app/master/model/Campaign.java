package com.oga.app.master.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * キャンペーン情報
 */
@Value
@Builder
@Jacksonized
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}