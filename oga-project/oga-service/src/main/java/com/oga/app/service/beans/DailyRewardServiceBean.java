package com.oga.app.service.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * デイリーリワード情報
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class DailyRewardServiceBean extends ServiceBeanBase {

	/** ユーザID */
	private String userId;

}
