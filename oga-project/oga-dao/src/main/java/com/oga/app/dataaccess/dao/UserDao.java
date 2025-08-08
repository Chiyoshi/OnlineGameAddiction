package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.UserMapper;
import com.oga.app.dataaccess.entity.User;

/**
 * ユーザ情報テーブルを操作するクラス
 */
public class UserDao extends DaoBase {

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
	 * @return ユーザ情報
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
	 * @return ユーザ情報リスト
	 */
	public List<User> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ユーザ情報を登録する
	 * 
	 * @param user ユーザ情報
	 */
	public void insert(User user) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.insert(user);
			session.commit();
		}
	}

	/**
	 * ユーザ情報を更新する
	 * 
	 * @param user ユーザ情報
	 */
	public void update(User user) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.update(user);
			session.commit();
		}
	}

	/**
	 * 指定したユーザ情報を削除する
	 * 
	 * @param userId
	 */
	public void delete(String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.delete(userId);
			session.commit();
		}
	}

	/**
	 * ユーザ情報をすべて削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.deleteAll();
			session.commit();
		}
	}
}
