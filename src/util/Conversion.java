package util;

public class Conversion {

	public static float PixelsToMeters(float pixels, float pixelToMeterRatio) {
		return pixels / pixelToMeterRatio;
	}

	public static float MetersToPixels(float meters, float pixelToMeterRatio) {
		return meters * pixelToMeterRatio;
	}
}
