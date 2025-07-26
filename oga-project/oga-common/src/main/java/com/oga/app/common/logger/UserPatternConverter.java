package com.oga.app.common.logger;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

import com.oga.app.common.utils.StringUtil;

@Plugin(name = "UserPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "user" })
public class UserPatternConverter extends LogEventPatternConverter {

	protected UserPatternConverter(String name, String style) {
		super(name, style);
	}

	public static UserPatternConverter newInstance(String[] options) {
		return new UserPatternConverter("user", "user");
	}

	@Override
	public void format(LogEvent event, StringBuilder toAppendTo) {
		// ユーザー名を取得する
		String userId = System.getProperty("userid");

		// ログに追加する
		if (!StringUtil.isNullOrEmpty(userId)) {
			toAppendTo.append("[" + userId + "]");
		}
	}
}
