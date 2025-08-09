package com.oga.app.batch;

import java.io.File;
import java.util.List;

import com.oga.app.batch.base.BatchBase;
import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.FileUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.RSManagement;
import com.oga.app.dataaccess.entity.User;
import com.oga.app.service.provider.redstone.batch.RedstoneBatchProvider;

public class ImportCsvFileBatch extends BatchBase {

	/** CSVファイル名(user.csv) */
	private final String CSV_FILE_USER = "user.csv";

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
				insertRSManagement(dataList);
				break;
			default:
				break;
			}

		}

	}

	@Override
	public void post() throws ApplicationException, SystemException {
	}

	private void insertUser(List<String[]> dataList) {
		// ユーザ情報を削除する
		LogUtil.info("[DELETE] [USER] [START]");
		RedstoneBatchProvider.getInstance().deleteAllUser();
		LogUtil.info("[DELETE] [USER] [END]");

		LogUtil.info("[INSERT] [USER] [START]");

		for (String[] data : dataList) {
			User user = User.builder()
					.userId(data[0])
					.password(data[1])
					.deleteFlg(YesNo.NO.getValue())
					.build();

			LogUtil.info(user.toString());

			// ユーザ情報を登録する
			RedstoneBatchProvider.getInstance().insertUser(user);
		}

		LogUtil.info("[INSERT] [USER] [END]");
	}

	private void insertRSManagement(List<String[]> dataList) {
		LogUtil.info("[DELETE] [RSMANAGEMENT] [START]");
		RedstoneBatchProvider.getInstance().deleteAllUser();
		LogUtil.info("[DELETE] [RSMANAGEMENT] [END]");

		LogUtil.info("[INSERT] [RSMANAGEMENT] [START]");

		for (String[] data : dataList) {
			RSManagement rsManagement = RSManagement.builder()
					.userId(data[0])
					.gem("0")
					.servicePoint("0")
					.redsPoint("0")
					.loginCampaignFlg(data[2])
					.dailyRewardFlg(data[3])
					.rouletteFlg(data[4])
					.deleteFlg(YesNo.NO.getValue())
					.build();

			LogUtil.info(rsManagement.toString());

			// レッドストーン管理情報を登録する
			RedstoneBatchProvider.getInstance().insertRSManagement(rsManagement);
		}

		LogUtil.info("[INSERT] [RSMANAGEMENT] [END]");
	}

	public static void main(String[] args) {
		new ImportCsvFileBatch().run(args);
	}

}
