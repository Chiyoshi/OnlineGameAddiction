package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.LoginCampaignDetailsMapper;
import com.oga.app.dataaccess.entity.LoginCampaignDetails;

/**
 * ログインキャンペーン詳細テーブルを操作するクラス
 */
public class LoginCampaignDetailsDao extends DaoBase {

	/** ログインキャンペーン詳細DAO */
	private static LoginCampaignDetailsDao loginCampaignDetailsDao;

	/**
	 * コンストラクタ
	 */
	private LoginCampaignDetailsDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized LoginCampaignDetailsDao getInstance() {
		if (loginCampaignDetailsDao == null) {
			loginCampaignDetailsDao = new LoginCampaignDetailsDao();
		}
		return loginCampaignDetailsDao;
	}

	/**
	 * 主キーで指定したログインキャンペーン詳細を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetMonth 対象月
	 * @return
	 */
	public LoginCampaignDetails findByPKey(String userId, String targetMonth) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignDetailsMapper mapper = session.getMapper(LoginCampaignDetailsMapper.class);
			return mapper.findByPKey(userId, targetMonth);
		}
	}

	/**
	 * ログインキャンペーン詳細リストを取得する
	 * 
	 * @return ログインキャンペーン詳細リスト
	 */
	public List<LoginCampaignDetails> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignDetailsMapper mapper = session.getMapper(LoginCampaignDetailsMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * 主キーで指定したログインキャンペーン詳細の件数を取得する
	 * 
	 * @param userId ユーザID
	 * @param targetMonth 対象月
	 * @return
	 */
	public int countByPKey(String userId, String targetMonth) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignDetailsMapper mapper = session.getMapper(LoginCampaignDetailsMapper.class);
			return mapper.countByPKey(userId, targetMonth);
		}
	}

	/**
	 * ログインキャンペーン詳細を登録する
	 * 
	 * @param loginCampaignDetails ログインキャンペーン詳細
	 */
	public void insert(LoginCampaignDetails loginCampaignDetails) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignDetailsMapper mapper = session.getMapper(LoginCampaignDetailsMapper.class);
			mapper.insert(loginCampaignDetails);
			session.commit();
		}
	}

	/**
	 * ログインキャンペーン詳細を更新する
	 * 
	 * @param loginCampaignDetails ログインキャンペーン詳細
	 */
	public void update(LoginCampaignDetails loginCampaignDetails) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignDetailsMapper mapper = session.getMapper(LoginCampaignDetailsMapper.class);
			mapper.update(loginCampaignDetails);
			session.commit();
		}
	}

	/**
	 * 指定したログインキャンペーン詳細を削除する
	 *
	 * @param userId ユーザID
	 * @param targetMonth 対象月
	 */
	public void delete(String userId, String targetMonth) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignDetailsMapper mapper = session.getMapper(LoginCampaignDetailsMapper.class);
			mapper.delete(userId, targetMonth);
			session.commit();
		}
	}

	/**
	 * ログインキャンペーン詳細をすべて削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			LoginCampaignDetailsMapper mapper = session.getMapper(LoginCampaignDetailsMapper.class);
			mapper.deleteAll();
			session.commit();
		}
	}
}
