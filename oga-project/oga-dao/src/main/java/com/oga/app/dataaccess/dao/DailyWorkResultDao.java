package com.oga.app.dataaccess.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.DailyWorkResultMapper;
import com.oga.app.dataaccess.entity.DailyWorkResult;

/**
 * 日次作業結果テーブルを操作するクラス
 */
public class DailyWorkResultDao extends BaseDao {

	private static DailyWorkResultDao dailyWorkResultDao;

	/**
	 * コンストラクタ
	 */
	private DailyWorkResultDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized DailyWorkResultDao getInstance() {
		if (dailyWorkResultDao == null) {
			dailyWorkResultDao = new DailyWorkResultDao();
		}
		return dailyWorkResultDao;
	}

	/**
	 * 日次作業結果を取得する
	 * 
	 * @return 日次作業結果リスト
	 */
	public List<DailyWorkResult> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * 主キーで指定した日次作業結果を取得する
	 * 
	 * @param userId ユーザID
	 * @param baseDate 基準日
	 * @param serviceType サービス種別
	 * @return 日次作業結果
	 */
	public DailyWorkResult findByPKey(String userId, String baseDate, String serviceType) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			return mapper.findByPKey(userId, baseDate, serviceType);
		}
	}

	/**
	 * 対象期間を指定した日次作業結果を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetDateFrom 基準日From
	 * @param targetDateTo 基準日To
	 * @param serviceType サービス種別
	 * @return 日次作業結果
	 */
	public List<DailyWorkResult> findByTargetDate(String userId, String targetDateFrom, String targetDateTo,
			String serviceType) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			return mapper.findByTargetDate(userId, targetDateFrom, targetDateTo, serviceType);
		}
	}

	/**
	 * 対象期間を指定した日次作業結果の件数を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetDateFrom 基準日From
	 * @param targetDateTo 基準日To
	 * @param serviceType サービス種別
	 * @param status ステータス
	 * @return 日次作業結果
	 */
	public int countByTargetDate(String userId, String targetDateFrom, String targetDateTo, String serviceType,
			String status) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			return mapper.countByTargetDate(userId, targetDateFrom, targetDateTo, serviceType, status);
		}
	}

	/**
	 * 対象日付の日次作業結果のエラー件数を取得する
	 * 
	 * @param baseDate 基準日
	 * @param status ステータス
	 * @return 件数
	 */
	public int countByErrorStatus(String baseDate, String status) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			return mapper.countByErrorStatus(baseDate, status);
		}
	}

	/**
	 * 対象キャンペーンの獲得アイテムの集計結果を取得する
	 * 
	 * @param targetDateFrom 基準日From
	 * @param targetDateTo 基準日To
	 * @param serviceType サービス種別
	 * @param status ステータス
	 * @param targetRewardItemList 集計対象の獲得アイテム
	 * @return 集計結果リスト
	 */
	public List<LinkedHashMap<String, Object>> findByRewardItemAggregate(
			String targetDateFrom, String targetDateTo, String serviceType,
			String status, List<String> targetRewardItemList) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			return mapper.findByRewardItemAggregate(targetDateFrom, targetDateTo, serviceType, status,
					targetRewardItemList);
		}
	}

	/**
	 * 日次作業結果を登録する
	 * 
	 * @param dailyWork 日次作業結果Entity
	 */
	public void insert(DailyWorkResult dailyWorkResult) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			mapper.insertDailyWorkResult(dailyWorkResult);
			session.commit();
		}
	}

	/**
	 * 日次作業結果を更新する
	 * 
	 * @param dailyWork 日次作業結果Entity
	 */
	public void update(DailyWorkResult dailyWorkResult) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			mapper.updateDailyWorkResult(dailyWorkResult);
			session.commit();
		}
	}

	/**
	 * 指定した日次作業結果を削除する
	 * 
	 * @param userId ユーザID
	 * @param baseDate 基準日
	 * @param serviceType サービス種別
	 */
	public void delete(String userId, String baseDate, String serviceType) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			mapper.deleteDailyWorkResult(userId, baseDate, serviceType);
			session.commit();
		}
	}

	/**
	 * 日次作業結果を削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			mapper.deleteAllDailyWorkResult();
			session.commit();
		}
	}
}
