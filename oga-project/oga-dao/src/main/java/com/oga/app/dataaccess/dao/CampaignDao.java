package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.CampaignMapper;
import com.oga.app.dataaccess.entity.Campaign;

/**
 * �L�����y�[�����e�[�u���𑀍삷��N���X
 */
public class CampaignDao extends BaseDao {

	private static CampaignDao campaignDao;

	/**
	 * �R���X�g���N�^
	 */
	private CampaignDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized CampaignDao getInstance() {
		if (campaignDao == null) {
			campaignDao = new CampaignDao();
		}
		return campaignDao;
	}

	/**
	 * �L�����y�[�������擾����
	 * 
	 * @return �L�����y�[����񃊃X�g
	 */
	public List<Campaign> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CampaignMapper mapper = session.getMapper(CampaignMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ��L�[�Ŏw�肵���L�����y�[�������擾����
	 * 
	 * @param campaignId �L�����y�[��ID
	 * @return �L�����y�[�����
	 */
	public Campaign findByPKey(String campaignId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CampaignMapper mapper = session.getMapper(CampaignMapper.class);
			return mapper.findByPKey(campaignId);
		}
	}

	/**
	 * �L�����y�[����ʁA�Ώۓ����w�肵�ăL�����y�[�������擾����
	 * 
	 * @param campaignType �L�����y�[�����
	 * @param targetDate �Ώۓ�
	 * @return �L�����y�[�����
	 */
	public List<Campaign> findByCampaignDate(String campaignType, String targetDate) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CampaignMapper mapper = session.getMapper(CampaignMapper.class);
			return mapper.findByCampaignDate(campaignType, targetDate);
		}
	}

	/**
	 * �L�����y�[������o�^����
	 * 
	 * @param campaign �L�����y�[�����Entity
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
	 * �w�肵���L�����y�[�������폜����
	 * 
	 * @param campaignId �L�����y�[��ID
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
	 * �L�����y�[�������폜����
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
