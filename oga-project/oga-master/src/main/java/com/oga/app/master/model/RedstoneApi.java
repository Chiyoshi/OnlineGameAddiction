package com.oga.app.master.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * レッドストーン情報API
 */
public class RedstoneApi {

	/** KEY */
	@JsonProperty("name")
	private String name;

	/** URL */
	@JsonProperty("url")
	private String url;

	/** 説明 */
	@JsonProperty("explanation")
	private String explanation;

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url セットする url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return explanation
	 */
	public String getExplanation() {
		return explanation;
	}

	/**
	 * @param explanation セットする explanation
	 */
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

}