package util;

import org.jbox2d.common.Vec2;

public class Conversion {

	public static Vec2 PixelsToMeters(Vec2 pixels, float pixelToMeterRatio) {
		return new Vec2(PixelsToMeters(pixels.x, pixelToMeterRatio), PixelsToMeters(pixels.y, pixelToMeterRatio));
	}

	public static Vec2 MetersToPixels(Vec2 meters, float pixelToMeterRatio) {
		return new Vec2(MetersToPixels(meters.x, pixelToMeterRatio), MetersToPixels(meters.y, pixelToMeterRatio));
	}

	public static float PixelsToMeters(float pixels, float pixelToMeterRatio) {
		return pixels / pixelToMeterRatio;
	}

	public static float MetersToPixels(float meters, float pixelToMeterRatio) {
		return meters * pixelToMeterRatio;
	}
}
