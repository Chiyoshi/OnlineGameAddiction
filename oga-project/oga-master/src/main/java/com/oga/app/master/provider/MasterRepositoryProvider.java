package com.oga.app.master.provider;

import com.oga.app.common.exception.SystemException;
import com.oga.app.master.initializer.MasterDataInitializer;
import com.oga.app.master.repository.CampaignRepository;
import com.oga.app.master.repository.ItemRepository;
import com.oga.app.master.repository.RedstoneApiRepository;

public class MasterRepositoryProvider {

	/** MasterRepositoryProvider */
	private static MasterRepositoryProvider masterRepositoryProvider;

	/** MasterDataInitializer */
	private static final MasterDataInitializer initializer = new MasterDataInitializer();

	/**
	 * コンストラクタ
	 */
	public MasterRepositoryProvider() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized MasterRepositoryProvider getInstance() {
		if (masterRepositoryProvider == null) {
			masterRepositoryProvider = new MasterRepositoryProvider();
		}
		return masterRepositoryProvider;
	}

	/**
	 * 初期処理呼び出し
	 * @throws SystemException 
	 */
	public void initAll() throws SystemException {
		initializer.initializeAll();
	}

	/**
	 * @return redstoneApiRepository
	 */
	public RedstoneApiRepository getRedstoneApiRepository() {
		return initializer.getRedstoneApiRepository();
	}

	/**
	 * @return campaignRepository
	 */
	public CampaignRepository getCampaignRepository() {
		return initializer.getCampaignRepository();
	}

	/**
	 * @return itemRepository
	 */
	public ItemRepository getItemRepository() {
		return initializer.getItemRepository();
	}

	public String getRedstoneApiUrl(String name) {
		return getRedstoneApiRepository().contains(name) ? getRedstoneApiRepository().get(name).getUrl() : name;
	}

	public String getItemName(String itemCode) {
		return getItemRepository().contains(itemCode) ? getItemRepository().get(itemCode).getItemName() : itemCode;
	}
}
