package com.oga.app.common.enums;

public enum LoginCampaignType {

	/** ログインキャンペーン(通常) */
	NORMAL("通常マス", "0"),
	/** ログインキャンペーン(コンプリート1) */
	CMPLETE_CHAPTER1("コンプリートマス(チャプタ1)", "1"),
	/** ログインキャンペーン(コンプリート2) */
	CMPLETE_CHAPTER2("コンプリートマス(チャプタ2)", "2"),
	/** ログインキャンペーン(コンプリート3) */
	CMPLETE_CHAPTER3("コンプリートマス(チャプタ3)", "3");

	private final String name;

	private final String value;

	private LoginCampaignType(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	/**
	 * 論理名を取得する
	 * 
	 * @param code コード 
	 * @return
	 */
	public static String getName(String value) {
		for (LoginCampaignType loginCampaignType : LoginCampaignType.values()) {
			if (value == loginCampaignType.getValue()) {
				return loginCampaignType.getName();
			}
		}
		return "";
	}
}
