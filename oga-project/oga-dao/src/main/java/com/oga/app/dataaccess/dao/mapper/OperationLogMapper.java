package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.oga.app.dataaccess.dao.sqlprovider.OperationLogSqlProvider;
import com.oga.app.dataaccess.entity.OperationLog;

@Mapper
public interface OperationLogMapper {

	@SelectProvider(type = OperationLogSqlProvider.class, method = "findAll")
	List<OperationLog> findAll();

	@SelectProvider(type = OperationLogSqlProvider.class, method = "findByOperationDate")
	List<OperationLog> findByOperationDate(@Param("fromOperationDate") String fromOperationDate,
			@Param("toOperationDate") String toOperationDate, @Param("userId") String userId);

	@InsertProvider(type = OperationLogSqlProvider.class, method = "insertOperationLog")
	void insertOperationLog(OperationLog operationLog);

}
