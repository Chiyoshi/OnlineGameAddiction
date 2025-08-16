package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oga.app.dataaccess.dao.sqlprovider.LoginCampaignHistorySqlProvider;
import com.oga.app.dataaccess.entity.LoginCampaignHistory;

@Mapper
public interface LoginCampaignHistoryMapper {

	@SelectProvider(type = LoginCampaignHistorySqlProvider.class, method = "findByPKey")
	LoginCampaignHistory findByPKey(
			@Param("userId") String userId,
			@Param("targetMonth") String targetMonth,
			@Param("targetDate") String targetDate,
			@Param("loginCampaignType") String loginCampaignType);

	@SelectProvider(type = LoginCampaignHistorySqlProvider.class, method = "findAll")
	List<LoginCampaignHistory> findAll();

	@SelectProvider(type = LoginCampaignHistorySqlProvider.class, method = "countByPKey")
	int countByPKey(
			@Param("userId") String userId,
			@Param("targetMonth") String targetMonth,
			@Param("targetDate") String targetDate,
			@Param("loginCampaignType") int loginCampaignType);

	@InsertProvider(type = LoginCampaignHistorySqlProvider.class, method = "insert")
	void insert(LoginCampaignHistory loginCampaignHistory);

	@UpdateProvider(type = LoginCampaignHistorySqlProvider.class, method = "update")
	void update(LoginCampaignHistory loginCampaignHistory);

	@DeleteProvider(type = LoginCampaignHistorySqlProvider.class, method = "delete")
	void delete(
			@Param("userId") String userId,
			@Param("targetMonth") String targetMonth,
			@Param("targetDate") String targetDate,
			@Param("loginCampaignType") String loginCampaignType);
	
	@DeleteProvider(type = LoginCampaignHistorySqlProvider.class, method = "deleteAll")
	void deleteAll();
}
