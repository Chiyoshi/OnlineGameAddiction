package com.oga.app.service.provider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.Status;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.dataaccess.dao.DailyWorkResultDao;
import com.oga.app.dataaccess.entity.Campaign;
import com.oga.app.dataaccess.entity.DailyWorkResult;
import com.oga.app.service.manager.MasterDataManager;

/**
 * 日次作業結果プロパイダ
 */
public class DailyWorkResultProvider {

	/** 日次作業結果プロパイダ */
	private static DailyWorkResultProvider dailyWorkResultProvider;

	/**
	 * コンストラクタ
	 */
	public DailyWorkResultProvider() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized DailyWorkResultProvider getInstance() {
		if (dailyWorkResultProvider == null) {
			dailyWorkResultProvider = new DailyWorkResultProvider();
		}
		return dailyWorkResultProvider;
	}

	/**
	 * 日次作業結果を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetDate 対象日
	 * @param serviceType サービス種別
	 * @return
	 */
	public DailyWorkResult getDailyWorkResult(String userId, String targetDate, String serviceType) {
		return DailyWorkResultDao.getInstance().findByPKey(userId, targetDate, serviceType);
	}

	/**
	 * 対象期間を指定した日次作業結果を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetDateFrom 対象日From
	 * @param targetDateTo 対象日 To
	 * @param serviceType サービス種別
	 * @return
	 */
	public List<DailyWorkResult> getDailyWorkResultByTargetDate(String userId, String targetDateFrom,
			String targetDateTo, String serviceType) {
		return DailyWorkResultDao.getInstance().findByTargetDate(userId, targetDateFrom, targetDateTo, serviceType);
	}

	/**
	 * 対象日付の日次作業が実施済みか否か判断する
	 * 
	 * @param userId ユーザID
	 * @param targetDate 対象日
	 * @param serviceType サービス種別
	 * @return
	 */
	public boolean isExecutionNeeded(String userId, String targetDate, String serviceType) {
		DailyWorkResult dailyWorkResult = this.getDailyWorkResult(userId, targetDate, serviceType);
		return dailyWorkResult == null || Status.ERROR.getValue().equals(dailyWorkResult.getStatus());
	}

	/**
	 * ログインキャンペーンの実施回数が上限に達しているか否か
	 * 
	 * @param userId ユーザID
	 * @param baseDate 基準日
	 * @return
	 * @throws ApplicationException
	 */
	public boolean isLimitLoginCampaign(String userId, String baseDate) throws ApplicationException {
		// キャンペーン情報を取得する
		Campaign campaign = CampaignProvider.getInstance()
				.getCampaignByTargetDate(ServiceType.LOGINCAMPAIGN.getValue(), baseDate);

		// キャンペーン期間内の日次作業結果の件数を取得する
		int loginCampaignExecCount = DailyWorkResultDao.getInstance()
				.countByTargetDate(userId, campaign.getStartDate(), campaign.getEndDate(),
						ServiceType.LOGINCAMPAIGN.getValue(), Status.SUCCESS.getValue());

		// ログインキャンペーンの各マップ回数
		int loginCampaignMapNum1 = Integer
				.parseInt(MasterDataManager.getInstance().get("redstone.logincampaign.map1.num"));
		int loginCampaignMapNum2 = Integer
				.parseInt(MasterDataManager.getInstance().get("redstone.logincampaign.map2.num"));
		int loginCampaignMapNum3 = Integer
				.parseInt(MasterDataManager.getInstance().get("redstone.logincampaign.map3.num"));

		return loginCampaignExecCount >= (loginCampaignMapNum1 + loginCampaignMapNum2 + loginCampaignMapNum3);
	}

	/**
	 * 対象キャンペーンの獲得アイテムの集計結果を取得する
	 * 
	 * @param campaign キャンペーン情報
	 * @param targetRewardItemList 集計対象の獲得アイテム
	 * @return 獲得アイテム集計結果リスト
	 */
	public List<Object[]> getRewardItemAggregateList(Campaign campaign, List<String> targetRewardItemList) {

		// サービス種別
		String serviceType = null;

		// キャンペーン種別が「1：ログインキャンペーン」の場合
		if (ServiceType.LOGINCAMPAIGN.getValue().equals(campaign.getCampaignType())) {
			serviceType = ServiceType.LOGINCAMPAIGN.getValue();
		}
		// キャンペーン種別が「4：ルーレット」の場合
		else if (ServiceType.ROULETTE.getValue().equals(campaign.getCampaignType())) {
			serviceType = ServiceType.ROULETTE.getValue();
		}

		// 獲得アイテムの集計結果を取得する
		List<LinkedHashMap<String, Object>> rewardItemAggregateList = DailyWorkResultDao.getInstance()
				.findByRewardItemAggregate(
						campaign.getStartDate(), campaign.getEndDate(),
						serviceType, Status.SUCCESS.getValue(), targetRewardItemList);

		// 獲得アイテム集計結果リスト
		List<Object[]> data = new ArrayList<Object[]>();

		for (Map<String, Object> row : rewardItemAggregateList) {
			// 一時的なリスト
			List<Object> tempList = new ArrayList<>();

			for (Map.Entry<String, Object> entry : row.entrySet()) {
				tempList.add(entry.getValue());
			}

			// 配列に変換して格納する
			data.add(tempList.toArray());
		}

		return data;
	}

	/**
	 * 日次作業結果を登録する
	 * 
	 * @param userId ユーザID
	 */
	public void insertDailyWorkResult(DailyWorkResult dailyWorkResult) {
		DailyWorkResultDao.getInstance().insert(dailyWorkResult);
	}

	/**
	 * 指定した日次作業結果を削除する
	 * 
	 * @param userId ユーザID
	 */
	public void deleteDailyWorkResult(String userId, String baseDate, String serviceType) {
		DailyWorkResultDao.getInstance().delete(userId, baseDate, serviceType);
	}

	/**
	 * 日次作業結果をすべて削除する
	 */
	public void deleteDailyWorkResult() {
		DailyWorkResultDao.getInstance().delete();
	}
}
