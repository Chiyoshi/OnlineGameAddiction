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
public class AccessToken {
	/** アクセスタイプ */
	@JsonProperty("access_type")
	private String accessType;

	/** アクセストークン */
	@JsonProperty("access_token")
	private String accessToken;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
