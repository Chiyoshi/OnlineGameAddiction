package com.oga.app.service.beans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.oga.app.common.utils.DateUtil;
import com.oga.app.service.response.LoginResult;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ServiceBeanBase {

	/** 基本情報 */
	/** 基準日 */
	private String baseDate;

	/** ブラウザが初期化済みか判定する */
	@Builder.Default
	private boolean isBrowserInitialized = false;

	/** ログイン済みか判定する */
	@Builder.Default
	private boolean isLogged = false;

	/** 認証情報 */
	private LoginResult loginResult;

	/**
	 * 基準日(ハイフンあり)を取得する(SQL用)
	 * 
	 * @return 基準日(YYYY-MM-DD)
	 */
	public String getBaseDateSql() {
		return DateUtil.parseDateStr(this.baseDate, DateUtil.DATE_FORMAT_YYYYMMDD_2);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
