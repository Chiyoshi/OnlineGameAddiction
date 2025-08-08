package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.RSManagementMapper;
import com.oga.app.dataaccess.entity.RSManagement;

/**
 * レッドストーン管理テーブルを操作するクラス
 */
public class RSManagementDao extends DaoBase {

	/** レッドストーン管理テーブルDAO */
	private static RSManagementDao rsManagementDao;

	/**
	 * コンストラクタ
	 */
	private RSManagementDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RSManagementDao getInstance() {
		if (rsManagementDao == null) {
			rsManagementDao = new RSManagementDao();
		}
		return rsManagementDao;
	}

	/**
	 * 主キーで指定したレッドストーン管理を取得する
	 * 
	 * @param userId
	 * @return レッドストーン管理
	 */
	public RSManagement findByPKey(String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RSManagementMapper mapper = session.getMapper(RSManagementMapper.class);
			return mapper.findByPKey(userId);
		}
	}

	/**
	 * レッドストーン管理リストを取得する
	 * 
	 * @return レッドストーン管理リスト
	 */
	public List<RSManagement> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RSManagementMapper mapper = session.getMapper(RSManagementMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * レッドストーン管理を登録する
	 * 
	 * @param rsManagement レッドストーン管理
	 */
	public void insert(RSManagement rsManagement) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RSManagementMapper mapper = session.getMapper(RSManagementMapper.class);
			mapper.insert(rsManagement);
			session.commit();
		}
	}

	/**
	 * レッドストーン管理を更新する
	 * 
	 * @param rsManagement レッドストーン管理
	 */
	public void update(RSManagement rsManagement) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RSManagementMapper mapper = session.getMapper(RSManagementMapper.class);
			mapper.update(rsManagement);
			session.commit();
		}
	}

	/**
	 * 指定したレッドストーン管理を削除する
	 * 
	 * @param userId
	 */
	public void delete(String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RSManagementMapper mapper = session.getMapper(RSManagementMapper.class);
			mapper.delete(userId);
			session.commit();
		}
	}

	/**
	 * レッドストーン管理をすべて削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RSManagementMapper mapper = session.getMapper(RSManagementMapper.class);
			mapper.deleteAll();
			session.commit();
		}
	}
}
