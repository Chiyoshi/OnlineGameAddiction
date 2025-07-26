package com.oga.app.dataaccess.entity;

/*
 * メニューアイテム情報
 */
public class Menu extends BaseEntity {

	/** メニューID */
	private int menuId;

	/** メニュー名 */
	private String menuName;

	/** 画像パス */
	private String imagePath;
	
	/** 表示有無フラグ */
	private String viewFlg;

	/** 表示順序 */
	private int sortOrder;

	/** 登録日 */
	private String registrationDate;

	/** 更新日 */
	private String updateDate;

	/** 削除フラグ */
	private String deleteFlg;

	/** 削除日 */
	private String deleteDate;

	/**
	 * @return menuId
	 */
	public int getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId セットする menuId
	 */
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName セットする menuName
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath セットする imagePath
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * @return viewFlg
	 */
	public String getViewFlg() {
		return viewFlg;
	}

	/**
	 * @param viewFlg セットする viewFlg
	 */
	public void setViewFlg(String viewFlg) {
		this.viewFlg = viewFlg;
	}

	/**
	 * @return sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder セットする sortOrder
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return registrationDate
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate セットする registrationDate
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate セットする updateDate
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return deleteFlg
	 */
	public String getDeleteFlg() {
		return deleteFlg;
	}

	/**
	 * @param deleteFlg セットする deleteFlg
	 */
	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	/**
	 * @return deleteDate
	 */
	public String getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @param deleteDate セットする deleteDate
	 */
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}
}
