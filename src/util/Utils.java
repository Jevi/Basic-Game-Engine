package util;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

public class Utils {

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

	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
}
