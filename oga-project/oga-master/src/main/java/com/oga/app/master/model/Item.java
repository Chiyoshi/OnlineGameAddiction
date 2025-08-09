package com.oga.app.master.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Item {

	/** アイテムコード */
	@JsonProperty("item_code")
	private String itemCode;

	/** アイテム名 */
	@JsonProperty("item_name")
	private String itemName;

	/**
	 * @return itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}