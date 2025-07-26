package com.oga.app.dataaccess.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.common.exception.SystemException;
import com.oga.app.dataaccess.dao.mapper.SettingMapper;
import com.oga.app.dataaccess.entity.Setting;

/**
 * 設定情報テーブルを操作するクラス
 */
public class SettingDao extends BaseDao {

	/** 設定情報テーブルDAO */
	private static SettingDao settingDao;

	/**
	 * コンストラクタ
	 */
	public SettingDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized SettingDao getInstance() {
		if (settingDao == null) {
			settingDao = new SettingDao();
		}
		return settingDao;
	}

	/**
	 * 設定情報リストを取得する
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
	 * 主キーで指定した設定情報を取得する
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
