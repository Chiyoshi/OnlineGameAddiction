package com.oga.app.dataaccess.dao.mapper;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oga.app.dataaccess.dao.sqlprovider.DailyWorkResultSqlProvider;
import com.oga.app.dataaccess.entity.DailyWorkResult;

@Mapper
public interface DailyWorkResultMapper {

	@SelectProvider(type = DailyWorkResultSqlProvider.class, method = "findAll")
	List<DailyWorkResult> findAll();

	@SelectProvider(type = DailyWorkResultSqlProvider.class, method = "findByPKey")
	DailyWorkResult findByPKey(
			@Param("userId") String userId,
			@Param("baseDate") String baseDate,
			@Param("serviceType") String serviceType);

	@SelectProvider(type = DailyWorkResultSqlProvider.class, method = "findByTargetDate")
	List<DailyWorkResult> findByTargetDate(
			@Param("userId") String userId,
			@Param("targetDateFrom") String targetDateFrom,
			@Param("targetDateTo") String targetDateTo,
			@Param("serviceType") String serviceType);

	@SelectProvider(type = DailyWorkResultSqlProvider.class, method = "countByTargetDate")
	int countByTargetDate(
			@Param("userId") String userId,
			@Param("targetDateFrom") String targetDateFrom,
			@Param("targetDateTo") String targetDateTo,
			@Param("serviceType") String serviceType,
			@Param("status") String status);

	@SelectProvider(type = DailyWorkResultSqlProvider.class, method = "countByErrorStatus")
	int countByErrorStatus(
			@Param("baseDate") String baseDate,
			@Param("status") String status);

	@SelectProvider(type = DailyWorkResultSqlProvider.class, method = "findByRewardItemAggregate")
	List<LinkedHashMap<String, Object>> findByRewardItemAggregate(
			@Param("targetDateFrom") String targetDateFrom,
			@Param("targetDateTo") String targetDateTo,
			@Param("serviceType") String serviceType,
			@Param("status") String status,
			final List<String> targetRewardItemList);

	@InsertProvider(type = DailyWorkResultSqlProvider.class, method = "insertDailyWorkResult")
	void insertDailyWorkResult(DailyWorkResult dailyWorkResult);

	@UpdateProvider(type = DailyWorkResultSqlProvider.class, method = "updateDailyWorkResult")
	void updateDailyWorkResult(DailyWorkResult dailyWorkResult);

	@DeleteProvider(type = DailyWorkResultSqlProvider.class, method = "deleteDailyWorkResult")
	void deleteDailyWorkResult(
			@Param("userId") String userId,
			@Param("baseDate") String baseDate,
			@Param("serviceType") String serviceType);

	@DeleteProvider(type = DailyWorkResultSqlProvider.class, method = "deleteAllDailyWorkResult")
	void deleteAllDailyWorkResult();
}
