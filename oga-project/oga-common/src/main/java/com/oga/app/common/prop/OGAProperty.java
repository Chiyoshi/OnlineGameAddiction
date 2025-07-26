package com.oga.app.common.prop;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.oga.app.common.utils.StringUtil;

/**
 * �v���p�e�B�t�@�C��(oga.properties)
 *
 */
public class OGAProperty {

	private static final Properties properties;

	/**
	 * �R���X�g���N�^
	 */
	private OGAProperty() {
	}

	static {
		// �v���p�e�B�t�@�C���̃p�X���擾����
		String file = System.getProperty("prop.file");

		if (StringUtil.isNullOrEmpty(file)) {
			throw new RuntimeException("�v���p�e�B�t�@�C��(oga.properties)���ݒ肳��Ă��܂���B");
		}

		properties = new Properties();

		try {
			// �v���p�e�B�t�@�C����ǂݍ���
			properties.load(Files.newBufferedReader(Paths.get(file), StandardCharsets.UTF_8));
		} catch (IOException e) {
			// �t�@�C���ǂݍ��݂Ɏ��s
			throw new RuntimeException(String.format("�v���p�e�B�t�@�C���̓ǂݍ��݂Ɏ��s���܂����B�t�@�C����:%s", file), e);
		}
	}

	/**
	 * �v���p�e�B�l���擾����
	 *
	 * @param key �L�[
	 * @return �l
	 */
	public static String getProperty(final String key) {
		return getProperty(key, "");
	}

	/**
	 * �v���p�e�B�l���擾����
	 *
	 * @param key          �L�[
	 * @param defaultValue �f�t�H���g�l
	 * @return �L�[�����݂��Ȃ��ꍇ�A�f�t�H���g�l ���݂���ꍇ�A�l
	 */
	public static String getProperty(final String key, final String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
}
