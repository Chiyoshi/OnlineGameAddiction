package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.oga.app.dataaccess.dao.sqlprovider.MenuSqlProvider;
import com.oga.app.dataaccess.entity.Menu;

@Mapper
public interface MenuMapper {

	@SelectProvider(type = MenuSqlProvider.class, method = "findByPKey")
	Menu findByPKey(@Param("menuId") String menuId);

	@SelectProvider(type = MenuSqlProvider.class, method = "findAll")
	List<Menu> findAll();
}
