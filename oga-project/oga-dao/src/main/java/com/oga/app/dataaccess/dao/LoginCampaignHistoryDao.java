package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.LoginCampaignHistoryMapper;
import com.oga.app.dataaccess.entity.LoginCampaignHistory;

/**
 * ログインキャンペーン履歴テーブルを操作するクラス
 */
public class LoginCampaignHistoryDao extends DaoBase {

	/** ログインキャンペーン履歴DAO */
	private static LoginCampaignHistoryDao loginCampaignHistoryDao;

	/**
	 * コンストラクタ
	 */
	private LoginCampaignHistoryDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized LoginCampaignHistoryDao getInstance() {
		if (loginCampaignHistoryDao == null) {
			loginCampaignHistoryDao = new LoginCampaignHistoryDao();
		}
		return loginCampaignHistoryDao;
	}

	/**
	 * 主キーで指定したログインキャンペーン履歴を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetMonth 対象月
	 * @param targetDate 対象日
	 * @param loginCampaignType ログインキャンペーン種別
	 * @return
	 */
	public LoginCampaignHistory findByPKey(String userId, String targetMonth, String targetDate, String loginCampaignType) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignHistoryMapper mapper = session.getMapper(LoginCampaignHistoryMapper.class);
			return mapper.findByPKey(userId, targetMonth, targetDate, loginCampaignType);
		}
	}

	/**
	 * ログインキャンペーン履歴リストを取得する
	 * 
	 * @return ログインキャンペーン履歴リスト
	 */
	public List<LoginCampaignHistory> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignHistoryMapper mapper = session.getMapper(LoginCampaignHistoryMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ログインキャンペーン履歴を登録する
	 * 
	 * @param loginCampaignHistory ログインキャンペーン履歴
	 */
	public void insert(LoginCampaignHistory loginCampaignHistory) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignHistoryMapper mapper = session.getMapper(LoginCampaignHistoryMapper.class);
			mapper.insert(loginCampaignHistory);
			session.commit();
		}
	}

	/**
	 * ログインキャンペーン履歴を更新する
	 * 
	 * @param loginCampaignHistory ログインキャンペーン履歴
	 */
	public void update(LoginCampaignHistory loginCampaignHistory) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignHistoryMapper mapper = session.getMapper(LoginCampaignHistoryMapper.class);
			mapper.update(loginCampaignHistory);
			session.commit();
		}
	}

	/**
	 * 指定したログインキャンペーン履歴を削除する
	 *
	 * @param userId ユーザID
	 * @param targetMonth 対象月
	 * @param targetDate 対象日
	 * @param loginCampaignType ログインキャンペーン種別
	 */
	public void delete(String userId, String targetMonth, String targetDate, String loginCampaignType) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignHistoryMapper mapper = session.getMapper(LoginCampaignHistoryMapper.class);
			mapper.delete(userId, targetMonth, targetDate, loginCampaignType);
			session.commit();
		}
	}

	/**
	 * ログインキャンペーン履歴をすべて削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignHistoryMapper mapper = session.getMapper(LoginCampaignHistoryMapper.class);
			mapper.deleteAll();
			session.commit();
		}
	}
}
