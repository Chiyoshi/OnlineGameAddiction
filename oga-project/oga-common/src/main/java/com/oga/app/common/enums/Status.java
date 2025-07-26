package com.oga.app.common.enums;

public enum Status {

	/** 正常終了 */
	SUCCESS("正常終了", "0"),
	/** 異常終了 */
	ERROR("異常終了", "1"),
	/** スキップ */
	SKIP("スキップ", "2");

	private final String name;

	private final String value;

	private Status(String name, String value) {
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
	 * ステータスコード論理名を取得する
	 * 
	 * @param code ステータスコード 
	 * @return
	 */
	public static String getName(String value) {
		for (Status status : Status.values()) {
			if (value == status.getValue()) {
				return status.getName();
			}
		}
		return "";
	}
}
