package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.CampaignMapper;
import com.oga.app.dataaccess.entity.Campaign;

/**
 * キャンペーン情報テーブルを操作するクラス
 */
public class CampaignDao extends BaseDao {

	private static CampaignDao campaignDao;

	/**
	 * コンストラクタ
	 */
	private CampaignDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized CampaignDao getInstance() {
		if (campaignDao == null) {
			campaignDao = new CampaignDao();
		}
		return campaignDao;
	}

	/**
	 * キャンペーン情報を取得する
	 * 
	 * @return キャンペーン情報リスト
	 */
	public List<Campaign> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CampaignMapper mapper = session.getMapper(CampaignMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * 主キーで指定したキャンペーン情報を取得する
	 * 
	 * @param campaignId キャンペーンID
	 * @return キャンペーン情報
	 */
	public Campaign findByPKey(String campaignId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CampaignMapper mapper = session.getMapper(CampaignMapper.class);
			return mapper.findByPKey(campaignId);
		}
	}

	/**
	 * キャンペーン種別、対象日を指定してキャンペーン情報を取得する
	 * 
	 * @param campaignType キャンペーン種別
	 * @param targetDate 対象日
	 * @return キャンペーン情報
	 */
	public List<Campaign> findByCampaignDate(String campaignType, String targetDate) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CampaignMapper mapper = session.getMapper(CampaignMapper.class);
			return mapper.findByCampaignDate(campaignType, targetDate);
		}
	}

	/**
	 * キャンペーン情報を登録する
	 * 
	 * @param campaign キャンペーン情報Entity
	 */
	public void insert(Campaign campaign) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CampaignMapper mapper = session.getMapper(CampaignMapper.class);
			mapper.insertCampaign(campaign);
			session.commit();
		}
	}

	/**
	 * 指定したキャンペーン情報を削除する
	 * 
	 * @param campaignId キャンペーンID
	 */
	public void delete(String campaignId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CampaignMapper mapper = session.getMapper(CampaignMapper.class);
			mapper.deleteCampaign(campaignId);
			session.commit();
		}
	}

	/**
	 * キャンペーン情報を削除する
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CampaignMapper mapper = session.getMapper(CampaignMapper.class);
			mapper.deleteAllCampaign();
			session.commit();
		}
	}
}
