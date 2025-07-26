package com.oga.app.dataaccess.dao.sqlprovider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.oga.app.common.utils.StringUtil;
import com.oga.app.dataaccess.entity.OperationLog;

public class OperationLogSqlProvider {

	public String findAll() {
		return new SQL() {
			{
				SELECT("*");
				FROM("OperationLog");
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String findByOperationDate(@Param("fromOperationDate") final String fromOperationDate,
			@Param("toOperationDate") final String toOperationDate, @Param("userId") final String userId) {
		return new SQL() {
			{
				SELECT("*");
				FROM("OperationLog");
				if (!StringUtil.isNullOrEmpty(fromOperationDate)) {
					WHERE("baseDate >= #{fromOperationDate}");
				}

				if (!StringUtil.isNullOrEmpty(toOperationDate)) {
					WHERE("baseDate <= #{toOperationDate}");
				}

				if (!StringUtil.isNullOrEmpty(userId)) {
					WHERE("userId = #{userId}");
				}
				WHERE("deleteFlg = 'N'");
			}
		}.toString();
	}

	public String insertOperationLog(final OperationLog operationLog) {
		return new SQL() {
			{
				INSERT_INTO("OperationLog");
				VALUES("operationCode", "#{operationCode}");
				VALUES("operationDate", "datetime('now', '+9 hours')");
				VALUES("level", "#{level}");
				VALUES("message", "#{message}");
				VALUES("userId", "#{userId}");
				VALUES("registrationDate", "datetime('now', '+9 hours')");
				VALUES("updateDate", "datetime('now', '+9 hours')");
				VALUES("deleteFlg", "'N'");
			}
		}.toString();
	}
}
