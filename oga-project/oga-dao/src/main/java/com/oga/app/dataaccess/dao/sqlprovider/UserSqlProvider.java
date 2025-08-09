package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.User;

public class UserSqlProvider {

	public String findByPKey(@Param("userId") final String userId) {
		return new SQL() {
			{
				SELECT("*");
				FROM("user");
				WHERE("userId = #{userId}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("user");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("userId");
			}
		}.toString();
	}

	public String insert(final User user) {
		return new SQL() {
			{
				INSERT_INTO("user");
				VALUES("userId", "#{userId}");
				VALUES("password", "#{password}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String update(final User user) {
		return new SQL() {
			{
				UPDATE("user");
				if (!StringUtil.isNullOrEmpty(user.getPassword())) {
					SET("password = #{password}");
				}

				SET("updateDate = datetime('now', '+9 hours')");
				WHERE("userId = #{userId}");
			}
		}.toString();
	}

	public String delete(@Param("userId") final String userId) {
		return new SQL() {
			{
				DELETE_FROM("user");
				WHERE("userId = #{userId}");
			}
		}.toString();
	}

	public String deleteAll() {
		return new SQL() {
			{
				DELETE_FROM("user");
			}
		}.toString();
	}
}
