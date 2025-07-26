package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.oga.app.dataaccess.dao.sqlprovider.MenuItemSqlProvider;
import com.oga.app.dataaccess.entity.MenuItem;

@Mapper
public interface MenuItemMapper {

	@SelectProvider(type = MenuItemSqlProvider.class, method = "findByPKey")
	MenuItem findByPKey(@Param("menuId") String menuId, @Param("menuItemId") String menuItemId);

	@SelectProvider(type = MenuItemSqlProvider.class, method = "findAll")
	List<MenuItem> findAll();
}
