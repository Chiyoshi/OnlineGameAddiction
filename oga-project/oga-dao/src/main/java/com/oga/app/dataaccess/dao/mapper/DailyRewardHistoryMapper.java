package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oga.app.dataaccess.dao.sqlprovider.DailyRewardHistorySqlProvider;
import com.oga.app.dataaccess.entity.DailyRewardHistory;

@Mapper
public interface DailyRewardHistoryMapper {

	@SelectProvider(type = DailyRewardHistorySqlProvider.class, method = "findByPKey")
	DailyRewardHistory findByPKey(
			@Param("userId") String userId, 
			@Param("targetDate") String targetDate);

	@SelectProvider(type = DailyRewardHistorySqlProvider.class, method = "findAll")
	List<DailyRewardHistory> findAll();

	@SelectProvider(type = DailyRewardHistorySqlProvider.class, method = "findByUserId")
	List<DailyRewardHistory> findByUserId(@Param("userId") String userId);

	@InsertProvider(type = DailyRewardHistorySqlProvider.class, method = "insert")
	void insert(DailyRewardHistory dailyRewardHistory);

	@UpdateProvider(type = DailyRewardHistorySqlProvider.class, method = "update")
	void update(DailyRewardHistory dailyRewardHistory);

	@DeleteProvider(type = DailyRewardHistorySqlProvider.class, method = "delete")
	void delete(
			@Param("userId") String userId, 
			@Param("targetDate") String targetDate);

	@DeleteProvider(type = DailyRewardHistorySqlProvider.class, method = "deleteAll")
	void deleteAll();
}
