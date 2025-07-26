package com.oga.app.dataaccess.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.DailyWorkResultMapper;
import com.oga.app.dataaccess.entity.DailyWorkResult;

/**
 * ������ƌ��ʃe�[�u���𑀍삷��N���X
 */
public class DailyWorkResultDao extends BaseDao {

	private static DailyWorkResultDao dailyWorkResultDao;

	/**
	 * �R���X�g���N�^
	 */
	private DailyWorkResultDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized DailyWorkResultDao getInstance() {
		if (dailyWorkResultDao == null) {
			dailyWorkResultDao = new DailyWorkResultDao();
		}
		return dailyWorkResultDao;
	}

	/**
	 * ������ƌ��ʂ��擾����
	 * 
	 * @return ������ƌ��ʃ��X�g
	 */
	public List<DailyWorkResult> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ��L�[�Ŏw�肵��������ƌ��ʂ��擾����
	 * 
	 * @param userId ���[�UID
	 * @param baseDate ���
	 * @param serviceType �T�[�r�X���
	 * @return ������ƌ���
	 */
	public DailyWorkResult findByPKey(String userId, String baseDate, String serviceType) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			return mapper.findByPKey(userId, baseDate, serviceType);
		}
	}

	/**
	 * �Ώۊ��Ԃ��w�肵��������ƌ��ʂ��擾����
	 * 
	 * @param userId ���[�UID
	 * @param targetDateFrom ���From
	 * @param targetDateTo ���To
	 * @param serviceType �T�[�r�X���
	 * @return ������ƌ���
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
	 * �Ώۊ��Ԃ��w�肵��������ƌ��ʂ̌������擾����
	 * 
	 * @param userId ���[�UID
	 * @param targetDateFrom ���From
	 * @param targetDateTo ���To
	 * @param serviceType �T�[�r�X���
	 * @param status �X�e�[�^�X
	 * @return ������ƌ���
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
	 * �Ώۓ��t�̓�����ƌ��ʂ̃G���[�������擾����
	 * 
	 * @param baseDate ���
	 * @param status �X�e�[�^�X
	 * @return ����
	 */
	public int countByErrorStatus(String baseDate, String status) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkResultMapper mapper = session.getMapper(DailyWorkResultMapper.class);
			return mapper.countByErrorStatus(baseDate, status);
		}
	}

	/**
	 * �ΏۃL�����y�[���̊l���A�C�e���̏W�v���ʂ��擾����
	 * 
	 * @param targetDateFrom ���From
	 * @param targetDateTo ���To
	 * @param serviceType �T�[�r�X���
	 * @param status �X�e�[�^�X
	 * @param targetRewardItemList �W�v�Ώۂ̊l���A�C�e��
	 * @return �W�v���ʃ��X�g
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
	 * ������ƌ��ʂ�o�^����
	 * 
	 * @param dailyWork ������ƌ���Entity
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
	 * ������ƌ��ʂ��X�V����
	 * 
	 * @param dailyWork ������ƌ���Entity
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
	 * �w�肵��������ƌ��ʂ��폜����
	 * 
	 * @param userId ���[�UID
	 * @param baseDate ���
	 * @param serviceType �T�[�r�X���
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
	 * ������ƌ��ʂ��폜����
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
