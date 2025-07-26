package com.oga.app.dataaccess.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.oga.app.common.exception.SystemException;
import com.oga.app.dataaccess.dao.mapper.CalendarMapper;
import com.oga.app.dataaccess.entity.Calendar;

/**
 * �J�����_�[���e�[�u���𑀍삷��N���X
 */
public class CalendarDao extends BaseDao {

	private static CalendarDao calendarDao;

	/**
	 * �R���X�g���N�^
	 */
	private CalendarDao() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized CalendarDao getInstance() {
		if (calendarDao == null) {
			calendarDao = new CalendarDao();
		}
		return calendarDao;
	}

	/**
	 * �J�����_�[�����擾����
	 * 
	 * @return �J�����_�[��񃊃X�g
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
	 * ��L�[�Ŏw�肵���J�����_�[�����擾����
	 * 
	 * @param baseDate ���
	 * @return �J�����_�[���
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
	 * �J�����_�[����o�^����
	 * 
	 * @param dailyWork �J�����_�[���Entity
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
