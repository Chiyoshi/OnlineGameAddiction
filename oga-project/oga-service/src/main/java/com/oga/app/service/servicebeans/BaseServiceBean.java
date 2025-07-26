package com.oga.app.service.servicebeans;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.oga.app.common.utils.DateUtil;

public class BaseServiceBean {

	/** ��{��� */
	/** ��� */
	private String baseDate;

	/** ���O�C����� */
	private boolean isLogged = false;

	/**
	 * @return baseDate
	 */
	public String getBaseDate() {
		return baseDate;
	}

	/**
	 * @param baseDate �Z�b�g���� baseDate
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
	 * @param isLogined �Z�b�g���� isLogined
	 */
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

	/**
	 * ���(�n�C�t������)���擾����(SQL�p)
	 * 
	 * @return ���(YYYY-MM-DD)
	 */
	public String getBaseDateSql() {
		return DateUtil.parseDateStr(this.baseDate, DateUtil.DATE_FORMAT_YYYYMMDD_2);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
