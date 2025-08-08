package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.RouletteHistory;

public class RouletteHistorySqlProvider {

	public String findByPKey(
			@Param("userId") final String userId,
			@Param("campaignId") String campaignId,
			@Param("targetDate") String targetDate) {
		return new SQL() {
			{
				SELECT("*");
				FROM("rouletteHistory");
				WHERE("userId = #{userId}");
				WHERE("campaignId = #{campaignId}");
				WHERE("targetDate = #{targetDate}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("rouletteHistory");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("userId");
				ORDER_BY("campaignId");
				ORDER_BY("targetDate");
			}
		}.toString();
	}

	public String findByCampaignId(
			@Param("userId") final String userId,
			@Param("campaignId") String campaignId) {
		return new SQL() {
			{
				SELECT("*");
				FROM("rouletteHistory");
				WHERE("userId = #{userId}");
				WHERE("campaignId = #{campaignId}");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("targetDate");
			}
		}.toString();
	}

	public String insert(final RouletteHistory rouletteHistory) {
		return new SQL() {
			{
				INSERT_INTO("rouletteHistory");
				VALUES("userId", "#{userId}");
				VALUES("campaignId", "#{campaignId}");
				VALUES("targetDate", "#{targetDate}");
				VALUES("rewardItem", "#{rewardItem}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String update(final RouletteHistory rouletteHistory) {
		return new SQL() {
			{
				UPDATE("rouletteHistory");
				if (!StringUtil.isNullOrEmpty(rouletteHistory.getRewardItem())) {
					SET("rewardItem = #{rewardItem}");
				}

				SET("updateDate = datetime('now', '+9 hours')");
				WHERE("userId = #{userId}");
				WHERE("campaignId = #{campaignId}");
				WHERE("targetDate = #{targetDate}");
			}
		}.toString();
	}

	public String delete(
			@Param("userId") final String userId,
			@Param("campaignId") String campaignId,
			@Param("targetDate") String targetDate) {
		return new SQL() {
			{
				DELETE_FROM("rouletteHistory");
				WHERE("userId = #{userId}");
				WHERE("campaignId = #{campaignId}");
				WHERE("targetDate = #{targetDate}");
			}
		}.toString();
	}

	public String deleteAll() {
		return new SQL() {
			{
				DELETE_FROM("rouletteHistory");
			}
		}.toString();
	}
}
