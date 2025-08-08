package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.OperationLogMapper;
import com.oga.app.dataaccess.entity.OperationLog;

/**
 * 操作ログテーブルを操作するクラス
 */
public class OperationLogDao extends DaoBase {

	/** 操作ログテーブルDAO */
	private static OperationLogDao operationLogDao;

	/**
	 * コンストラクタ
	 */
	private OperationLogDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized OperationLogDao getInstance() {
		if (operationLogDao == null) {
			operationLogDao = new OperationLogDao();
		}
		return operationLogDao;
	}

	/**
	 * 操作ログを取得する
	 * 
	 * @return 操作ログリスト
	 */
	public List<OperationLog> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			OperationLogMapper mapper = session.getMapper(OperationLogMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * 検索条件を指定して操作ログを取得する
	 * 
	 * @param fromOperationDate 操作日付From
	 * @param toOperationDate 操作日付To
	 * @param userId ユーザID
	 * @return
	 */
	public List<OperationLog> findByOperationDate(String fromOperationDate, String toOperationDate, String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			OperationLogMapper mapper = session.getMapper(OperationLogMapper.class);
			return mapper.findByOperationDate(fromOperationDate, toOperationDate, userId);
		}
	}

	/**
	 * 操作ログを取得する
	 * 
	 * @return 操作ログリスト
	 */
	public void insertOperationLog(OperationLog operationLog) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			OperationLogMapper mapper = session.getMapper(OperationLogMapper.class);
			mapper.insertOperationLog(operationLog);
			session.commit();
		}
	}
}
