package com.oga.app.master.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oga.app.master.model.RedstoneApi;

public class RedstoneApiDto {

	@JsonProperty("redstoneapi")
	private List<RedstoneApi> redstoneApiList;

	/**
	 * @return redstoneApiList
	 */
	public List<RedstoneApi> getRedstoneApiList() {
		return redstoneApiList;
	}

	/**
	 * @param redstoneApiList セットする redstoneApiList
	 */
	public void setRedstoneApiList(List<RedstoneApi> redstoneApiList) {
		this.redstoneApiList = redstoneApiList;
	}
}
