package com.oga.app.service.provider.base;

import java.util.List;

import com.oga.app.dataaccess.dao.RSManagementDao;
import com.oga.app.dataaccess.dao.UserDao;
import com.oga.app.dataaccess.entity.RSManagement;
import com.oga.app.dataaccess.entity.User;

public abstract class RedstoneProviderBase {

	/**
	 * レッドストーン管理を取得する
	 * 
	 * @param userId ユーザID
	 * @return レッドストーン管理
	 */
	public RSManagement fetchRSManagement(String userId) {
		return RSManagementDao.getInstance().findByPKey(userId);
	}

	/**
	 * レッドストーン管理リストを取得する
	 * 
	 * @return
	 */
	public List<RSManagement> fetchRSManagementList() {
		return RSManagementDao.getInstance().findAll();
	}

	/**
	 * レッドストーン管理を更新する
	 * 
	 * @param rsManagement レッドストーン管理
	 */
	public void updateRSManagement(RSManagement rsManagement) {
		if (rsManagement != null) {
			RSManagementDao.getInstance().update(rsManagement);
		}
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
}
