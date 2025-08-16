package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.LoginCampaignHistory;

public class LoginCampaignHistorySqlProvider {

	public String findByPKey(
			@Param("userId") final String userId,
			@Param("targetMonth") String targetMonth,
			@Param("targetDate") String targetDate,
			@Param("loginCampaignType") String loginCampaignType) {
		return new SQL() {
			{
				SELECT("*");
				FROM("loginCampaignHistory");
				WHERE("userId = #{userId}");
				WHERE("targetMonth = #{targetMonth}");
				WHERE("targetDate = #{targetDate}");
				WHERE("loginCampaignType = #{loginCampaignType}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("loginCampaignHistory");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("userId");
				ORDER_BY("targetMonth");
				ORDER_BY("targetDate");
				ORDER_BY("loginCampaignType");
			}
		}.toString();
	}

	public String countByPKey(
			@Param("userId") final String userId,
			@Param("targetMonth") String targetMonth,
			@Param("targetDate") String targetDate,
			@Param("loginCampaignType") int loginCampaignType) {
		return new SQL() {
			{
				SELECT("count(*)");
				FROM("loginCampaignHistory");
				WHERE("userId = #{userId}");
				WHERE("targetMonth = #{targetMonth}");
				WHERE("targetDate = #{targetDate}");
				WHERE("loginCampaignType = #{loginCampaignType}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String insert(final LoginCampaignHistory loginCampaignHistory) {
		return new SQL() {
			{
				INSERT_INTO("loginCampaignHistory");
				VALUES("userId", "#{userId}");
				VALUES("targetMonth", "#{targetMonth}");
				VALUES("targetDate", "#{targetDate}");
				VALUES("loginCampaignType", "#{loginCampaignType}");
				VALUES("stage", "#{stage}");
				VALUES("rewardItem1", "#{rewardItem1}");
				VALUES("rewardItem2", "#{rewardItem2}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String update(final LoginCampaignHistory loginCampaignHistory) {
		return new SQL() {
			{
				UPDATE("loginCampaignHistory");
				if (!StringUtil.isNullOrEmpty(loginCampaignHistory.getStage())) {
					SET("stage = #{stage}");
				}

				if (!StringUtil.isNullOrEmpty(loginCampaignHistory.getRewardItem1())) {
					SET("rewardItem1 = #{rewardItem1}");
				}

				if (!StringUtil.isNullOrEmpty(loginCampaignHistory.getRewardItem2())) {
					SET("rewardItem2 = #{rewardItem2}");
				}

				SET("updateDate = datetime('now', '+9 hours')");
				WHERE("userId = #{userId}");
				WHERE("targetMonth = #{targetMonth}");
				WHERE("targetDate = #{targetDate}");
				WHERE("loginCampaignType = #{loginCampaignType}");
			}
		}.toString();
	}

	public String delete(
			@Param("userId") final String userId,
			@Param("targetMonth") String targetMonth,
			@Param("targetDate") String targetDate,
			@Param("loginCampaignType") String loginCampaignType) {
		return new SQL() {
			{
				DELETE_FROM("loginCampaignHistory");
				WHERE("userId = #{userId}");
				WHERE("targetMonth = #{targetMonth}");
				WHERE("targetDate = #{targetDate}");
				WHERE("loginCampaignType = #{loginCampaignType}");
			}
		}.toString();
	}

	public String deleteAll() {
		return new SQL() {
			{
				DELETE_FROM("loginCampaignHistory");
			}
		}.toString();
	}
}
