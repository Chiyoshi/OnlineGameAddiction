package com.oga.app.master.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oga.app.master.model.Item;

public class ItemDto {

	@JsonProperty("item")
	private List<Item> itemList;

	/**
	 * @return itemList
	 */
	public List<Item> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList セットする itemList
	 */
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
}
