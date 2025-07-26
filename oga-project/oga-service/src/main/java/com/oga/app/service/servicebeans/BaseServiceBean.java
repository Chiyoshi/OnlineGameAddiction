package com.oga.app.service.servicebeans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.oga.app.common.utils.DateUtil;

public class BaseServiceBean {

	/** 基本情報 */
	/** 基準日 */
	private String baseDate;

	/** ログイン状態 */
	private boolean isLogged = false;

	/**
	 * @return baseDate
	 */
	public String getBaseDate() {
		return baseDate;
	}

	/**
	 * @param baseDate セットする baseDate
	 */
	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	/**
	 * @return isLogined
	 */
	public boolean isLogged() {
		return isLogged;
	}

	/**
	 * @param isLogined セットする isLogined
	 */
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

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
