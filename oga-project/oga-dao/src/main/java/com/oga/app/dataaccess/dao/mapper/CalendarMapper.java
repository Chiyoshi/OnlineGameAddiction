package com.oga.app.dataaccess.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import com.oga.app.dataaccess.dao.sqlprovider.CalendarSqlProvider;
import com.oga.app.dataaccess.entity.Calendar;

@Mapper
public interface CalendarMapper {

	@SelectProvider(type = CalendarSqlProvider.class, method = "findByPKey")
	Calendar findByPKey(String baseDate);

	@SelectProvider(type = CalendarSqlProvider.class, method = "findAll")
	List<Calendar> findAll();

	@InsertProvider(type = CalendarSqlProvider.class, method = "insertCalendar")
	void insertCalendar(Calendar calendar);
}
