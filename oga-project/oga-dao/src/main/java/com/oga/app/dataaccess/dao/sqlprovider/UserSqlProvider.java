package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.User;

public class UserSqlProvider {

	public String findByPKey(final String userId) {
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

	public String findByUserInfoList() {
		return new SQL() {
			{
				SELECT("u.userId");
				SELECT("u.birthDay");
				SELECT("u.mailAddress");
				SELECT("u.gem");
				SELECT("u.servicePoint");
				SELECT("u.redspoint");
				SELECT("dw.loginCampaignFlg");
				SELECT("dw.dailyRewardFlg");
				SELECT("dw.rouletteFlg");
				FROM("user u");
				FROM("dailyWork dw");
				WHERE("u.userId = dw.userId");
				WHERE("u.deleteFlg = 'N'");

			}
		}.toString();
	}

	public String insertUser(final User user) {
		return new SQL() {
			{
				INSERT_INTO("user");
				VALUES("userId", "#{userId}");
				VALUES("password", "#{password}");
				VALUES("birthDay", "#{birthDay}");
				VALUES("mailAddress", "#{mailAddress}");
				VALUES("gem", "#{gem}");
				VALUES("servicePoint", "#{servicePoint}");
				VALUES("redspoint", "#{redspoint}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String updateUser(final User user) {
		return new SQL() {
			{
				UPDATE("user");
				if (!StringUtil.isNullOrEmpty(user.getPassword())) {
					SET("password = #{password}");
				}

				if (!StringUtil.isNullOrEmpty(user.getBirthDay())) {
					SET("birthDay = #{birthDay}");
				}

				if (!StringUtil.isNullOrEmpty(user.getMailAddress())) {
					SET("mailAddress = #{mailAddress}");
				}

				if (!StringUtil.isNullOrEmpty(user.getGem())) {
					SET("gem = #{gem}");
				}

				if (!StringUtil.isNullOrEmpty(user.getServicePoint())) {
					SET("servicePoint = #{servicePoint}");
				}

				if (!StringUtil.isNullOrEmpty(user.getRedspoint())) {
					SET("redspoint = #{redspoint}");
				}

				SET("updateDate = datetime('now', '+9 hours')");
				WHERE("userId = #{userId}");
			}
		}.toString();
	}

	public String deleteUser(final String userId) {
		return new SQL() {
			{
				DELETE_FROM("user");
				WHERE("userId = #{userId}");
			}
		}.toString();
	}

	public String deleteAllUser() {
		return new SQL() {
			{
				DELETE_FROM("user");
			}
		}.toString();
	}
}
