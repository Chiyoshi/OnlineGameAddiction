package com.oga.app.dataaccess.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import lombok.Builder;
import lombok.Data;

/**
 * ログインキャンペーン詳細
 */
@Data
@Builder
public class LoginCampaignDetails {

	/** ユーザID */
	private String userId;

	/** 対象月(シーズン) */
	private String targetMonth;

	/** ステージ(現在のマス目) */
	private String stage;

	/** コンプリートマスの獲得アイテム(チャプタ1) */
	private String completeItemChapter1;

	/** コンプリートマスの獲得アイテム(チャプタ2) */
	private String completeItemChapter2;

	/** コンプリートマスの獲得アイテム(チャプタ3) */
	private String completeItemChapter3;

	/** 獲得アイテム一覧 */
	private String rewardItems;

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
