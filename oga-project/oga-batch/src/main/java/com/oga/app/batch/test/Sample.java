package com.oga.app.batch.test;

import com.oga.app.dataaccess.dao.LoginCampaignDetailsDao;
import com.oga.app.dataaccess.entity.LoginCampaignDetails;

public class Sample {

	public static void main(String[] args) {
		
		LoginCampaignDetails loginCampaignDetails = LoginCampaignDetailsDao.getInstance().findByPKey("NM@yamauchi123", "202508");
		
		System.out.println("3".compareTo("21"));
		
		if (loginCampaignDetails == null || loginCampaignDetails.getStage().compareTo("21") < 0) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}
}
