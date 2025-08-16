package com.oga.app.service.provider.base;

import java.util.List;

import com.oga.app.dataaccess.dao.RSManagementDao;
import com.oga.app.dataaccess.dao.UserDao;
import com.oga.app.dataaccess.entity.RSManagement;
import com.oga.app.dataaccess.entity.User;

public abstract class RedstoneProviderBase {

	/**
	 * レッドストーン管理情報を取得する
	 * 
	 * @param userId ユーザID
	 * @return レッドストーン管理情報
	 */
	public RSManagement fetchRSManagement(String userId) {
		return RSManagementDao.getInstance().findByPKey(userId);
	}

	/**
	 * レッドストーン管理情報リストを取得する
	 * 
	 * @return
	 */
	public List<RSManagement> fetchRSManagementList() {
		return RSManagementDao.getInstance().findAll();
	}

	/**
	 * レッドストーン管理情報を登録する
	 * 
	 * @param rsManagement レッドストーン管理情報
	 */
	public void insertRSManagement(RSManagement rsManagement) {
		if (rsManagement != null) {
			RSManagementDao.getInstance().insert(rsManagement);
		}
	}

	/**
	 * レッドストーン管理情報を更新する
	 * 
	 * @param rsManagement レッドストーン管理情報
	 */
	public void updateRSManagement(RSManagement rsManagement) {
		if (rsManagement != null) {
			RSManagementDao.getInstance().update(rsManagement);
		}
	}

	/**
	 * レッドストーン管理情報を削除する
	 */
	public void deleteAllRSManagement() {
		RSManagementDao.getInstance().delete();
	}
	
	/**
	 * ユーザ情報を取得する
	 * 
	 * @param userId ユーザID
	 * @return ユーザ情報
	 */
	public User fetchUserInfo(String userId) {
		return UserDao.getInstance().findByPKey(userId);
	}

	/**
	 * ユーザ情報を登録する
	 * 
	 * @param user ユーザ情報
	 */
	public void insertUser(User user) {
		if (user != null) {
			UserDao.getInstance().insert(user);
		}
	}

	/**
	 * ユーザ情報をすべて削除する
	 */
	public void deleteAllUser() {
		UserDao.getInstance().delete();
	}
}
