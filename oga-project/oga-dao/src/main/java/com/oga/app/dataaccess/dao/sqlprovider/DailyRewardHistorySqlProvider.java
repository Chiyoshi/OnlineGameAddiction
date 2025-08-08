package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.DailyRewardHistory;

public class DailyRewardHistorySqlProvider {

	public String findByPKey(
			@Param("userId") final String userId,
			@Param("targetDate") String targetDate) {
		return new SQL() {
			{
				SELECT("*");
				FROM("dailyRewardHistory");
				WHERE("userId = #{userId}");
				WHERE("targetDate = #{targetDate}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("dailyRewardHistory");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("userId");
				ORDER_BY("targetDate");
			}
		}.toString();
	}

	public String findByUserId(@Param("userId") final String userId) {
		return new SQL() {
			{
				SELECT("*");
				FROM("dailyRewardHistory");
				WHERE("userId = #{userId}");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("targetDate");
			}
		}.toString();
	}

	public String insert(final DailyRewardHistory dailyRewardHistory) {
		return new SQL() {
			{
				INSERT_INTO("dailyRewardHistory");
				VALUES("userId", "#{userId}");
				VALUES("targetDate", "#{targetDate}");
				VALUES("servicePoint", "#{servicePoint}");
				VALUES("isPointsRewardRate2x", "#{isPointsRewardRate2x}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String update(final DailyRewardHistory dailyRewardHistory) {
		return new SQL() {
			{
				UPDATE("dailyRewardHistory");
				if (!StringUtil.isNullOrEmpty(dailyRewardHistory.getServicePoint())) {
					SET("servicePoint = #{servicePoint}");
				}
				SET("isPointsRewardRate2x = #{isPointsRewardRate2x}");
				SET("updateDate = datetime('now', '+9 hours')");
				WHERE("userId = #{userId}");
				WHERE("targetDate = #{targetDate}");
			}
		}.toString();
	}

	public String delete(
			@Param("userId") final String userId,
			@Param("targetDate") String targetDate) {
		return new SQL() {
			{
				DELETE_FROM("dailyRewardHistory");
				WHERE("userId = #{userId}");
				WHERE("targetDate = #{targetDate}");
			}
		}.toString();
	}

	public String deleteAll() {
		return new SQL() {
			{
				DELETE_FROM("dailyRewardHistory");
			}
		}.toString();
	}
}
