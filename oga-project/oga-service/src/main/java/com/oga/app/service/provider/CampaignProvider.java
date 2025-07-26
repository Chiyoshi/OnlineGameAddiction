package com.oga.app.service.provider;

import java.util.List;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.dataaccess.dao.CampaignDao;
import com.oga.app.dataaccess.entity.Campaign;

/**
 * キャンペーン情報プロパイダ
 */
public class CampaignProvider {

	/** キャンペーン情報プロパイダ */
	private static CampaignProvider masterProvider;

	/**
	 * コンストラクタ
	 */
	public CampaignProvider() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized CampaignProvider getInstance() {
		if (masterProvider == null) {
			masterProvider = new CampaignProvider();
		}
		return masterProvider;
	}

	/**
	 * キャンペーン情報を取得する
	 * 
	 * @param campaignId キャンペーンID
	 * @return キャンペーン情報
	 */
	public Campaign getCampaign(String campaignId) {
		return CampaignDao.getInstance().findByPKey(campaignId);
	}

	/**
	 * キャンペーン種別、対象日を指定してキャンペーン情報を取得する
	 * 
	 * @param campaignType キャンペーン種別
	 * @param targetDate 対象日
	 * @return キャンペーン情報
	 * @throws ApplicationException 
	 */
	public Campaign getCampaignByTargetDate(String campaignType, String targetDate) throws ApplicationException {
		List<Campaign> campaignList = this.getCampaignList(campaignType, targetDate);

		// キャンペーン情報が登録されていない場合はエラーとする
		if (campaignList == null || campaignList.isEmpty()) {
			throw new ApplicationException("キャンペーン情報が未登録、または、期間対象外です。");
		}
		// キャンペーン情報が2つ以上存在する場合は想定外のためエラーとする
		else if (campaignList.size() > 1) {
			throw new ApplicationException("キャンペーン情報の対象期間が重複して登録されています。");
		}

		return campaignList.get(0);
	}

	/**
	 * キャンペーン種別、対象日を指定してキャンペーン情報を取得する
	 * 
	 * @param campaignType キャンペーン種別
	 * @param targetDate 対象日
	 * @return キャンペーン情報
	 */
	public List<Campaign> getCampaignList(String campaignType, String targetDate) {
		return CampaignDao.getInstance().findByCampaignDate(campaignType, targetDate);
	}

	/**
	 * 対象キャンペーンが開催中か否か判断する
	 * 
	 * @param campaignType キャンペーン種別
	 * @param targetDate 対象日
	 * @return
	 */
	public boolean isNowCampaign(String campaignType, String targetDate) {
		List<Campaign> campaignList = this.getCampaignList(campaignType, targetDate);
		return campaignList != null && !campaignList.isEmpty();
	}
	
	/**
	 * キャンペーン情報リストを取得する
	 * @return
	 */
	public List<Campaign> getCampaignList() {
		return CampaignDao.getInstance().findAll();
	}

	/**
	 * キャンペーン情報を登録する
	 * 
	 * @param campaign キャンペーン情報
	 */
	public void insertCampaign(Campaign campaign) {
		CampaignDao.getInstance().insert(campaign);
	}

	/**
	 * 指定したキャンペーン情報を削除する
	 * 
	 * @param campaignId キャンペーンID
	 */
	public void deleteCampaign(String campaignId) {
		CampaignDao.getInstance().delete(campaignId);
	}

	/**
	 * キャンペーン情報をすべて削除する
	 */
	public void deleteCampaign() {
		CampaignDao.getInstance().delete();
	}
}
