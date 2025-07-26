package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oga.app.dataaccess.dao.sqlprovider.DailyWorkSqlProvider;
import com.oga.app.dataaccess.entity.DailyWork;

@Mapper
public interface DailyWorkMapper {

	@SelectProvider(type = DailyWorkSqlProvider.class, method = "findByPKey")
	DailyWork findByPKey(String userId);

	@SelectProvider(type = DailyWorkSqlProvider.class, method = "findAll")
	List<DailyWork> findAll();
	
	@SelectProvider(type = DailyWorkSqlProvider.class, method = "findAllWithUser")
	List<DailyWork> findAllWithUser();

	@InsertProvider(type = DailyWorkSqlProvider.class, method = "insertDailyWork")
	void insertDailyWork(DailyWork dailyWork);

	@UpdateProvider(type = DailyWorkSqlProvider.class, method = "updateDailyWork")
	void updateDailyWork(DailyWork dailyWork);

	@DeleteProvider(type = DailyWorkSqlProvider.class, method = "deleteDailyWork")
	void deleteDailyWork(String userId);

	@DeleteProvider(type = DailyWorkSqlProvider.class, method = "deleteAllDailyWork")
	void deleteAllDailyWork();
}
