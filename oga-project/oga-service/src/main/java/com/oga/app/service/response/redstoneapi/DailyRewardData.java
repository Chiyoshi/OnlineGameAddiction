package com.oga.app.service.response.redstoneapi;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class DailyRewardData {

	/** 残高情報 */
	@JsonProperty("wallet")
	private Wallet wallet;

	/** DailyReward獲得金額 */
	@JsonProperty("reward")
	private Reward reward;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
