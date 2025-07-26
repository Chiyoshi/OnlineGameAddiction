package com.oga.app.batch;

import java.io.File;
import java.util.List;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.FileUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.Campaign;
import com.oga.app.dataaccess.entity.DailyWork;
import com.oga.app.dataaccess.entity.DailyWorkResult;
import com.oga.app.dataaccess.entity.Master;
import com.oga.app.dataaccess.entity.User;
import com.oga.app.service.provider.CampaignProvider;
import com.oga.app.service.provider.DailyWorkProvider;
import com.oga.app.service.provider.DailyWorkResultProvider;
import com.oga.app.service.provider.MasterProvider;
import com.oga.app.service.provider.UserProvider;

public class ImportCsvFileBatch extends BaseBatch {

	/** CSVファイル名(user.csv) */
	private final String CSV_FILE_USER = "user.csv";

	/** CSVファイル名(dailywork.csv) */
	private final String CSV_FILE_DAILYWORK = "dailywork.csv";

	/** CSVファイル名(dailyworkresult.csv) */
	private final String CSV_FILE_DAILYWORKRESULT = "dailyworkresult.csv";

	/** CSVファイル名(campaign.csv) */
	private final String CSV_FILE_CAMPAIGN = "campaign.csv";

	/** CSVファイル名(master.csv) */
	private final String CSV_FILE_MASTER = "master.csv";

	/** CSV格納先ディレクトリ */
	private String INPUT_CSV_PATH = null;

	@Override
	public void pre(String[] args) throws ApplicationException, SystemException {
		// 環境変数からパスを取得する
		String inputCsvPath = System.getProperty("input.dir");

		if (StringUtil.isNullOrEmpty(inputCsvPath)) {
			throw new SystemException("環境変数が設定されていません。：input.dir");
		}

		// ディレクトリ存在チェック
		if (!FileUtil.isExists(inputCsvPath)) {
			throw new ApplicationException("CSV格納先のディレクトリが存在しません。：" + inputCsvPath);
		}

		INPUT_CSV_PATH = inputCsvPath;
	}

	@Override
	public void exec() throws ApplicationException, SystemException {

		// CSVファイル一覧を取得する
		List<File> files = FileUtil.getFileList(INPUT_CSV_PATH);

		for (File file : files) {

			// CSVファイル名を取得する
			String csvFileName = file.getName().toLowerCase();

			// CSVファイルを読み込む 
			List<String[]> dataList = FileUtil.readCsvFile(file.getAbsolutePath());

			// ファイルごとに処理を分岐する
			switch (csvFileName) {
			case CSV_FILE_USER:
				// Userテーブルを登録する
				insertUser(dataList);
				break;
			case CSV_FILE_DAILYWORK:
				// DailyWorkテーブルを登録する
				insertDailyWork(dataList);
				break;
			case CSV_FILE_DAILYWORKRESULT:
				// DailyWorkResultテーブルを登録する
				insertDailyWorkResult(dataList);
				break;
			case CSV_FILE_CAMPAIGN:
				// Campaignテーブルを登録する
				insertCampaign(dataList);
				break;
			case CSV_FILE_MASTER:
				// Masterテーブルを登録する
				insertMaster(dataList);
				break;
			default:
				break;
			}

		}

	}

	@Override
	public void post() throws ApplicationException, SystemException {
	}

	/**
	 * CSVファイルの内容をUserテーブルに登録する
	 * 
	 * @param dataList データリスト
	 */
	private void insertUser(List<String[]> dataList) {
		// ユーザ情報を削除する
		LogUtil.info("[DELETE] [USER] [START]");
		UserProvider.getInstance().deleteUser();
		LogUtil.info("[DELETE] [USER] [END]");

		LogUtil.info("[INSERT] [USER] [START]");

		for (String[] data : dataList) {
			User user = new User();
			user.setUserId(data[0]);
			user.setPassword(data[1]);
			user.setBirthDay(data[2]);
			user.setMailAddress(data[3]);
			user.setGem(data[4]);
			user.setServicePoint(data[5]);
			user.setRedspoint(data[6]);
			//user.setRegistrationDate(data[7]);
			//user.setUpdateDate(data[8]);
			user.setDeleteFlg(data[9]);
			user.setDeleteDate(data[10]);

			LogUtil.info(user.toString());

			// ユーザ情報を登録する
			UserProvider.getInstance().insertUser(user);
		}

		LogUtil.info("[INSERT] [USER] [END]");
	}

