package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.oga.app.dataaccess.entity.Setting;

public class SettingSqlProvider {
	
	public String findByPKey(final String key) {
		return new SQL() {
			{
				SELECT("*");
				FROM("setting");
				WHERE("key = #{key}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("setting");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String insertSetting(final Setting setting) {
		return new SQL() {
			{
				INSERT_INTO("setting");
				VALUES("key", "#{key}");
				VALUES("value", "#{value}");
				VALUES("explanation", "#{explanation}");
				VALUES("order", "#{order}");
				VALUES("registrationDate", "datetime('now')");
				VALUES("updateDate", "datetime('now')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String updateSetting(final Setting setting) {
		return new SQL() {
			{
				UPDATE("setting");
				SET("value = #{value}");
				WHERE("key = #{key}");
			}
		}.toString();
	}
}
