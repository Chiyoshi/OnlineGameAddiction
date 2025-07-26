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
	 * ファイル一覧を取得する
	 * 
	 * @param dir ディレクトリ
	 * @return
	 */
	public static List<File> getFileList(String dir) {
		File[] files = new File(dir).listFiles();
		return Arrays.asList(files);
	}

	/**
	 * ディレクトリを作成する
	 * 
	 * @param dir ディレクトリ
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
	 * 存在チェック
	 * 
	 * @param filePath ファイルパスパス
	 */
	public static boolean isExists(String filePath) {
		return Files.exists(Paths.get(filePath));
	}

	/**
	 * CSVファイル書き出す
	 * 
	 * @param csvFilePath ファイルパス
	 * @param header ヘッダー
	 * @param data データ
	 * @param isAppend 追記型
	 * @throws SystemException 
	 */
	public static void writeCsvFile(String csvFilePath, Object[] header, List<Object[]> data, boolean isAppend)
			throws SystemException {
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(csvFilePath, Charset.forName("MS932"), isAppend))) {
			// ヘッダーを書き込む
			writeLine(writer, header);

			// データを書き込む
			for (Object[] row : data) {
				writeLine(writer, row);
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new SystemException("CSVファイルの書き込みに失敗しました", e);
		}
	}

	/**
	 * 1行書き込み
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
	 * CSVファイル読み込み
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
				// 行をカンマで分割して配列にする
				String[] values = line.split(",");

				// ダブルクォーテーションを削除する
				for (int i = 0; i < values.length; i++) {
					values[i] = values[i].replaceAll("\"", "");

					if (StringUtil.isNullOrEmpty(values[i])) {
						values[i] = null;
					}
				}

				data.add(values);
			}
		} catch (IOException e) {
			throw new SystemException("CSVファイルの読み込みに失敗しました", e);
		}
		return data;
	}
}
