package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oga.app.dataaccess.dao.sqlprovider.RouletteHistorySqlProvider;
import com.oga.app.dataaccess.entity.RouletteHistory;

@Mapper
public interface RouletteHistoryMapper {

	@SelectProvider(type = RouletteHistorySqlProvider.class, method = "findByPKey")
	RouletteHistory findByPKey(
			@Param("userId") String userId, 
			@Param("campaignId") String campaignId,
			@Param("targetDate") String targetDate);

	@SelectProvider(type = RouletteHistorySqlProvider.class, method = "findAll")
	List<RouletteHistory> findAll();

	@SelectProvider(type = RouletteHistorySqlProvider.class, method = "findByCampaignId")
	List<RouletteHistory> findByCampaignId(
			@Param("userId") String userId, 
			@Param("campaignId") String campaignId);

	@InsertProvider(type = RouletteHistorySqlProvider.class, method = "insert")
	void insert(RouletteHistory rouletteHistory);

	@UpdateProvider(type = RouletteHistorySqlProvider.class, method = "update")
	void update(RouletteHistory rouletteHistory);

	@DeleteProvider(type = RouletteHistorySqlProvider.class, method = "delete")
	void delete(
			@Param("userId") String userId, 
			@Param("campaignId") String campaignId,
			@Param("targetDate") String targetDate);

	@DeleteProvider(type = RouletteHistorySqlProvider.class, method = "deleteAll")
	void deleteAll();
}
