package com.oga.app.dataaccess.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Builder;
import lombok.Data;

/**
 * レッドストーン管理
 */
@Data
@Builder
public class RSManagement {

	/** ユーザID */
	private String userId;

	/** Gem */
	private String gem;

	/** SP */
	private String servicePoint;

	/** Red's Point */
	private String redsPoint;

	/** ログインキャンペーンフラグ */
	private String loginCampaignFlg;

	/** デイリーリワードフラグ */
	private String dailyRewardFlg;

	/** ルーレットフラグ */
	private String rouletteFlg;

	/** 最終ログインキャンペーン実行日 */
	private String lastLoginCampaignDate;

	/** 最終デイリーリワード実行日 */
	private String lastDailyRewardDate;

	/** 最終ルーレット実行日 */
	private String lastRouletteDate;

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
