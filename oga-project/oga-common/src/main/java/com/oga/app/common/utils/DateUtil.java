package com.oga.app.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

	/** ���t���� yyyy/MM/dd HH:mm:ss */
	public static final String DATE_FORMAT_YYYYMMDDHH24MISS = "yyyy/MM/dd HH:mm:ss";

	/** ���t���� yyyy/MM/dd */
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

	/** ���t���� yyyyMMdd */
	public static final String DATE_FORMAT_YYYYMMDD_2 = "yyyy-MM-dd";

	/** ���t���� yyyyMMdd */
	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

	/**
	 * ������擾����
	 * 
	 * @return ���YYYYMMDD
	 */
	public static String getBaseDate() {
		return getBaseDate(DATE_FORMAT_YYYYMMDD_2);
	}

	/**
	 * �������w�肵�Ċ�����擾����
	 * 
	 * @param pattern ����
	 * @return ���
	 */
	public static String getBaseDate(String pattern) {
		return formatDate(getCurrentDate(), pattern);
	}

	/**
	 * ���t���w�肵�������ŕ�����ɕϊ�
	 * 
	 * @param date    ���t
	 * @param pattern ���t����
	 * @return
	 */
	public static String formatDate(LocalDate date, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}

	/**
	 * ���t��������w�肵��������LocalDate�ɕϊ�
	 * 
	 * @param dateStr ��������tYYYYMMDD
	 * @param pattern ���t����
	 * @return
	 */
	public static String parseDateStr(String dateStr, String pattern) {
		return formatDate(parseDate(dateStr, DATE_FORMAT_YYYYMMDD), pattern);
	}

	/**
	 * ���t��������w�肵��������LocalDate�ɕϊ�
	 * 
	 * @param dateStr ��������tYYYYMMDD
	 * @param pattern ���t����
	 * @return
	 */
	public static LocalDate parseDate(String dateStr, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateStr, formatter);
	}

	/**
	 * ���݂̓��t���擾
	 * 
	 * @return
	 */
	public static LocalDate getCurrentDate() {
		return LocalDate.now();
	}

	/**
	 * ���݂̓������擾
	 * 
	 * @return
	 */
	public static LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now();
	}

	/**
	 * �w�肵���Ώۓ��t�̌������擾����
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate getFirstDayOfMonth(LocalDate date) {
		return date.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * �w�肵���Ώۓ��t�̌������擾����
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate getLastDayOfMonth(LocalDate date) {
		return date.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * ���t�ɓ��������Z
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static LocalDate addDays(LocalDate date, int days) {
		return date.plusDays(days);
	}

	/**
	 * ���t������������Z
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static LocalDate subtractDays(LocalDate date, int days) {
		return date.minusDays(days);
	}

	/**
	 * 2�̓��t�̊Ԃ̓������v�Z
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long daysBetween(LocalDate startDate, LocalDate endDate) {
		return ChronoUnit.DAYS.between(startDate, endDate);
	}
}
