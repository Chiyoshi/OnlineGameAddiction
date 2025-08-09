package com.oga.app.service.provider.redstone.batch;

import com.oga.app.master.provider.MasterRepositoryProvider;
import com.oga.app.service.provider.base.RedstoneProviderBase;

public class RedstoneBatchProvider extends RedstoneProviderBase {

	/** RedstoneBatchProvider */
	private static RedstoneBatchProvider redstoneBatchProvider;

	/** MasterRepositoryProvider */
	private MasterRepositoryProvider masterRepositoryProvider;

	/**
	 * コンストラクタ
	 * 
	 * @param driver WEBドライバー
	 */
	public RedstoneBatchProvider() {
		this.masterRepositoryProvider = MasterRepositoryProvider.getInstance();
		this.masterRepositoryProvider = MasterRepositoryProvider.getInstance();
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RedstoneBatchProvider getInstance() {
		if (redstoneBatchProvider == null) {
			redstoneBatchProvider = new RedstoneBatchProvider();
		}
		return redstoneBatchProvider;
	}

}
