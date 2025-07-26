package com.oga.app.batch;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.LogUtil;

public abstract class BaseBatch {

	/**
	 * バッチ実行
	 */
	protected void run(String[] args) {
		try {
			// ログ出力
			System.setProperty("log4j.configurationFile", "log4j2_batch.xml");

			// 初期化
			LogUtil.info("[初期化処理] [START]");
			init();
			LogUtil.info("[初期化処理] [END]");

			// 前処理
			LogUtil.info("[前処理] [START]");
			pre(args);
			LogUtil.info("[前処理] [END]");

			// 主処理
			LogUtil.info("[実処理] [START]");
			exec();
			LogUtil.info("[実処理] [END]");

		} catch (SystemException e) {
			LogUtil.error(e.getMessage(), e);
		} catch (ApplicationException e) {
			LogUtil.error(e.getMessage(), e);
		} catch (Throwable th) {
			LogUtil.error(th.getMessage(), th);
		} finally {
			try {

				// 後処理
				LogUtil.info("[後処理] [START]");
				post();
				LogUtil.info("[後処理] [END]");

			} catch (ApplicationException e) {
				LogUtil.error(e.getMessage(), e);
			} catch (SystemException e) {
				LogUtil.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 初期化処理
	 * 
	 * @throws ApplicationException 
	 */
	private void init() throws ApplicationException {
//		// マスタデータにマスタ情報を設定する
//		MasterServiceImpl.getInstance().createMasterData();
//		// マスタデータに設定情報を設定する
//		SettingServiceImpl.getInstance().createMasterData();

//		// WEBドライバー
//		String webdriver = System.getProperty("webdriver");
//		
//		if (StringUtil.isNullOrEmpty(webdriver)) {
//			throw new ApplicationException("WEBドライバーを環境変数に設定してください");
//		}
//		
//		if (MasterData.contains("webdriver.chrome.driver")) {
//			MasterData.remove("webdriver.chrome.driver");
//		}
//
//		// マスタデータにWEBドライバーを設定する
//		MasterData.put("webdriver.chrome.driver", System.getProperty("webdriver"));
	}

	/**
	 * 前処理
	 */
	abstract public void pre(String[] args) throws ApplicationException, SystemException;

	/**
	 * 主処理
	 */
	abstract public void exec() throws ApplicationException, SystemException;

	/**
	 * 後処理
	 */
	abstract public void post() throws ApplicationException, SystemException;

}
