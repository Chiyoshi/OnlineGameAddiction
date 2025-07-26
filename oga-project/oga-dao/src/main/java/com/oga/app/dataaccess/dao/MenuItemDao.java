package com.oga.app.dataaccess.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.MenuItemMapper;
import com.oga.app.dataaccess.entity.MenuItem;

/**
 * メニューアイテム情報テーブルを操作するクラス
 */
public class MenuItemDao extends BaseDao {

	/** メニューアイテム情報テーブルDAO */
	private static MenuItemDao menuItemDao;

	/**
	 * コンストラクタ
	 */
	public MenuItemDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized MenuItemDao getInstance() {
		if (menuItemDao == null) {
			menuItemDao = new MenuItemDao();
		}
		return menuItemDao;
	}

	/**
	 * メニューアイテム情報リストを取得する
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
	 * 主キーで指定したメニューアイテム情報を取得する
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
