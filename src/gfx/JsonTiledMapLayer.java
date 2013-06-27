package gfx;

import java.util.Arrays;

public class JsonTiledMapLayer {

	private long[] data;
	private JsonTiledMapObject[] objects;

	private int width, height;
	private String name;
	private String type;

	private int x, y;

	public JsonTiledMapLayer() {

	}

	public JsonTiledMapLayerType getType() {
		return JsonTiledMapLayerType.valueOf(type.toUpperCase());
	}

	public long[] getData() {
		return data;
	}

	public JsonTiledMapObject[] getObjects() {
		return objects;
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
		return "JsonTiledMapLayer [data=" + Arrays.toString(data) + ", objects=" + Arrays.toString(objects) + ", width=" + width + ", height=" + height + ", name=" + name + ", type=" + type + ", x="
				+ x + ", y=" + y + "]";
	}

}
