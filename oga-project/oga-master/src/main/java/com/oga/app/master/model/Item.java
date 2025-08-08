package com.oga.app.master.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

	/** アイテムコード */
	@JsonProperty("item_code")
	private String itemCode;

	/** アイテム名 */
	@JsonProperty("item_name")
	private String itemName;

	/**
	 * @return itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode セットする itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName セットする itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}