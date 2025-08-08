package com.oga.app.dataaccess.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Builder;
import lombok.Data;

/**
 * ルーレット詳細
 */
@Data
@Builder
public class RouletteHistory {

	/** ユーザID */
	private String userId;

	/** キャンペーンID */
	private String campaignId;

	/** 対象日 */
	private String targetDate;

	/** 獲得アイテム */
	private String rewardItem;

	/** 登録日 */
	private String registrationDate;

	/** 更新日 */
	private String updateDate;

	/** 削除フラグ */
	private String deleteFlg;

	/** 削除日 */
	private String deleteDate;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
