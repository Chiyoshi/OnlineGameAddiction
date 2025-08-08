package com.oga.app.dataaccess.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Builder;
import lombok.Data;

/**
 * ログインキャンペーン履歴
 */
@Data
@Builder
public class LoginCampaignHistory {

	/** ユーザID */
	private String userId;

	/** 対象月(シーズン) */
	private String targetMonth;

	/** 対象日 */
	private String targetDate;

	/** ステージ(現在のマス目) */
	private String stage;

	/** ログインキャンペーン種別 */
	private String loginCampaignType;

	/** 獲得アイテム1 */
	private String rewardItem1;

	/** 獲得アイテム1 */
	private String rewardItem2;

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
