package com.oga.app.service.response.redstoneapi;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class LoginCampaignData {

	/** cdl */
	@JsonProperty("cdl")
	private String cdl;

	/** ログインキャンペーン獲得アイテム */
	@JsonProperty("rewards")
	private List<Integer> rewards;

	/** ログインキャンペーン獲得状態 */
	@JsonProperty("state")
	private State state;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
