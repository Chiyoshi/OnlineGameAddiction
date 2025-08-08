package com.oga.app.service.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * ログインキャンペーン情報
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class LoginCampaignServiceBean extends ServiceBeanBase {

	/** ユーザID */
	private String userId;

}
