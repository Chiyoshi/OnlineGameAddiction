package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.UserMapper;
import com.oga.app.dataaccess.entity.User;

/**
 * ユーザ情報テーブルを操作するクラス
 */
public class UserDao extends BaseDao {

	/** ユーザ情報テーブルDAO */
	private static UserDao userDao;

	/**
	 * コンストラクタ
	 */
	private UserDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized UserDao getInstance() {
		if (userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}

	/**
	 * 主キーで指定したユーザ情報を取得する
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
	 * ユーザ情報リストを取得する
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
	 * ユーザ情報（日次作業フラグを含む）リストを取得する
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
	 * ユーザ情報を登録する
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
	 * ユーザ情報を更新する
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
	 * 指定したユーザ情報を削除する
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
	 * ユーザ情報をすべて削除する
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
