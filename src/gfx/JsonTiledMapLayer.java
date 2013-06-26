package gfx;

import java.util.Arrays;

public class JsonTiledMapLayer {

	private long[] data;
	private int width, height;
	private String name;

	private int x, y;

	public JsonTiledMapLayer() {

	}

	public long[] getData() {
		return data;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "JsonTiledMapLayer [data=" + Arrays.toString(data) + ", width=" + width + ", height=" + height + ", name=" + name + ", x=" + x + ", y=" + y + "]";
	}

}
