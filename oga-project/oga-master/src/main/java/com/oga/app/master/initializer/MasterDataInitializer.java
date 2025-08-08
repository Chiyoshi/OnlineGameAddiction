package com.oga.app.master.initializer;

import com.oga.app.common.exception.SystemException;
import com.oga.app.common.prop.OgaProperty;
import com.oga.app.common.utils.LogUtil;
import com.oga.app.master.dto.CampaignDto;
import com.oga.app.master.dto.ItemDto;
import com.oga.app.master.dto.RedstoneApiDto;
import com.oga.app.master.loader.JsonMasterLoader;
import com.oga.app.master.repository.CampaignRepository;
import com.oga.app.master.repository.ItemRepository;
import com.oga.app.master.repository.RedstoneApiRepository;

public class MasterDataInitializer {

	/** レッドストーンAPI情報リポジトリ */
	private RedstoneApiRepository redstoneApiRepository;

	/** キャンペーン情報リポジトリ */
	private CampaignRepository campaignRepository;

	/** アイテム情報リポジトリ */
	private ItemRepository itemRepository;

	/**
	 * コンストラクタ
	 */
	public MasterDataInitializer() {
		this.redstoneApiRepository = new RedstoneApiRepository();
		this.campaignRepository = new CampaignRepository();
		this.itemRepository = new ItemRepository();
	}

	/**
	 * 初期処理
	 * @throws SystemException 
	 */
	public void initializeAll() throws SystemException {

		LogUtil.info("[マスタ情報] [レッドストーンAPI情報] [START]");
		
		/*
		 * レッドストーンAPI情報
		 */
		String masterDataUrlRedstoneapi = OgaProperty.getProperty("master.data.url.redstoneapi");
		RedstoneApiDto redstoneApiDto = JsonMasterLoader.loadFromUrl(masterDataUrlRedstoneapi, RedstoneApiDto.class, true);

		redstoneApiRepository.setItemList(redstoneApiDto.getRedstoneApiList());

		LogUtil.info("[マスタ情報] [レッドストーンAPI情報] [END]");
		LogUtil.info("[マスタ情報] [キャンペーン情報] [START]");

		/*
		 * キャンペーン情報
		 */
		String masterDataUrlCampaign = OgaProperty.getProperty("master.data.url.campaign");
		CampaignDto campaignDto = JsonMasterLoader.loadFromUrl(masterDataUrlCampaign, CampaignDto.class, true);
		campaignRepository.setItemList(campaignDto.getCampaignList());

		LogUtil.info("[マスタ情報] [キャンペーン情報] [END]");
		LogUtil.info("[マスタ情報] [アイテム情報] [START]");

		/**
		 * アイテム情報
		 */
		String masterDataUrlItem = OgaProperty.getProperty("master.data.url.item");
		ItemDto itemDto = JsonMasterLoader.loadFromUrl(masterDataUrlItem, ItemDto.class, true);
		itemRepository.setItemList(itemDto.getItemList());

		LogUtil.info("[マスタ情報] [アイテム情報] [END]");
	}

	/**
	 * @return redstoneApiRepository
	 */
	public RedstoneApiRepository getRedstoneApiRepository() {
		return redstoneApiRepository;
	}

	/**
	 * @return campaignRepository
	 */
	public CampaignRepository getCampaignRepository() {
		return campaignRepository;
	}

	/**
	 * @return itemRepository
	 */
	public ItemRepository getItemRepository() {
		return itemRepository;
	}
}
