package com.oga.app.service.provider;

import java.util.List;

import com.oga.app.dataaccess.dao.MasterDao;
import com.oga.app.dataaccess.entity.Master;

/**
 * マスタ情報プロパイダ
 */
public class MasterProvider {

	/** マスタ情報プロパイダ */
	private static MasterProvider masterProvider;

	/**
	 * コンストラクタ
	 */
	public MasterProvider() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized MasterProvider getInstance() {
		if (masterProvider == null) {
			masterProvider = new MasterProvider();
		}
		return masterProvider;
	}

	/**
	 * マスタ情報リストを取得する
	 * @return
	 */
	public List<Master> getMasterList() {
		return MasterDao.getInstance().findAll();
	}

	/**
	 * マスタ情報を登録する
	 * 
	 * @param master マスタ情報
	 */
	public void insertMaster(Master master) {
		MasterDao.getInstance().insert(master);
	}

	/**
	 * 指定したマスタ情報を削除する
	 * 
	 * @param userId ユーザID
	 */
	public void deleteMaster(String key) {
		MasterDao.getInstance().delete(key);
	}

	/**
	 * マスタ情報をすべて削除する
	 */
	public void deleteMaster() {
		MasterDao.getInstance().delete();
	}
}
