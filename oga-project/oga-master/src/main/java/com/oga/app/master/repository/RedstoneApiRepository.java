package com.oga.app.master.repository;

import com.oga.app.master.model.RedstoneApi;

/**
 * レッドストーンAPI情報リポジトリ
 */
public class RedstoneApiRepository extends MasterRepository<RedstoneApi> {

	@Override
	protected String mapKey(RedstoneApi redstoneApi) {
		return redstoneApi.getName();

	}
}
