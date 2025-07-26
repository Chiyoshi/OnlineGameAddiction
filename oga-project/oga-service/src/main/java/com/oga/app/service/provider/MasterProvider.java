package com.oga.app.service.provider;

import java.util.List;

import com.oga.app.dataaccess.dao.MasterDao;
import com.oga.app.dataaccess.entity.Master;

/**
 * �}�X�^���v���p�C�_
 */
public class MasterProvider {

	/** �}�X�^���v���p�C�_ */
	private static MasterProvider masterProvider;

	/**
	 * �R���X�g���N�^
	 */
	public MasterProvider() {
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized MasterProvider getInstance() {
		if (masterProvider == null) {
			masterProvider = new MasterProvider();
		}
		return masterProvider;
	}

	/**
	 * �}�X�^��񃊃X�g���擾����
	 * @return
	 */
	public List<Master> getMasterList() {
		return MasterDao.getInstance().findAll();
	}

	/**
	 * �}�X�^����o�^����
	 * 
	 * @param master �}�X�^���
	 */
	public void insertMaster(Master master) {
		MasterDao.getInstance().insert(master);
	}

	/**
	 * �w�肵���}�X�^�����폜����
	 * 
	 * @param userId ���[�UID
	 */
	public void deleteMaster(String key) {
		MasterDao.getInstance().delete(key);
	}

	/**
	 * �}�X�^�������ׂč폜����
	 */
	public void deleteMaster() {
		MasterDao.getInstance().delete();
	}
}
