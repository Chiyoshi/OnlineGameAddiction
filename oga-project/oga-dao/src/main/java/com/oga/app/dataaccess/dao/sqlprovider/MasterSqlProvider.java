package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.oga.app.dataaccess.entity.Master;

public class MasterSqlProvider {

	public String findByPKey(final String key) {
		return new SQL() {
			{
				SELECT("*");
				FROM("master");
				WHERE("key = #{key}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("master");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String insertMaster(final Master master) {
		return new SQL() {
			{
				INSERT_INTO("master");
				VALUES("key", "#{key}");
				VALUES("value", "#{value}");
				VALUES("explanation", "#{explanation}");
				VALUES("`order`", "#{order}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String deleteMaster(final String key) {
		return new SQL() {
			{
				DELETE_FROM("master");
				WHERE("key = #{key}");
			}
		}.toString();
	}

	public String deleteAllMaster() {
		return new SQL() {
			{
				DELETE_FROM("master");
			}
		}.toString();
	}
}
