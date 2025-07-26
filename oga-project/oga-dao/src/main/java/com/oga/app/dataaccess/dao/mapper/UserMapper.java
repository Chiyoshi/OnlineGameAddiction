package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oga.app.dataaccess.dao.sqlprovider.UserSqlProvider;
import com.oga.app.dataaccess.entity.User;

@Mapper
public interface UserMapper {

	@SelectProvider(type = UserSqlProvider.class, method = "findByPKey")
	User findByPKey(String userId);

	@SelectProvider(type = UserSqlProvider.class, method = "findAll")
	List<User> findAll();

	@SelectProvider(type = UserSqlProvider.class, method = "findByUserInfoList")
	List<User> findByUserInfoList();

	@InsertProvider(type = UserSqlProvider.class, method = "insertUser")
	void insertUser(User user);

	@UpdateProvider(type = UserSqlProvider.class, method = "updateUser")
	void updateUser(User user);

	@DeleteProvider(type = UserSqlProvider.class, method = "deleteUser")
	void deleteUser(String userId);
	
	@DeleteProvider(type = UserSqlProvider.class, method = "deleteAllUser")
	void deleteAllUser();
}
