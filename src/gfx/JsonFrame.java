package gfx;

public class JsonFrame {
	private float x;
	private float y;
	private float w;
	private float h;

	public JsonFrame() {

	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getW() {
		return w;
	}

	public float getH() {
		return h;
	}

	@Override
	public String toString() {
		return "JsonFrame [x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + "]";
	}

}
