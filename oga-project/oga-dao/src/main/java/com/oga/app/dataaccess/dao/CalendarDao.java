package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.common.exception.SystemException;
import com.oga.app.dataaccess.dao.mapper.CalendarMapper;
import com.oga.app.dataaccess.entity.Calendar;

/**
 * カレンダー情報テーブルを操作するクラス
 */
public class CalendarDao extends BaseDao {

	private static CalendarDao calendarDao;

	/**
	 * コンストラクタ
	 */
	private CalendarDao() {
	}

	/**
	 * インスタンス取得
	 */
	public static synchronized CalendarDao getInstance() {
		if (calendarDao == null) {
			calendarDao = new CalendarDao();
		}
		return calendarDao;
	}

	/**
	 * カレンダー情報を取得する
	 * 
	 * @return カレンダー情報リスト
	 * @throws SystemException
	 */
	public List<Calendar> findAll() {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CalendarMapper mapper = session.getMapper(CalendarMapper.class);
			return mapper.findAll();
		}
	}

	/**
	 * 主キーで指定したカレンダー情報を取得する
	 * 
	 * @param baseDate 基準日
	 * @return カレンダー情報
	 * @throws SystemException
	 */
	public Calendar findByPKey(String baseDate) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CalendarMapper mapper = session.getMapper(CalendarMapper.class);
			return mapper.findByPKey(baseDate);
		}
	}

	/**
	 * カレンダー情報を登録する
	 * 
	 * @param dailyWork カレンダー情報Entity
	 * @return
	 * @throws SystemException
	 */
	public void insert(Calendar calendar) {
		SqlSessionFactory sqlSessionFactory = super.getSqlSessionFactory();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			CalendarMapper mapper = session.getMapper(CalendarMapper.class);
			mapper.insertCalendar(calendar);
			session.commit();
		}
	}
}