	/**
	 * CSVファイルの内容をDailyWorkテーブルに登録する
	 * 
	 * @param dataList データリスト
	 */
	private void insertDailyWork(List<String[]> dataList) {
		// 日次作業情報を削除する
		LogUtil.info("[DELETE] [DAILYWORK] [START]");
		DailyWorkProvider.getInstance().deleteDailyWork();
		LogUtil.info("[DELETE] [DAILYWORK] [END]");

		LogUtil.info("[INSERT] [DAILYWORK] [START]");

		for (String[] data : dataList) {
			DailyWork dailyWork = new DailyWork();
			dailyWork.setUserId(data[0]);
			dailyWork.setLoginCampaignFlg(data[1]);
			dailyWork.setDailyRewardFlg(data[2]);
			dailyWork.setRouletteFlg(data[3]);
			dailyWork.setLastLoginCampaignDate(data[4]);
			dailyWork.setLastDailyRewardDate(data[5]);
			dailyWork.setLastRouletteDate(data[6]);
			//dailyWork.setRegistrationDate(data[7]);
			//dailyWork.setUpdateDate(data[8]);
			dailyWork.setDeleteFlg(data[9]);
			dailyWork.setDeleteDate(data[10]);

			LogUtil.info(dailyWork.toString());

			// 日次作業情報を登録する
			DailyWorkProvider.getInstance().insertDailyWork(dailyWork);
		}

		LogUtil.info("[INSERT] [DAILYWORK] [END]");
	}

	/**
	 * CSVファイルの内容をDailyWorkResultテーブルに登録する
	 * 
	 * @param dataList データリスト
	 */
	private void insertDailyWorkResult(List<String[]> dataList) {
		// 日次作業結果を削除する
		LogUtil.info("[DELETE] [DAILYWORKRESULT] [START]");
		DailyWorkResultProvider.getInstance().deleteDailyWorkResult();
		LogUtil.info("[DELETE] [DAILYWORKRESULT] [END]");

		LogUtil.info("[INSERT] [DAILYWORKRESULT] [START]");

		for (String[] data : dataList) {
			DailyWorkResult dailyWorkResult = new DailyWorkResult();
			dailyWorkResult.setUserId(data[0]);
			dailyWorkResult.setBaseDate(data[1]);
			dailyWorkResult.setServiceType(data[2]);
			dailyWorkResult.setStatus(data[3]);
			dailyWorkResult.setRewardItem(data[4]);
			dailyWorkResult.setRewardItemImage(data[5]);
			//dailyWorkResult.setRegistrationDate(data[6]);
			//dailyWorkResult.setUpdateDate(data[7]);
			dailyWorkResult.setDeleteFlg(data[8]);
			dailyWorkResult.setDeleteDate(data[9]);

			LogUtil.info(dailyWorkResult.toString());

			// 日次作業結果を登録する
			DailyWorkResultProvider.getInstance().insertDailyWorkResult(dailyWorkResult);
		}

		LogUtil.info("[INSERT] [DAILYWORKRESULT] [END]");
	}

	/**
	 * CSVファイルの内容をCampaignテーブルに登録する
	 * 
	 * @param dataList データリスト
	 */
	private void insertCampaign(List<String[]> dataList) {
		// キャンペーン情報を削除する
		LogUtil.info("[DELETE] [CAMPAIGN] [START]");
		CampaignProvider.getInstance().deleteCampaign();
		LogUtil.info("[DELETE] [CAMPAIGN] [END]");

		LogUtil.info("[INSERT] [CAMPAIGN] [START]");

		for (String[] data : dataList) {
			Campaign campaign = new Campaign();
			campaign.setCampaignId(data[0]);
			campaign.setCampaignType(data[1]);
			campaign.setCampaignName(data[2]);
			campaign.setStartDate(data[3]);
			campaign.setEndDate(data[4]);
			//campaign.setRegistrationDate(data[5]);
			//campaign.setUpdateDate(data[6]);
			campaign.setDeleteFlg(data[7]);
			campaign.setDeleteDate(data[8]);

			LogUtil.info(campaign.toString());

			// ユーザ情報を登録する
			CampaignProvider.getInstance().insertCampaign(campaign);
		}

		LogUtil.info("[INSERT] [CAMPAIGN] [END]");
	}

	/**
	 * CSVファイルの内容をMasterテーブルに登録する
	 * 
	 * @param dataList データリスト
	 */
	private void insertMaster(List<String[]> dataList) {
		// マスタ情報を削除する
		LogUtil.info("[DELETE] [MASTER] [START]");
		MasterProvider.getInstance().deleteMaster();
		LogUtil.info("[DELETE] [MASTER] [END]");

		LogUtil.info("[INSERT] [MASTER] [START]");

		for (String[] data : dataList) {
			Master master = new Master();
			master.setKey(data[0]);
			master.setValue(data[1]);
			master.setExplanation(data[2]);
			master.setOrder(Integer.parseInt(data[3]));
			//master.setRegistrationDate(data[4]);
			//master.setUpdateDate(data[5]);
			master.setDeleteFlg(data[6]);
			master.setDeleteDate(data[7]);

			LogUtil.info(master.toString());

			// マスタ情報を登録する
			MasterProvider.getInstance().insertMaster(master);
		}

		LogUtil.info("[INSERT] [MASTER] [END]");
	}

	public static void main(String[] args) {
		new ImportCsvFileBatch().run(args);
	}

}
