package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.lwjgl.util.glu.GLU.*;

import static org.lwjgl.opengl.GL11.*;

public class Utils {

	public static void glCheckError() throws Exception {
		int errorCheckValue = glGetError();
		if (errorCheckValue != GL_NO_ERROR) {
			throw new Exception(gluErrorString(errorCheckValue));
		}
	}

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

	@SafeVarargs
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	public static float[] concatAll(float[] first, float[]... rest) {
		int totalLength = first.length;
		for (float[] array : rest) {
			totalLength += array.length;
		}
		float[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (float[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}
}
