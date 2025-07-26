package com.oga.app.service.manager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.oga.app.dataaccess.entity.Master;
import com.oga.app.service.provider.MasterProvider;

public class MasterDataManager {

	/** WebDriverManager */
	private static MasterDataManager manger = null;

	/** マスタ情報 */
	private ConcurrentMap<String, String> map = null;

	/**
	 * コンストラクタ
	 */
	public MasterDataManager() {
		if (map == null) {
			this.map = new ConcurrentHashMap<>();
		}

		// 初期処理
		init();
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized MasterDataManager getInstance() {
		if (manger == null) {
			manger = new MasterDataManager();
		}
		return manger;
	}

	/**
	 * 初期処理
	 */
	public void init() {
		// マスタ情報リストを取得する
		List<Master> masterList = MasterProvider.getInstance().getMasterList();

		for (Master master : masterList) {
			if (null != master.getValue()) {
				// マスタデータに格納する
				put(master.getKey(), master.getValue());
			}
		}
	}

	/**
	 * マスタ情報を取得する
	 * 
	 * @param key キー
	 * @return 値
	 */
	public String get(String key) {
		return this.map.get(key);
	}

	/**
	 * マスタ情報を設定する
	 * 
	 * @param key   キー
	 * @param value 値
	 */
	public void put(String key, String value) {
		this.map.put(key, value);
	}

	/**
	 * マスタ情報を削除する
	 * 
	 * @param key   キー
	 * @param value 値
	 */
	public void remove(String key) {
		this.map.remove(key);
	}

	/**
	 * 存在チェック
	 * 
	 * @param key キー
	 * @return True：存在する、Fale：存在しない
	 */
	public boolean contains(String key) {
		if (this.map.containsKey(key)) {
			return true;
		}
		return false;
	}

}
