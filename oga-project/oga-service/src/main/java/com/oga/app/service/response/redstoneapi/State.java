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
public class State {

	/** usn */
	@JsonProperty("usn")
	private String usn;

	/** シーズン */
	@JsonProperty("season")
	private String targetMonth;

	/** ステージ */
	@JsonProperty("stage")
	private String stage;

	/** コンプリート獲得アイテム(Chapter1) */
	@JsonProperty("s1")
	private String completeItemChapter1;

	/** コンプリート獲得アイテム(Chapter2) */
	@JsonProperty("s2")
	private String completeItemChapter2;

	/** コンプリート獲得アイテム(Chapter3) */
	@JsonProperty("s3")
	private String completeItemChapter3;

	/** 獲得アイテム一覧 */
	@JsonProperty("gets")
	private String rewardItems;

	/** 更新日 */
	@JsonProperty("updt")
	private String updateDate;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
