package com.oga.app.dataaccess.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DaoBase {

	/** SqlSessionFactory */
	private static SqlSessionFactory sqlSessionFactory;

	static {
		if (sqlSessionFactory == null) {
			try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
				Properties orop = new Properties();
				orop.setProperty("db.url", System.getProperty("db.url"));
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, orop);
			} catch (IOException e) {
				throw new RuntimeException("SqlSessionFactoryの初期化に失敗しました", e);
			}
		}
	}

	/**
	 * コンストラクタ
	 */
	public DaoBase() {
	}

	/**
	 * SqlSessionFactoryを取得する
	 * 
	 * @return sqlSessionFactory
	 */
	public static synchronized SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

}
