package com.oga.app.common.enums;

public enum YesNo {

	/** YES */
	YES("YES", "Y"),
	/** No */
	NO("NO", "N");

	private final String name;

	private final String value;

	private YesNo(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}
}
