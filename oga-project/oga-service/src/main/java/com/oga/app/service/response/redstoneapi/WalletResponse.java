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
public class WalletResponse {

	@JsonProperty("done")
	private boolean done;

	@JsonProperty("packet")
	private Wallet wallet;

	/** エラー情報 */
	@JsonProperty("error")
	private ErrorInfo errorInfo;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
