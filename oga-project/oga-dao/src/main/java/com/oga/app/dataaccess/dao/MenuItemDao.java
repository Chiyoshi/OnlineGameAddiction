package com.oga.app.dataaccess.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.MenuItemMapper;
import com.oga.app.dataaccess.entity.MenuItem;

/**
 * ���j���[�A�C�e�����e�[�u���𑀍삷��N���X
 */
public class MenuItemDao extends BaseDao {

	/** ���j���[�A�C�e�����e�[�u��DAO */
	private static MenuItemDao menuItemDao;

	/**
	 * �R���X�g���N�^
	 */
	public MenuItemDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized MenuItemDao getInstance() {
		if (menuItemDao == null) {
			menuItemDao = new MenuItemDao();
		}
		return menuItemDao;
	}

	/**
	 * ���j���[�A�C�e����񃊃X�g���擾����
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<MenuItem> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MenuItemMapper mapper = session.getMapper(MenuItemMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ��L�[�Ŏw�肵�����j���[�A�C�e�������擾����
	 * 
	 * @param key
	 * @return
	 */
	public MenuItem findByPKey(String menuId, String menuItemId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MenuItemMapper mapper = session.getMapper(MenuItemMapper.class);
			return mapper.findByPKey(menuId, menuItemId);
		}
	}
}
