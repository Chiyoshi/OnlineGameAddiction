package com.oga.app.service.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * ログイン情報
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class LoginServiceBean extends ServiceBeanBase {

	/** ユーザID */
	private String userId;

	/** パスワード */
	private String password;

}
