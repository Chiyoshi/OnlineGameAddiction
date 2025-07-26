package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.DailyWorkMapper;
import com.oga.app.dataaccess.entity.DailyWork;

/**
 * 日次作業情報テーブルを操作するクラス
 */
public class DailyWorkDao extends BaseDao {

	private static DailyWorkDao dailyWorkDao;

	/**
	 * コンストラクタ
	 */
	private DailyWorkDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized DailyWorkDao getInstance() {
		if (dailyWorkDao == null) {
			dailyWorkDao = new DailyWorkDao();
		}
		return dailyWorkDao;
	}

	/**
	 * 日次作業情報を取得する
	 * 
	 * @return 日次作業情報リスト
	 */
	public List<DailyWork> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * 主キーで指定した日次作業情報を取得する
	 * 
	 * @param userId ユーザID
	 * @return 日次作業情報
	 */
	public DailyWork findByPKey(String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			return mapper.findByPKey(userId);
		}
	}

	/**
	 * 日次作業情報を取得する
	 * 
	 * @return 日次作業情報リスト
	 */
	public List<DailyWork> findAllWithUser() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			return mapper.findAllWithUser();
		}
	}
	
	

	/**
	 * 日次作業情報を登録する
	 * 
	 * @param dailyWork 日次作業情報Entity
	 */
	public void insert(DailyWork dailyWork) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			mapper.insertDailyWork(dailyWork);
			session.commit();
		}
	}

	/**
	 * 日次作業情報を更新する
	 * 
	 * @param dailyWork 日次作業情報Entity
	 */
	public void update(DailyWork dailyWork) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			mapper.updateDailyWork(dailyWork);
			session.commit();
		}
	}

	/**
	 * 指定した日次作業情報を削除する
	 * 
	 * @param userId ユーザID
	 */
	public void delete(String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			mapper.deleteDailyWork(userId);
			session.commit();
		}
	}

	/**
	 * 日次作業情報をすべて削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			mapper.deleteAllDailyWork();
			session.commit();
		}
	}

}
