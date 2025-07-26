package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.oga.app.dataaccess.dao.sqlprovider.CampaignSqlProvider;
import com.oga.app.dataaccess.entity.Campaign;

@Mapper
public interface CampaignMapper {

	@SelectProvider(type = CampaignSqlProvider.class, method = "findByPKey")
	Campaign findByPKey(String campaignId);

	@SelectProvider(type = CampaignSqlProvider.class, method = "findAll")
	List<Campaign> findAll();

	@SelectProvider(type = CampaignSqlProvider.class, method = "findByCampaignDate")
	List<Campaign> findByCampaignDate(
			@Param("campaignType") String campaignType,
			@Param("targetDate") String targetDate);

	@InsertProvider(type = CampaignSqlProvider.class, method = "insertCampaign")
	void insertCampaign(Campaign campaign);
	
	@DeleteProvider(type = CampaignSqlProvider.class, method = "deleteCampaign")
	void deleteCampaign(String campaignId);
	
	@DeleteProvider(type = CampaignSqlProvider.class, method = "deleteAllCampaign")
	void deleteAllCampaign();
}
