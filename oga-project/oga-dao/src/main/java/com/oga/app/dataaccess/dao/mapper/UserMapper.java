package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oga.app.dataaccess.dao.sqlprovider.UserSqlProvider;
import com.oga.app.dataaccess.entity.User;

@Mapper
public interface UserMapper {

	@SelectProvider(type = UserSqlProvider.class, method = "findByPKey")
	User findByPKey(@Param("userId") String userId);

	@SelectProvider(type = UserSqlProvider.class, method = "findAll")
	List<User> findAll();

	@InsertProvider(type = UserSqlProvider.class, method = "insert")
	void insert(User user);

	@UpdateProvider(type = UserSqlProvider.class, method = "update")
	void update(User user);

	@DeleteProvider(type = UserSqlProvider.class, method = "delete")
	void delete(@Param("userId") String userId);
	
	@DeleteProvider(type = UserSqlProvider.class, method = "deleteAll")
	void deleteAll();
}
