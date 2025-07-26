package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.oga.app.dataaccess.dao.sqlprovider.SettingSqlProvider;
import com.oga.app.dataaccess.entity.Setting;

@Mapper
public interface SettingMapper {

	@SelectProvider(type = SettingSqlProvider.class, method = "findByPKey")
	Setting findByPKey(String key);

	@SelectProvider(type = SettingSqlProvider.class, method = "findAll")
	List<Setting> findAll();

	@InsertProvider(type = SettingSqlProvider.class, method = "insertSetting")
//	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertSetting(Setting setting);

	@InsertProvider(type = SettingSqlProvider.class, method = "updateSetting")
	void updateSetting(Setting setting);
}
