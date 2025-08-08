package com.oga.app.batch;

import java.io.File;
import java.util.List;

import com.oga.app.common.enums.YesNo;
import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.FileUtil;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.User;
import com.oga.app.service.provider.redstone.management.UserProvider;

public class ImportCsvFileBatch extends BatchBase {

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
			User user = User.builder()
					.userId(data[0])
					.password(data[1])
					.deleteFlg(YesNo.NO.getValue())
					.build();

			LogUtil.info(user.toString());

			// ユーザ情報を登録する
			UserProvider.getInstance().insertUser(user);
		}

		LogUtil.info("[INSERT] [USER] [END]");
	}


}
