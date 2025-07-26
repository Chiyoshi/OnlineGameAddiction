package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class MenuSqlProvider {
	
	public String findByPKey(@Param("menuId") final String menuId) {
		return new SQL() {
			{
				SELECT("*");
				FROM("menu");
				WHERE("menuId = #{menuId}");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("SORTORDER");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("menu");
				WHERE("viewFlg = 'Y'");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("SORTORDER");
			}
		}.toString();
	}
}
