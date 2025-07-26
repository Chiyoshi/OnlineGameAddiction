package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.jdbc.SQL;

import com.oga.app.dataaccess.entity.Calendar;

public class CalendarSqlProvider {

	public String findByPKey(final String baseDate) {
		return new SQL() {
			{
				SELECT("*");
				FROM("calendar");
				WHERE("baseDate = #{baseDate}");
			}
		}.toString();
	}

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("calendar");
			}
		}.toString();
	}

	public String insertCalendar(final Calendar calendar) {
		return new SQL() {
			{
				INSERT_INTO("calendar");
				VALUES("baseDate", "#{baseDate}");
				VALUES("dayOfWeek", "#{dayOfWeek}");
				VALUES("maintenanceFlg", "#{maintenanceFlg}");
				VALUES("mtStartTime", "#{mtStartTime}");
				VALUES("mtEndTime", "#{mtEndTime}");
			}
		}.toString();
	}
}
