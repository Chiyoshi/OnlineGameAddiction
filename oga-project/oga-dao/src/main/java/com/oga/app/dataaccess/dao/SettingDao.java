package com.oga.app.dataaccess.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.common.exception.SystemException;
import com.oga.app.dataaccess.dao.mapper.SettingMapper;
import com.oga.app.dataaccess.entity.Setting;

/**
 * �ݒ���e�[�u���𑀍삷��N���X
 */
public class SettingDao extends BaseDao {

	/** �ݒ���e�[�u��DAO */
	private static SettingDao settingDao;

	/**
	 * �R���X�g���N�^
	 */
	public SettingDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized SettingDao getInstance() {
		if (settingDao == null) {
			settingDao = new SettingDao();
		}
		return settingDao;
	}

	/**
	 * �ݒ��񃊃X�g���擾����
	 * 
	 * @return
	 * @throws SQLException
	 * @throws SystemException
	 */
	public List<Setting> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			SettingMapper mapper = session.getMapper(SettingMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ��L�[�Ŏw�肵���ݒ�����擾����
	 * 
	 * @param key
	 * @return
	 * @throws SystemException
	 */
	public Setting findByPKey(String key) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			SettingMapper mapper = session.getMapper(SettingMapper.class);
			return mapper.findByPKey(key);
		}
	}
}
