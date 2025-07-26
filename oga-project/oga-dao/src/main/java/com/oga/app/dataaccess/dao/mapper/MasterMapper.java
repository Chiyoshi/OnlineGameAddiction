package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.oga.app.dataaccess.dao.sqlprovider.MasterSqlProvider;
import com.oga.app.dataaccess.entity.Master;

@Mapper
public interface MasterMapper {

	@SelectProvider(type = MasterSqlProvider.class, method = "findByPKey")
	Master findByPKey(String key);

	@SelectProvider(type = MasterSqlProvider.class, method = "findAll")
	List<Master> findAll();

	@InsertProvider(type = MasterSqlProvider.class, method = "insertMaster")
	void insertMaster(Master master);

	@DeleteProvider(type = MasterSqlProvider.class, method = "deleteMaster")
	void deleteMaster(String key);

	@DeleteProvider(type = MasterSqlProvider.class, method = "deleteAllMaster")
	void deleteAllMaster();
}
