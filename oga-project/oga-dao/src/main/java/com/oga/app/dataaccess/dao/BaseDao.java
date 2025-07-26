package com.oga.app.dataaccess.dao;

import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BaseDao {

	/** SqlSessionFactory */
	private static SqlSessionFactory sqlSessionFactory;

	static {
		if (sqlSessionFactory == null) {
			try {
				String resource = "mybatis-config.xml";
				InputStream inputStream = Resources.getResourceAsStream(resource);

				Properties properties = new Properties();
				properties.setProperty("db.url", System.getProperty("db.url"));
				// properties.setProperty("db.username", System.getProperty("db.username"));
				// properties.setProperty("db.password", System.getProperty("db.password"));

				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
			} catch (Exception e) {
				throw new RuntimeException("Error initializing MyBatis class. Cause: " + e);
			}
		}
	}

	/**
	 * �R���X�g���N�^
	 */
	public BaseDao() {
	}

	/**
	 * SqlSessionFactory���擾����
	 * 
	 * @return sqlSessionFactory
	 */
	public static synchronized SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

}
