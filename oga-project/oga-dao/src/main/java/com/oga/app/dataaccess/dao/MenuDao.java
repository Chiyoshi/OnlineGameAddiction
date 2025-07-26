package com.oga.app.dataaccess.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.MenuMapper;
import com.oga.app.dataaccess.entity.Menu;

/**
 * ���j���[���e�[�u���𑀍삷��N���X
 */
public class MenuDao extends BaseDao {

	/** ���j���[���e�[�u��DAO */
	private static MenuDao menuDao;

	/**
	 * �R���X�g���N�^
	 */
	public MenuDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized MenuDao getInstance() {
		if (menuDao == null) {
			menuDao = new MenuDao();
		}
		return menuDao;
	}

	/**
	 * ���j���[��񃊃X�g���擾����
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Menu> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MenuMapper mapper = session.getMapper(MenuMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * ��L�[�Ŏw�肵�����j���[�����擾����
	 * 
	 * @param key
	 * @return
	 */
	public Menu findByPKey(String menuId) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			MenuMapper mapper = session.getMapper(MenuMapper.class);
			return mapper.findByPKey(menuId);
		}
	}
}
