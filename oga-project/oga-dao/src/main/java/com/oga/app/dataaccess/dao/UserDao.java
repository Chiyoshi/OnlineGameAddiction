package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.UserMapper;
import com.oga.app.dataaccess.entity.User;

/**
 * ���[�U���e�[�u���𑀍삷��N���X
 */
public class UserDao extends BaseDao {

	/** ���[�U���e�[�u��DAO */
	private static UserDao userDao;

	/**
	 * �R���X�g���N�^
	 */
	private UserDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized UserDao getInstance() {
		if (userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}

	/**
	 * ��L�[�Ŏw�肵�����[�U�����擾����
	 * 
	 * @param userId
	 * @return
	 */
	public User findByPKey(String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.findByPKey(userId);
		}
	}

	/**
	 * ���[�U��񃊃X�g���擾����
	 * 
	 * @return
	 */
	public List<User> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ���[�U���i������ƃt���O���܂ށj���X�g���擾����
	 * 
	 * @return
	 */
	public List<User> findByUserInfoList() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.findByUserInfoList();
		}
	}

	/**
	 * ���[�U����o�^����
	 * 
	 * @return
	 */
	public void insert(User user) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.insertUser(user);
			session.commit();
		}
	}

	/**
	 * ���[�U�����X�V����
	 * 
	 * @return
	 */
	public void update(User user) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.updateUser(user);
			session.commit();
		}
	}

	/**
	 * �w�肵�����[�U�����폜����
	 * 
	 * @return
	 */
	public void delete(String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.deleteUser(userId);
			session.commit();
		}
	}

	/**
	 * ���[�U�������ׂč폜����
	 * 
	 * @return
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.deleteAllUser();
			session.commit();
		}
	}
}
