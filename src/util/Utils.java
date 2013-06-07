package util;

import java.util.Arrays;

public class Utils {

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
