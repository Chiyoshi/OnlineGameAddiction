package com.oga.app.common.enums;

public enum ServiceType {

	/** 通常ログイン */
	NORMAL_LOGIN("通常ログイン", "0"),
	/** ログインキャンペーン(通常) */
	LOGINCAMPAIGN("ログインキャンペーン(通常)", "1"),
	/** ログインキャンペーン */
	LOGINCAMPAIGN_COMPLETE("ログインキャンペーン(コンプリート)", "2"),
	/** ルーレット(コイン獲得) */
	ROULETTE_COIN("ルーレット(コイン獲得)", "3"),
	/** ルーレット */
	ROULETTE("ルーレット(START)", "4"),
	/** デイリーリワード */
	DAILYREWARD("デイリーリワード", "5");

	private final String name;

	private final String value;

	private ServiceType(String name, String value) {
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
		for (ServiceType serviceType : ServiceType.values()) {
			if (value == serviceType.getValue()) {
				return serviceType.getName();
			}
		}
		return "";
	}
}
