package com.oga.app.dataaccess.entity;

/*
 * ���j���[�A�C�e�����
 */
public class Menu extends BaseEntity {

	/** ���j���[ID */
	private int menuId;

	/** ���j���[�� */
	private String menuName;

	/** �摜�p�X */
	private String imagePath;
	
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
	 * @return menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName �Z�b�g���� menuName
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
	 * @param imagePath �Z�b�g���� imagePath
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
