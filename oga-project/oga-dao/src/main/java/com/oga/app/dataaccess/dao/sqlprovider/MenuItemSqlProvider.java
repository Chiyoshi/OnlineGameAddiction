package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class MenuItemSqlProvider {
	
	public String findByPKey(@Param("menuId") final String menuId, @Param("menuItemId")  String menuItemId) {
		return new SQL() {
			{
				SELECT("*");
				FROM("menuItem");
				WHERE("menuId = #{menuId}");
				WHERE("menuItemId = #{menuItemId}");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("SORTORDER");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("menuItem");
				WHERE("viewFlg = 'Y'");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("SORTORDER");
			}
		}.toString();
	}
}
