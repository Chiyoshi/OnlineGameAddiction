package com.oga.app.batch.base;

import com.oga.app.common.exception.ApplicationException;
import com.oga.app.common.exception.SystemException;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.master.provider.MasterRepositoryProvider;

public abstract class BatchBase {

	/**
	 * バッチ実行
	 */
	protected void run(String[] args) {
		try {
			// ログ出力
			System.setProperty("log4j.configurationFile", "log4j2.xml");

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
	 * @throws SystemException 
	 */
	private void init() throws ApplicationException, SystemException {
		MasterRepositoryProvider.getInstance().initAll();
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
