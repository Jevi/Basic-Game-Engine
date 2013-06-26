package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IO {

	public static String getFileContent(String path) throws IOException {
		StringBuilder content = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			content.append(line).append("\n");
		}
		bufferedReader.close();
		return content.toString();
	}
}
