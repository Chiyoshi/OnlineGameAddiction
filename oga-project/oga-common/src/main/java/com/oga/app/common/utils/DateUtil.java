package com.oga.app.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {

	/** 日付書式 yyyy/MM/dd HH:mm:ss */
	public static final String DATE_FORMAT_YYYYMMDDHH24MISS = "yyyy/MM/dd HH:mm:ss";

	/** 日付書式 yyyy/MM/dd */
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

	/** 日付書式 yyyy-MM-dd */
	public static final String DATE_FORMAT_YYYYMMDD_2 = "yyyy-MM-dd";

	/** 日付書式 yyyyMMdd */
	public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

	/**
	 * 基準日を取得する
	 * 
	 * @return 基準日YYYYMMDD
	 */
	public static String getBaseDate() {
		return getBaseDate(DATE_FORMAT_YYYYMMDD);
	}

	/**
	 * 書式を指定して基準日を取得する
	 * 
	 * @param pattern 書式
	 * @return 基準日(yyyyMMdd形式)
	 */
	public static String getBaseDate(String pattern) {
		return formatDate(getCurrentDate(), pattern);
	}

	/**
	 * 基準月を取得する
	 * 
	 * @return 基準月(yyyyMM形式)
	 */
	public static String getBaseMonth() {
		return formatDate(getCurrentDate(), DATE_FORMAT_YYYYMM);
	}

	/**
	 * 日付を指定した書式で文字列に変換
	 * 
	 * @param date    日付
	 * @param pattern 日付書式
	 * @return
	 */
	public static String formatDate(LocalDate date, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return date.format(formatter);
	}

	/**
	 * 日付文字列を指定した書式でLocalDateに変換
	 * 
	 * @param dateStr 文字列日付YYYYMMDD
	 * @param pattern 日付書式
	 * @return
	 */
	public static String parseDateStr(String dateStr, String pattern) {
		return formatDate(parseDate(dateStr, DATE_FORMAT_YYYYMMDD), pattern);
	}

	/**
	 * 日付文字列を指定した書式でLocalDateに変換
	 * 
	 * @param dateStr 文字列日付YYYYMMDD
	 * @param pattern 日付書式
	 * @return
	 */
	public static LocalDate parseDate(String dateStr, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(dateStr, formatter);
	}

	/**
	 * 現在の日付を取得
	 * 
	 * @return
	 */
	public static LocalDate getCurrentDate() {
		return LocalDate.now();
	}

	/**
	 * 現在の日時を取得
	 * 
	 * @return
	 */
	public static LocalDateTime getCurrentDateTime() {
		return LocalDateTime.now();
	}

	/**
	 * 指定した対象日付の月初を取得する
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate getFirstDayOfMonth(LocalDate date) {
		return date.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 指定した対象日付の月末を取得する
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate getLastDayOfMonth(LocalDate date) {
		return date.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * 日付に日数を加算
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static LocalDate addDays(LocalDate date, int days) {
		return date.plusDays(days);
	}

	/**
	 * 日付から日数を減算
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static LocalDate subtractDays(LocalDate date, int days) {
		return date.minusDays(days);
	}

	/**
	 * 2つの日付の間の日数を計算
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long daysBetween(LocalDate startDate, LocalDate endDate) {
		return ChronoUnit.DAYS.between(startDate, endDate);
	}
}
