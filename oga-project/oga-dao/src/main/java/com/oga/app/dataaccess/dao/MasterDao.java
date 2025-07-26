package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.MasterMapper;
import com.oga.app.dataaccess.entity.Master;

/**
 * マスタ情報テーブルを操作するクラス
 */
public class MasterDao extends BaseDao {

	/** マスタ情報テーブルDAO */
	private static MasterDao masterDao;

	/**
	 * コンストラクタ
	 */
	public MasterDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized MasterDao getInstance() {
		if (masterDao == null) {
			masterDao = new MasterDao();
		}
		return masterDao;
	}

	/**
	 * マスタ情報リストを取得する
	 * 
	 * @return
	 */
	public List<Master> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * 主キーで指定したマスタ情報を取得する
	 * 
	 * @param key キー
	 * @return マスタ情報
	 */
	public Master findByPKey(String key) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			return mapper.findByPKey(key);
		}
	}

	/**
	 * マスタ情報を登録する
	 * 
	 * @param master マスタ情報
	 */
	public void insert(Master master) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			mapper.insertMaster(master);
			session.commit();
		}
	}

	/**
	 * 指定したマスタ情報を削除する
	 * 
	 * @param key キー
	 */
	public void delete(String key) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			mapper.deleteMaster(key);
			session.commit();
		}
	}

	/**
	 * マスタ情報を削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			mapper.deleteAllMaster();
			session.commit();
		}
	}
}
