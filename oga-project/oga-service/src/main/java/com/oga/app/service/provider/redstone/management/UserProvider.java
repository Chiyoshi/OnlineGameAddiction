package com.oga.app.service.provider.redstone.management;

import java.util.List;

import com.oga.app.dataaccess.dao.UserDao;
import com.oga.app.dataaccess.entity.User;

/**
 * ユーザ情報プロパイダ
 */
public class UserProvider {

	/** ユーザ情報プロパイダ */
	private static UserProvider userProvider;

	/**
	 * コンストラクタ
	 */
	public UserProvider() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized UserProvider getInstance() {
		if (userProvider == null) {
			userProvider = new UserProvider();
		}
		return userProvider;
	}

	/**
	 * ユーザ情報リストを取得する
	 * @return
	 */
	public List<User> getUserList() {
		return UserDao.getInstance().findAll();
	}

	/**
	 * ユーザ情報を登録する
	 * 
	 * @param user ユーザ情報
	 */
	public void insertUser(User user) {
		UserDao.getInstance().insert(user);
	}

	/**
	 * 指定したユーザ情報を削除する
	 * 
	 * @param userId ユーザID
	 */
	public void deleteUser(String userId) {
		UserDao.getInstance().delete(userId);
	}

	/**
	 * ユーザ情報をすべて削除する
	 */
	public void deleteUser() {
		UserDao.getInstance().delete();
	}
}
