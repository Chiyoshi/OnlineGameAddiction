package com.oga.app.dataaccess.entity;

/*
 * ���j���[�A�C�e�����
 */
public class MenuItem extends BaseEntity {

	/** ���j���[ID */
	private int menuId;

	/** ���j���[�A�C�e��ID */
	private int menuItemId;

	/** ���j���[�A�C�e���� */
	private String menuItemName;

	/** �\���L���t���O */
	private String viewFlg;

	/** �\������ */
	private int sortOrder;

	/** �o�^�� */
	private String registrationDate;

	/** �X�V�� */
	private String updateDate;

	/** �폜�t���O */
	private String deleteFlg;

	/** �폜�� */
	private String deleteDate;

	/**
	 * @return menuId
	 */
	public int getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId �Z�b�g���� menuId
	 */
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return menuItemId
	 */
	public int getMenuItemId() {
		return menuItemId;
	}

	/**
	 * @param menuItemId �Z�b�g���� menuItemId
	 */
	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}

	/**
	 * @return menuItemName
	 */
	public String getMenuItemName() {
		return menuItemName;
	}

	/**
	 * @param menuItemName �Z�b�g���� menuItemName
	 */
	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	/**
	 * @return viewFlg
	 */
	public String getViewFlg() {
		return viewFlg;
	}

	/**
	 * @param viewFlg �Z�b�g���� viewFlg
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
	 * @param sortOrder �Z�b�g���� sortOrder
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
	 * @param registrationDate �Z�b�g���� registrationDate
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
	 * @param updateDate �Z�b�g���� updateDate
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
	 * @param deleteFlg �Z�b�g���� deleteFlg
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
	 * @param deleteDate �Z�b�g���� deleteDate
	 */
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}

}
