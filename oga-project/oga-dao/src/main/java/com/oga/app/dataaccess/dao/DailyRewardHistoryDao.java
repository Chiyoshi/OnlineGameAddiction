package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.DailyRewardHistoryMapper;
import com.oga.app.dataaccess.entity.DailyRewardHistory;

/**
 * デイリーリワード履歴テーブルを操作するクラス
 */
public class DailyRewardHistoryDao extends DaoBase {

	/** デイリーリワード履歴DAO */
	private static DailyRewardHistoryDao dailyRewardHistoryDao;

	/**
	 * コンストラクタ
	 */
	private DailyRewardHistoryDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized DailyRewardHistoryDao getInstance() {
		if (dailyRewardHistoryDao == null) {
			dailyRewardHistoryDao = new DailyRewardHistoryDao();
		}
		return dailyRewardHistoryDao;
	}

	/**
	 * 主キーで指定したデイリーリワード履歴を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetDate 対象日
	 * @return デイリーリワード履歴
	 */
	public DailyRewardHistory findByPKey(String userId, String targetDate) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyRewardHistoryMapper mapper = session.getMapper(DailyRewardHistoryMapper.class);
			return mapper.findByPKey(userId, targetDate);
		}
	}

	/**
	 * デイリーリワード履歴リストを取得する
	 * 
	 * @return デイリーリワード履歴リスト
	 */
	public List<DailyRewardHistory> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyRewardHistoryMapper mapper = session.getMapper(DailyRewardHistoryMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ユーザIDで指定したデイリーリワード履歴を取得する
	 * 
	 * @param userId ユーザID
	 * @return デイリーリワード履歴リスト
	 */
	public List<DailyRewardHistory> findByUserId(String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyRewardHistoryMapper mapper = session.getMapper(DailyRewardHistoryMapper.class);
			return mapper.findByUserId(userId);
		}
	}

	/**
	 * デイリーリワード履歴を登録する
	 * 
	 * @param dailyRewardHistory デイリーリワード履歴
	 */
	public void insert(DailyRewardHistory dailyRewardHistory) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyRewardHistoryMapper mapper = session.getMapper(DailyRewardHistoryMapper.class);
			mapper.insert(dailyRewardHistory);
			session.commit();
		}
	}

	/**
	 * デイリーリワード履歴を更新する
	 * 
	 * @param DailyRewardHistory デイリーリワード履歴
	 */
	public void update(DailyRewardHistory dailyRewardHistory) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyRewardHistoryMapper mapper = session.getMapper(DailyRewardHistoryMapper.class);
			mapper.update(dailyRewardHistory);
			session.commit();
		}
	}

	/**
	 * 指定したデイリーリワード履歴を削除する
	 *
	 * @param userId ユーザID
	 * @param campaignId キャンペーンID
	 * @param targetDate 対象日
	 */
	public void delete(String userId, String targetDate) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyRewardHistoryMapper mapper = session.getMapper(DailyRewardHistoryMapper.class);
			mapper.delete(userId, targetDate);
			session.commit();
		}
	}

	/**
	 * デイリーリワード履歴をすべて削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyRewardHistoryMapper mapper = session.getMapper(DailyRewardHistoryMapper.class);
			mapper.deleteAll();
			session.commit();
		}
	}
}
