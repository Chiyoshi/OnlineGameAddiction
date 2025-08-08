package com.oga.app.master.repository;

import com.oga.app.master.model.Item;

/**
 * アイテム情報リポジトリ
 */
public class ItemRepository extends MasterRepository<Item> {

	@Override
	protected String mapKey(Item item) {
		return item.getItemCode();

	}
}
