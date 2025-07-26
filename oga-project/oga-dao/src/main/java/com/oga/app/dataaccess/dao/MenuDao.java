package com.oga.app.dataaccess.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.dataaccess.dao.mapper.MenuMapper;
import com.oga.app.dataaccess.entity.Menu;

/**
 * メニュー情報テーブルを操作するクラス
 */
public class MenuDao extends BaseDao {

	/** メニュー情報テーブルDAO */
	private static MenuDao menuDao;

	/**
	 * コンストラクタ
	 */
	public MenuDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized MenuDao getInstance() {
		if (menuDao == null) {
			menuDao = new MenuDao();
		}
		return menuDao;
	}

	/**
	 * メニュー情報リストを取得する
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
	 * 主キーで指定したメニュー情報を取得する
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
