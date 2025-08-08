package com.oga.app.master.repository;

import com.oga.app.master.model.Campaign;

/**
 * キャンペーン情報リポジトリ
 */
public class CampaignRepository extends MasterRepository<Campaign> {

	@Override
	protected String mapKey(Campaign campaign) {
		return campaign.getCampaignId();

	}
}
