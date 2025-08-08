package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.RSManagement;

public class RSManagementSqlProvider {

	public String findByPKey(final String userId) {
		return new SQL() {
			{
				SELECT("*");
				FROM("rsManagement");
				WHERE("userId = #{userId}");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("rsManagement");
				WHERE("deleteFlg = 'N'");
				ORDER_BY("userId");
			}
		}.toString();
	}

	public String insert(final RSManagement rsManagement) {
		return new SQL() {
			{
				INSERT_INTO("rsManagement");
				VALUES("userId", "#{userId}");
				VALUES("gem", "#{gem}");
				VALUES("servicePoint", "#{servicePoint}");
				VALUES("redsPoint", "#{redsPoint}");
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

	public String update(final RSManagement rsManagement) {
		return new SQL() {
			{
				UPDATE("rsManagement");
				if (!StringUtil.isNullOrEmpty(rsManagement.getGem())) {
					SET("gem = #{gem}");
				}

				if (!StringUtil.isNullOrEmpty(rsManagement.getServicePoint())) {
					SET("servicePoint = #{servicePoint}");
				}

				if (!StringUtil.isNullOrEmpty(rsManagement.getRedsPoint())) {
					SET("redsPoint = #{redsPoint}");
				}

				if (!StringUtil.isNullOrEmpty(rsManagement.getLoginCampaignFlg())) {
					SET("loginCampaignFlg = #{loginCampaignFlg}");
				}

				if (!StringUtil.isNullOrEmpty(rsManagement.getDailyRewardFlg())) {
					SET("dailyRewardFlg = #{dailyRewardFlg}");
				}

				if (!StringUtil.isNullOrEmpty(rsManagement.getRouletteFlg())) {
					SET("rouletteFlg = #{rouletteFlg}");
				}

				if (!StringUtil.isNullOrEmpty(rsManagement.getLastLoginCampaignDate())) {
					SET("lastLoginCampaignDate = #{lastLoginCampaignDate}");
				}

				if (!StringUtil.isNullOrEmpty(rsManagement.getLastDailyRewardDate())) {
					SET("lastDailyRewardDate = #{lastDailyRewardDate}");
				}

				if (!StringUtil.isNullOrEmpty(rsManagement.getLastRouletteDate())) {
					SET("lastRouletteDate = #{lastRouletteDate}");
				}

				SET("updateDate = datetime('now', '+9 hours')");
				WHERE("userId = #{userId}");
			}
		}.toString();
	}

	public String delete(final String userId) {
		return new SQL() {
			{
				DELETE_FROM("rsManagement");
				WHERE("userId = #{userId}");
			}
		}.toString();
	}

	public String deleteAll() {
		return new SQL() {
			{
				DELETE_FROM("rsManagement");
			}
		}.toString();
	}
}
