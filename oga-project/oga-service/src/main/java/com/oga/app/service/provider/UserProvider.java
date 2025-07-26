package com.oga.app.service.provider;

import java.util.List;

import com.oga.app.dataaccess.dao.UserDao;
import com.oga.app.dataaccess.entity.User;

/**
 * ���[�U���v���p�C�_
 */
public class UserProvider {

	/** ���[�U���v���p�C�_ */
	private static UserProvider userProvider;

	/**
	 * �R���X�g���N�^
	 */
	public UserProvider() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized UserProvider getInstance() {
		if (userProvider == null) {
			userProvider = new UserProvider();
		}
		return userProvider;
	}

	/**
	 * ���[�U��񃊃X�g���擾����
	 * @return
	 */
	public List<User> getUserList() {
		return UserDao.getInstance().findAll();
	}

	/**
	 * ���[�U����o�^����
	 * 
	 * @param user ���[�U���
	 */
	public void insertUser(User user) {
		UserDao.getInstance().insert(user);
	}

	/**
	 * �w�肵�����[�U�����폜����
	 * 
	 * @param userId ���[�UID
	 */
	public void deleteUser(String userId) {
		UserDao.getInstance().delete(userId);
	}

	/**
	 * ���[�U�������ׂč폜����
	 */
	public void deleteUser() {
		UserDao.getInstance().delete();
	}
}
