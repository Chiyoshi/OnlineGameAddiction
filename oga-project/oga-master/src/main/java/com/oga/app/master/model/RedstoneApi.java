package com.oga.app.master.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * レッドストーン情報API
 */
@Value
@Builder
@Jacksonized
public class RedstoneApi {

	/** KEY */
	@JsonProperty("name")
	private String name;

	/** URL */
	@JsonProperty("url")
	private String url;

	/** 説明 */
	@JsonProperty("explanation")
	private String explanation;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}