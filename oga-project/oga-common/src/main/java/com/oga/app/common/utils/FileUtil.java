package com.oga.app.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.oga.app.common.exception.SystemException;

public class FileUtil {

	/**
	 * �t�@�C���ꗗ���擾����
	 * 
	 * @param dir �f�B���N�g��
	 * @return
	 */
	public static List<File> getFileList(String dir) {
		File[] files = new File(dir).listFiles();
		return Arrays.asList(files);
	}

	/**
	 * �f�B���N�g�����쐬����
	 * 
	 * @param dir �f�B���N�g��
	 * @throws SystemException 
	 */
	public static void createDirectory(String dir) throws SystemException {

		try {
			Files.createDirectories(Paths.get(dir));
		} catch (IOException e) {
			throw new SystemException(e);
		}
	}

	/**
	 * ���݃`�F�b�N
	 * 
	 * @param filePath �t�@�C���p�X�p�X
	 */
	public static boolean isExists(String filePath) {
		return Files.exists(Paths.get(filePath));
	}

	/**
	 * CSV�t�@�C�������o��
	 * 
	 * @param csvFilePath �t�@�C���p�X
	 * @param header �w�b�_�[
	 * @param data �f�[�^
	 * @param isAppend �ǋL�^
	 * @throws SystemException 
	 */
	public static void writeCsvFile(String csvFilePath, Object[] header, List<Object[]> data, boolean isAppend)
			throws SystemException {
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(csvFilePath, Charset.forName("MS932"), isAppend))) {
			// �w�b�_�[����������
			writeLine(writer, header);

			// �f�[�^����������
			for (Object[] row : data) {
				writeLine(writer, row);
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new SystemException("CSV�t�@�C���̏������݂Ɏ��s���܂���", e);
		}
	}

	/**
	 * 1�s��������
	 * 
	 * @param writer
	 * @param values
	 * @throws IOException
	 */
	private static void writeLine(BufferedWriter writer, Object[] values) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			sb.append("\"").append(values[i]).append("\"");
			if (i < values.length - 1) {
				sb.append(",");
			}
		}
		writer.write(sb.toString());
		writer.newLine();
	}

	/**
	 * CSV�t�@�C���ǂݍ���
	 * 
	 * @param csvFilePath
	 * @return
	 * @throws SystemException
	 */
	public static List<String[]> readCsvFile(String csvFilePath) throws SystemException {
		List<String[]> data = new ArrayList<>();
		String line = "";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath, Charset.forName("MS932")))) {
			while ((line = br.readLine()) != null) {
				// �s���J���}�ŕ������Ĕz��ɂ���
				String[] values = line.split(",");

				// �_�u���N�H�[�e�[�V�������폜����
				for (int i = 0; i < values.length; i++) {
					values[i] = values[i].replaceAll("\"", "");

					if (StringUtil.isNullOrEmpty(values[i])) {
						values[i] = null;
					}
				}

				data.add(values);
			}
		} catch (IOException e) {
			throw new SystemException("CSV�t�@�C���̓ǂݍ��݂Ɏ��s���܂���", e);
		}
		return data;
	}
}
