package com.oga.app.dataaccess.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Builder;
import lombok.Data;

/**
 * デイリーリワード履歴
 */
@Data
@Builder
public class DailyRewardHistory {

	/** ユーザID */
	private String userId;

	/** 対象日 */
	private String targetDate;

	/** サービスポイント */
	private String servicePoint;

	/** ポイント獲得倍率2倍 */
	private String isPointsRewardRate2x;
	
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
