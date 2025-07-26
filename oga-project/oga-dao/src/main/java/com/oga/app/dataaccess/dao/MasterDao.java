package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.MasterMapper;
import com.oga.app.dataaccess.entity.Master;

/**
 * �}�X�^���e�[�u���𑀍삷��N���X
 */
public class MasterDao extends BaseDao {

	/** �}�X�^���e�[�u��DAO */
	private static MasterDao masterDao;

	/**
	 * �R���X�g���N�^
	 */
	public MasterDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized MasterDao getInstance() {
		if (masterDao == null) {
			masterDao = new MasterDao();
		}
		return masterDao;
	}

	/**
	 * �}�X�^��񃊃X�g���擾����
	 * 
	 * @return
	 */
	public List<Master> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ��L�[�Ŏw�肵���}�X�^�����擾����
	 * 
	 * @param key �L�[
	 * @return �}�X�^���
	 */
	public Master findByPKey(String key) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			return mapper.findByPKey(key);
		}
	}

	/**
	 * �}�X�^����o�^����
	 * 
	 * @param master �}�X�^���
	 */
	public void insert(Master master) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			mapper.insertMaster(master);
			session.commit();
		}
	}

	/**
	 * �w�肵���}�X�^�����폜����
	 * 
	 * @param key �L�[
	 */
	public void delete(String key) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			mapper.deleteMaster(key);
			session.commit();
		}
	}

	/**
	 * �}�X�^�����폜����
	 */
	public void delete() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MasterMapper mapper = session.getMapper(MasterMapper.class);
			mapper.deleteAllMaster();
			session.commit();
		}
	}
}
