package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.oga.app.dataaccess.entity.Campaign;

public class CampaignSqlProvider {

	public String findByPKey(final String campaignId) {
		return new SQL() {
			{
				SELECT("*");
				FROM("campaign");
				WHERE("campaignId = #{campaignId}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("campaign");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findByCampaignDate(
			@Param("campaignType") final String campaignType,
			@Param("targetDate") final String targetDate) {
		return new SQL() {
			{
				SELECT("*");
				FROM("campaign");
				WHERE("campaignType = #{campaignType}");
				WHERE("startDate <= datetime(#{targetDate})");
				WHERE("endDate >= datetime(#{targetDate})");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String insertCampaign(final Campaign campaign) {
		return new SQL() {
			{
				INSERT_INTO("campaign");
				VALUES("campaignId", "#{campaignId}");
				VALUES("campaignType", "#{campaignType}");
				VALUES("campaignName", "#{campaignName}");
				VALUES("startDate", "#{startDate}");
				VALUES("endDate", "#{endDate}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "#{deleteFlg}");
				VALUES("deleteDate", "#{deleteDate}");
			}
		}.toString();
	}

	public String deleteCampaign(final String campaignId) {
		return new SQL() {
			{
				DELETE_FROM("campaign");
				WHERE("campaignId = #{campaignId}");
			}
		}.toString();
	}

	public String deleteAllCampaign() {
		return new SQL() {
			{
				DELETE_FROM("campaign");
			}
		}.toString();
	}
}
