package com.oga.app.service.manager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.oga.app.dataaccess.entity.Master;
import com.oga.app.service.provider.MasterProvider;

public class MasterDataManager {

	/** WebDriverManager */
	private static MasterDataManager manger = null;

	/** �}�X�^��� */
	private ConcurrentMap<String, String> map = null;

	/**
	 * �R���X�g���N�^
	 */
	public MasterDataManager() {
		if (map == null) {
			this.map = new ConcurrentHashMap<>();
		}

		// ��������
		init();
	}

	/**
	 * �C���X�^���X�擾
	 */
	public static synchronized MasterDataManager getInstance() {
		if (manger == null) {
			manger = new MasterDataManager();
		}
		return manger;
	}

	/**
	 * ��������
	 */
	public void init() {
		// �}�X�^��񃊃X�g���擾����
		List<Master> masterList = MasterProvider.getInstance().getMasterList();

		for (Master master : masterList) {
			if (null != master.getValue()) {
				// �}�X�^�f�[�^�Ɋi�[����
				put(master.getKey(), master.getValue());
			}
		}
	}

	/**
	 * �}�X�^�����擾����
	 * 
	 * @param key �L�[
	 * @return �l
	 */
	public String get(String key) {
		return this.map.get(key);
	}

	/**
	 * �}�X�^����ݒ肷��
	 * 
	 * @param key   �L�[
	 * @param value �l
	 */
	public void put(String key, String value) {
		this.map.put(key, value);
	}

	/**
	 * �}�X�^�����폜����
	 * 
	 * @param key   �L�[
	 * @param value �l
	 */
	public void remove(String key) {
		this.map.remove(key);
	}

	/**
	 * ���݃`�F�b�N
	 * 
	 * @param key �L�[
	 * @return True�F���݂���AFale�F���݂��Ȃ�
	 */
	public boolean contains(String key) {
		if (this.map.containsKey(key)) {
			return true;
		}
		return false;
	}

}
