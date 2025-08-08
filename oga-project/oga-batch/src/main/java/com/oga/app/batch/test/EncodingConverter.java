package com.oga.app.batch.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class EncodingConverter {

	public static void main(String[] args) throws IOException {
		Path rootDir = Paths.get("C:\\Users\\Chiyoshi\\git\\OnlineGameAddiction\\oga-project\\oga-master\\src"); // 対象ディレクトリを指定
		Charset sourceEncoding = Charset.forName("MS932");
		Charset targetEncoding = Charset.forName("UTF-8");

		Files.walk(rootDir)
				.filter(p -> Files.isRegularFile(p))
				.filter(p -> p.toString().endsWith(".java") || p.toString().endsWith(".txt"))
				.forEach(p -> {
					try {
						Path temp = Files.createTempFile("converted_", ".tmp");
						try (
								BufferedReader reader = Files.newBufferedReader(p, sourceEncoding);
								BufferedWriter writer = Files.newBufferedWriter(temp, targetEncoding)) {
							String line;
							while ((line = reader.readLine()) != null) {
								writer.write(line);
								writer.newLine();
							}
						}
						Files.move(temp, p, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("変換完了: " + p);
					} catch (IOException e) {
						System.err.println("エラー発生: " + p + " -> " + e.getMessage());
					}
				});
	}
}
