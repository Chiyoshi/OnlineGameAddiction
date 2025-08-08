package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oga.app.dataaccess.dao.sqlprovider.RSManagementSqlProvider;
import com.oga.app.dataaccess.entity.RSManagement;

@Mapper
public interface RSManagementMapper {

	@SelectProvider(type = RSManagementSqlProvider.class, method = "findByPKey")
	RSManagement findByPKey(@Param("userId") String userId);

	@SelectProvider(type = RSManagementSqlProvider.class, method = "findAll")
	List<RSManagement> findAll();

	@InsertProvider(type = RSManagementSqlProvider.class, method = "insert")
	void insert(RSManagement rsManagement);

	@UpdateProvider(type = RSManagementSqlProvider.class, method = "update")
	void update(RSManagement rsManagement);

	@DeleteProvider(type = RSManagementSqlProvider.class, method = "delete")
	void delete(@Param("userId") String userId);
	
	@DeleteProvider(type = RSManagementSqlProvider.class, method = "deleteAll")
	void deleteAll();
}
