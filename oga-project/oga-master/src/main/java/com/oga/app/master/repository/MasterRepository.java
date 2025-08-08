package com.oga.app.master.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class MasterRepository<T> {

	protected Map<String, T> itemMap = new HashMap<>();

	public void setItemList(List<T> items) {
		this.itemMap = items.stream()
				.map(item -> Map.entry(mapKey(item), item))
				.filter(entry -> entry.getKey() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	/**
	 * 対象マスタ情報のキーを取得する
	 * 
	 * @param item 対象マスタ情報
	 * @return
	 */
	protected abstract String mapKey(T item);

	/**
	 * 存在チェック
	 * 
	 * @param key キー
	 * @return
	 */
	public boolean contains(String key) {
		return itemMap.containsKey(key);
	}

	/**
	 * 指定したキーの値で取得する
	 * 
	 * @param key キー
	 * @return
	 */
	public T get(String key) {
		return itemMap.get(key);
	}
}
