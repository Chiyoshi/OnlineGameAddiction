package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.RouletteHistoryMapper;
import com.oga.app.dataaccess.entity.RouletteHistory;

/**
 * ルーレット履歴テーブルを操作するクラス
 */
public class RouletteHistoryDao extends DaoBase {

	/** ルーレット履歴DAO */
	private static RouletteHistoryDao rouletteHistoryDao;

	/**
	 * コンストラクタ
	 */
	private RouletteHistoryDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized RouletteHistoryDao getInstance() {
		if (rouletteHistoryDao == null) {
			rouletteHistoryDao = new RouletteHistoryDao();
		}
		return rouletteHistoryDao;
	}

	/**
	 * 主キーで指定したルーレット履歴を取得する
	 * 
	 * @param userId ユーザID
	 * @param campaignId キャンペーンID
	 * @param targetDate 対象日
	 * @return ルーレット履歴
	 */
	public RouletteHistory findByPKey(String userId, String campaignId, String targetDate) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RouletteHistoryMapper mapper = session.getMapper(RouletteHistoryMapper.class);
			return mapper.findByPKey(userId, campaignId, targetDate);
		}
	}

	/**
	 * ルーレット履歴リストを取得する
	 * 
	 * @return ルーレット履歴リスト
	 */
	public List<RouletteHistory> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RouletteHistoryMapper mapper = session.getMapper(RouletteHistoryMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * キャンペーンIDで指定したルーレット履歴を取得する
	 * 
	 * @param userId ユーザID
	 * @param campaignId キャンペーンID
	 * @return ルーレット履歴リスト
	 */
	public List<RouletteHistory> findByCampaignId(String userId, String campaignId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RouletteHistoryMapper mapper = session.getMapper(RouletteHistoryMapper.class);
			return mapper.findByCampaignId(userId, campaignId);
		}
	}

	/**
	 * ルーレット履歴を登録する
	 * 
	 * @param rouletteHistory ルーレット履歴
	 */
	public void insert(RouletteHistory rouletteHistory) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RouletteHistoryMapper mapper = session.getMapper(RouletteHistoryMapper.class);
			mapper.insert(rouletteHistory);
			session.commit();
		}
	}

	/**
	 * ルーレット履歴を更新する
	 * 
	 * @param rouletteHistory ルーレット履歴
	 */
	public void update(RouletteHistory rouletteHistory) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RouletteHistoryMapper mapper = session.getMapper(RouletteHistoryMapper.class);
			mapper.update(rouletteHistory);
			session.commit();
		}
	}

	/**
	 * 指定したルーレット履歴を削除する
	 *
	 * @param userId ユーザID
	 * @param campaignId キャンペーンID
	 * @param targetDate 対象日
	 */
	public void delete(String userId, String campaignId, String targetDate) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RouletteHistoryMapper mapper = session.getMapper(RouletteHistoryMapper.class);
			mapper.delete(userId, campaignId, targetDate);
			session.commit();
		}
	}

	/**
	 * ルーレット履歴をすべて削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			RouletteHistoryMapper mapper = session.getMapper(RouletteHistoryMapper.class);
			mapper.deleteAll();
			session.commit();
		}
	}
}
