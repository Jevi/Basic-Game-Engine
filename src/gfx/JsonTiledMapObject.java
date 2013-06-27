package gfx;

public class JsonTiledMapObject {

	private String name;
	private String type;
	private float x, y;
	private int width, height;
	private boolean ellipse = false;

	public JsonTiledMapObject() {
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isEllipse() {
		return ellipse;
	}

	@Override
	public String toString() {
		return "JsonTiledMapObject [name=" + name + ", type=" + type + ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", ellipse=" + ellipse + "]";
	}

}
