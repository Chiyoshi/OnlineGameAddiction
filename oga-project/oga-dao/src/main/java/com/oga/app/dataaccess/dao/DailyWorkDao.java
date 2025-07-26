package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.DailyWorkMapper;
import com.oga.app.dataaccess.entity.DailyWork;

/**
 * ������Ə��e�[�u���𑀍삷��N���X
 */
public class DailyWorkDao extends BaseDao {

	private static DailyWorkDao dailyWorkDao;

	/**
	 * �R���X�g���N�^
	 */
	private DailyWorkDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized DailyWorkDao getInstance() {
		if (dailyWorkDao == null) {
			dailyWorkDao = new DailyWorkDao();
		}
		return dailyWorkDao;
	}

	/**
	 * ������Ə����擾����
	 * 
	 * @return ������Ə�񃊃X�g
	 */
	public List<DailyWork> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ��L�[�Ŏw�肵��������Ə����擾����
	 * 
	 * @param userId ���[�UID
	 * @return ������Ə��
	 */
	public DailyWork findByPKey(String userId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			return mapper.findByPKey(userId);
		}
	}

	/**
	 * ������Ə����擾����
	 * 
	 * @return ������Ə�񃊃X�g
	 */
	public List<DailyWork> findAllWithUser() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			DailyWorkMapper mapper = session.getMapper(DailyWorkMapper.class);
			return mapper.findAllWithUser();
		}
	}
	
	

	/**
	 * ������Ə���o�^����
	 * 
	 * @param dailyWork ������Ə��Entity
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
	 * ������Ə����X�V����
	 * 
	 * @param dailyWork ������Ə��Entity
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
	 * �w�肵��������Ə����폜����
	 * 
	 * @param userId ���[�UID
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
	 * ������Ə������ׂč폜����
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
