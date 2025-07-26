package com.oga.app.dataaccess.dao.sqlprovider;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.DailyWorkResult;

public class DailyWorkResultSqlProvider {

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("dailyWorkResult");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findByPKey(
			@Param("userId") final String userId,
			@Param("baseDate") final String baseDate,
			@Param("serviceType") final String serviceType) {
		return new SQL() {
			{
				SELECT("*");
				FROM("dailyWorkResult");
				WHERE("userId = #{userId}");
				WHERE("baseDate = #{baseDate}");
				WHERE("serviceType = #{serviceType}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findByTargetDate(
			@Param("userId") final String userId,
			@Param("targetDateFrom") final String targetDateFrom,
			@Param("targetDateTo") final String targetDateTo,
			@Param("serviceType") final String serviceType) {
		return new SQL() {
			{
				SELECT("*");
				FROM("dailyWorkResult");
				WHERE("userId = #{userId}");
				WHERE("datetime(baseDate) >= #{targetDateFrom}");
				WHERE("datetime(baseDate) <= #{targetDateTo}");
				if (!StringUtil.isNullOrEmpty(serviceType)) {
					WHERE("serviceType = #{serviceType}");
				}
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String countByTargetDate(
			@Param("userId") final String userId,
			@Param("targetDateFrom") final String targetDateFrom,
			@Param("targetDateTo") final String targetDateTo,
			@Param("serviceType") final String serviceType,
			@Param("status") final String status) {
		return new SQL() {
			{
				SELECT("count(*)");
				FROM("dailyWorkResult");
				WHERE("userId = #{userId}");
				WHERE("datetime(baseDate) >= #{targetDateFrom}");
				WHERE("datetime(baseDate) <= #{targetDateTo}");
				WHERE("serviceType = #{serviceType}");
				WHERE("status = #{status}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String countByErrorStatus(
			@Param("baseDate") final String baseDate,
			@Param("status") final String status) {
		return new SQL() {
			{
				SELECT("count(*)");
				FROM("dailyWorkResult");
				WHERE("baseDate = #{baseDate}");
				WHERE("status = #{status}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findByRewardItemAggregate(
			@Param("targetDateFrom") final String targetDateFrom,
			@Param("targetDateTo") final String targetDateTo,
			@Param("serviceType") final String serviceType,
			@Param("status") final String status,
			final List<String> targetRewardItemList) {
		return new SQL() {
			{
				SELECT("userid");

				if (targetRewardItemList != null && targetRewardItemList.size() > 0) {
					for (int i = 0; i < targetRewardItemList.size(); i++) {
						SELECT("sum(case when rewarditem = '" + targetRewardItemList.get(i) + "' then 1 else 0 end) as ITEM" + (i + 1));
					}
				}

				FROM("dailyWorkResult");
				WHERE("datetime(baseDate) >= #{targetDateFrom}");
				WHERE("datetime(baseDate) <= #{targetDateTo}");
				WHERE("serviceType = #{serviceType}");
				WHERE("status = #{status}");
				WHERE("deleteFlg = 'N'");
				GROUP_BY("userid");
				ORDER_BY("userid");
			}
		}.toString();
	}	

	public String insertDailyWorkResult(final DailyWorkResult dailyWorkResult) {
		return new SQL() {
			{
				INSERT_INTO("dailyWorkResult");
				VALUES("userId", "#{userId}");
				VALUES("baseDate", "#{baseDate}");
				VALUES("serviceType", "#{serviceType}");
				VALUES("status", "#{status}");
				VALUES("rewardItem", "#{rewardItem}");
				VALUES("rewardItemImage", "#{rewardItemImage}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String updateDailyWorkResult(final DailyWorkResult dailyWorkResult) {
		return new SQL() {
			{
				UPDATE("dailyWorkResult");
				if (!StringUtil.isNullOrEmpty(dailyWorkResult.getStatus())) {
					SET("status = #{status}");
				}

				if (!StringUtil.isNullOrEmpty(dailyWorkResult.getRewardItem())) {
					SET("rewardItem = #{rewardItem}");
				}

				if (!StringUtil.isNullOrEmpty(dailyWorkResult.getRewardItemImage())) {
					SET("rewardItemImage = #{rewardItemImage}");
				}

				SET("updateDate = datetime('now', '+9 hours')");
				WHERE("userId = #{userId}");
				WHERE("baseDate = #{baseDate}");
				WHERE("serviceType = #{serviceType}");
			}
		}.toString();
	}

	public String deleteDailyWorkResult(
			@Param("userId") final String userId,
			@Param("baseDate") final String baseDate,
			@Param("serviceType") final String serviceType) {
		return new SQL() {
			{
				DELETE_FROM("dailyWorkResult");
				WHERE("userId = #{userId}");
				WHERE("baseDate = #{baseDate}");
				WHERE("serviceType = #{serviceType}");
			}
		}.toString();
	}

	public String deleteAllDailyWorkResult() {
		return new SQL() {
			{
				DELETE_FROM("dailyWorkResult");
			}
		}.toString();
	}
}
