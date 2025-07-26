package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.DailyWork;

public class DailyWorkSqlProvider {

	public String findByPKey(final String userId) {
		return new SQL() {
			{
				SELECT("*");
				FROM("dailyWork");
				WHERE("userId = #{userId}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("dailyWork");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAllWithUser() {
		return new SQL() {
			{
				SELECT("dw.userId");
				SELECT("dw.loginCampaignFlg");
				SELECT("dw.dailyRewardFlg");
				SELECT("dw.rouletteFlg");
				FROM("dailyWork dw");
				FROM("user u");
				WHERE("dw.userId = u.userId");
				WHERE("dw.deleteFlg = 'N'");
				WHERE("u.deleteFlg = 'N'");
			}
		}.toString();
	}

	public String insertDailyWork(final DailyWork dailyWork) {
		return new SQL() {
			{
				INSERT_INTO("dailyWork");
				VALUES("userId", "#{userId}");
				VALUES("loginCampaignFlg", "#{loginCampaignFlg}");
				VALUES("dailyRewardFlg", "#{dailyRewardFlg}");
				VALUES("rouletteFlg", "#{rouletteFlg}");
				VALUES("lastLoginCampaignDate", "#{lastLoginCampaignDate}");
				VALUES("lastDailyRewardDate", "#{lastDailyRewardDate}");
				VALUES("lastRouletteDate", "#{lastRouletteDate}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String updateDailyWork(final DailyWork dailyWork) {
		return new SQL() {
			{
				UPDATE("dailyWork");
				if (!StringUtil.isNullOrEmpty(dailyWork.getLoginCampaignFlg())) {
					SET("loginCampaignFlg = #{loginCampaignFlg}");
				}

				if (!StringUtil.isNullOrEmpty(dailyWork.getDailyRewardFlg())) {
					SET("dailyRewardFlg = #{dailyRewardFlg}");
				}

				if (!StringUtil.isNullOrEmpty(dailyWork.getRouletteFlg())) {
					SET("rouletteFlg = #{rouletteFlg}");
				}

				if (!StringUtil.isNullOrEmpty(dailyWork.getLastLoginCampaignDate())) {
					SET("lastLoginCampaignDate = #{lastLoginCampaignDate}");
				}

				if (!StringUtil.isNullOrEmpty(dailyWork.getLastDailyRewardDate())) {
					SET("lastDailyRewardDate = #{lastDailyRewardDate}");
				}

				if (!StringUtil.isNullOrEmpty(dailyWork.getLastRouletteDate())) {
					SET("lastRouletteDate = #{lastRouletteDate}");
				}

				SET("updateDate = datetime('now', '+9 hours')");
				WHERE("userId = #{userId}");
			}
		}.toString();
	}

	public String deleteDailyWork(final String userId) {
		return new SQL() {
			{
				DELETE_FROM("dailyWork");
				WHERE("userId = #{userId}");
			}
		}.toString();
	}

	public String deleteAllDailyWork() {
		return new SQL() {
			{
				DELETE_FROM("dailyWork");
			}
		}.toString();
	}
}
