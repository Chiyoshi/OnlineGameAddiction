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
public class Wallet {

	/** ユーザID(内部データ) */
	@JsonProperty("usn")
	private String usn;

	/** GEM */
	@JsonProperty("coin")
	private String gem;

	/** bonus */
	@JsonProperty("bonus")
	private String bonus;

	/** RED's point */
	@JsonProperty("mileage")
	private String redsPoint;

	/** サービスポイント */
	@JsonProperty("point")
	private String servicePoint;

	/** スクラッチポイント */
	@JsonProperty("scratch")
	private String scratch;

	/** アカウント作成日 */
	@JsonProperty("indt")
	private String accuntOpenedDate;

	/** payed */
	@JsonProperty("payed")
	private String payed;

	/** paymax */
	@JsonProperty("paymax")
	private String paymax;

	/** paylimit */
	@JsonProperty("paylimit")
	private String paylimit;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
