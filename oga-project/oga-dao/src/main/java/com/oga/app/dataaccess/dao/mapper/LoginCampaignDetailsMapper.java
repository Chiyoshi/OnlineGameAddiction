package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.oga.app.dataaccess.dao.sqlprovider.LoginCampaignDetailsSqlProvider;
import com.oga.app.dataaccess.entity.LoginCampaignDetails;

@Mapper
public interface LoginCampaignDetailsMapper {

	@SelectProvider(type = LoginCampaignDetailsSqlProvider.class, method = "findByPKey")
	LoginCampaignDetails findByPKey(
			@Param("userId") String userId,
			@Param("targetMonth") String targetMonth);

	@SelectProvider(type = LoginCampaignDetailsSqlProvider.class, method = "findAll")
	List<LoginCampaignDetails> findAll();

	@SelectProvider(type = LoginCampaignDetailsSqlProvider.class, method = "countByPKey")
	int countByPKey(
			@Param("userId") String userId,
			@Param("targetMonth") String targetMonth);

	@InsertProvider(type = LoginCampaignDetailsSqlProvider.class, method = "insert")
	void insert(LoginCampaignDetails loginCampaignDetails);

	@UpdateProvider(type = LoginCampaignDetailsSqlProvider.class, method = "update")
	void update(LoginCampaignDetails loginCampaignDetails);

	@DeleteProvider(type = LoginCampaignDetailsSqlProvider.class, method = "delete")
	void delete(
			@Param("userId") String userId,
			@Param("targetMonth") String targetMonth);

	@DeleteProvider(type = LoginCampaignDetailsSqlProvider.class, method = "deleteAll")
	void deleteAll();
}
