package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.LoginCampaignDetails;

public class LoginCampaignDetailsSqlProvider {

	public String findByPKey(
			@Param("userId") final String userId,
			@Param("targetMonth") String targetMonth) {
		return new SQL() {
			{
				SELECT("*");
				FROM("loginCampaignDetails");
				WHERE("userId = #{userId}");
				WHERE("targetMonth = #{targetMonth}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("loginCampaignDetails");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("userId");
				ORDER_BY("targetMonth");
			}
		}.toString();
	}

	public String countByPKey(
			@Param("userId") final String userId,
			@Param("targetMonth") String targetMonth) {
		return new SQL() {
			{
				SELECT("count(*)");
				FROM("loginCampaignDetails");
				WHERE("userId = #{userId}");
				WHERE("targetMonth = #{targetMonth}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String insert(final LoginCampaignDetails loginCampaignDetails) {
		return new SQL() {
			{
				INSERT_INTO("loginCampaignDetails");
				VALUES("userId", "#{userId}");
				VALUES("targetMonth", "#{targetMonth}");
				VALUES("stage", "#{stage}");
				VALUES("completeItemChapter1", "#{completeItemChapter1}");
				VALUES("completeItemChapter2", "#{completeItemChapter2}");
				VALUES("completeItemChapter3", "#{completeItemChapter3}");
				VALUES("rewardItems", "#{rewardItems}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String update(final LoginCampaignDetails loginCampaignDetails) {
		return new SQL() {
			{
				UPDATE("loginCampaignDetails");
				if (!StringUtil.isNullOrEmpty(loginCampaignDetails.getStage())) {
					SET("stage = #{stage}");
				}

				if (!StringUtil.isNullOrEmpty(loginCampaignDetails.getCompleteItemChapter1())) {
					SET("completeItemChapter1 = #{completeItemChapter1}");
				}

				if (!StringUtil.isNullOrEmpty(loginCampaignDetails.getCompleteItemChapter2())) {
					SET("completeItemChapter2 = #{completeItemChapter2}");
				}

				if (!StringUtil.isNullOrEmpty(loginCampaignDetails.getCompleteItemChapter3())) {
					SET("completeItemChapter3 = #{completeItemChapter3}");
				}

				if (!StringUtil.isNullOrEmpty(loginCampaignDetails.getRewardItems())) {
					SET("rewardItems = #{rewardItems}");
				}

				SET("updateDate = datetime('now', '+9 hours')");
				WHERE("userId = #{userId}");
				WHERE("targetMonth = #{targetMonth}");
			}
		}.toString();
	}

	public String delete(
			@Param("userId") final String userId,
			@Param("targetMonth") String targetMonth) {
		return new SQL() {
			{
				DELETE_FROM("loginCampaignDetails");
				WHERE("userId = #{userId}");
				WHERE("targetMonth = #{targetMonth}");
			}
		}.toString();
	}

	public String deleteAll() {
		return new SQL() {
			{
				DELETE_FROM("loginCampaignDetails");
			}
		}.toString();
	}
}
