package com.oga.app.service.provider;

import java.util.ArrayList;
import java.util.List;

import com.oga.app.common.enums.ServiceType;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.dataaccess.dao.DailyWorkDao;
import com.oga.app.dataaccess.entity.DailyWork;

/**
 * 日次作業情報プロパイダ
 */
public class DailyWorkProvider {

	/** 日次作業情報プロパイダ */
	private static DailyWorkProvider dailyWorkProvider;

	/**
	 * コンストラクタ
	 */
	public DailyWorkProvider() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized DailyWorkProvider getInstance() {
		if (dailyWorkProvider == null) {
			dailyWorkProvider = new DailyWorkProvider();
		}
		return dailyWorkProvider;
	}

	/**
	 * 日次作業の対象者リストを取得する
	 * 
	 * @return
	 * @throws SystemException 
	 */
	public List<DailyWork> getTargetDailyWorklList(String baseDate) throws ApplicationException, SystemException {

		List<DailyWork> targetDailyWorkList = new ArrayList<>();

		// キャンペーンの開催状況を取得
		CampaignProvider campaignProvider = CampaignProvider.getInstance();
		boolean isNowLoginCampaign = campaignProvider.isNowCampaign(ServiceType.LOGINCAMPAIGN.getValue(), baseDate);
		boolean isNowRoulette = campaignProvider.isNowCampaign(ServiceType.ROULETTE.getValue(), baseDate);

		// 日次作業情報を取得
		List<DailyWork> dailyWorksList = this.getDailyWork();
		DailyWorkResultProvider resultProvider = DailyWorkResultProvider.getInstance();

		// 日次作業の処理対象者を取得する
		for (DailyWork dailyWork : dailyWorksList) {
			 // 初期化
			boolean shouldExecute = false;

			// ログインキャンペーンが開催中、かつ、ログインキャンペーンフラグが「Y」の場合
			if (isNowLoginCampaign && YesNo.YES.getValue().equals(dailyWork.getLoginCampaignFlg())) {
				try {
					shouldExecute = !resultProvider.isLimitLoginCampaign(dailyWork.getUserId(), baseDate);
				} catch (ApplicationException e) {
					throw e;
				}

				shouldExecute = shouldExecute && resultProvider.isExecutionNeeded(dailyWork.getUserId(), baseDate,
						ServiceType.LOGINCAMPAIGN.getValue());
			}

			// ルーレットが開催中、かつ、ルーレットフラグが「Y」の場合
			if (isNowRoulette && YesNo.YES.getValue().equals(dailyWork.getRouletteFlg())) {
				shouldExecute = resultProvider.isExecutionNeeded(dailyWork.getUserId(), baseDate,
						ServiceType.ROULETTE.getValue());
			}

			// デイリーリワードフラグが「Y」の場合
			if (YesNo.YES.getValue().equals(dailyWork.getDailyRewardFlg())) {
				shouldExecute = resultProvider.isExecutionNeeded(dailyWork.getUserId(), baseDate,
						ServiceType.DAILYREWARD.getValue());
			}

			if (shouldExecute) {
				targetDailyWorkList.add(dailyWork);
			}
		}

		return targetDailyWorkList;
	}

	/**
	 * 日次作業情報を取得する
	 * @return
	 */
	public DailyWork getDailyWork(String userId) {
		return DailyWorkDao.getInstance().findByPKey(userId);
	}

	/**
	 * 日次作業情報を取得する
	 * @return
	 */
	public List<DailyWork> getDailyWork() {
		return DailyWorkDao.getInstance().findAllWithUser();
	}

	/**
	 * 日次作業情報を登録する
	 * 
	 * @param userId ユーザID
	 */
	public void insertDailyWork(DailyWork dailyWork) {
		DailyWorkDao.getInstance().insert(dailyWork);
	}

	/**
	 * 指定した日次作業情報を削除する
	 * 
	 * @param userId ユーザID
	 */
	public void deleteDailyWork(String userId) {
		DailyWorkDao.getInstance().delete(userId);
	}

	/**
	 * 日次作業情報をすべて削除する
	 */
	public void deleteDailyWork() {
		DailyWorkDao.getInstance().delete();
	}
}
