package graphics;

import java.util.Arrays;

public class Vertex {
	// Vertex data
	protected float[] xyzw = new float[] { 0f, 0f, 0f, 1f };
	protected float[] rgba = new float[] { 1f, 1f, 1f, 1f };

	// The amount of elements that a vertex has
	public static final int elementCount = 8;

	public static final int positionElementCount = 4;

	public static final int colorElementCount = 4;

	// The amount of bytes an element has
	public static final int elementBytes = 4;
	// The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
	public static final int sizeInBytes = elementBytes * elementCount;

	// Setters

	public void setXY(float x, float y) {
		this.setXYZW(x, y, 0f, 1f);
	}

	public void setXYZ(float x, float y, float z) {
		this.setXYZW(x, y, z, 1f);
	}

	public void setXYZW(float x, float y, float z, float w) {
		this.xyzw = new float[] { x, y, z, w };
	}

	public void setRGB(float r, float g, float b) {
		this.setRGBA(r, g, b, 1f);
	}

	public void setRGBA(float r, float g, float b, float a) {
		this.rgba = new float[] { r, g, b, a };
	}

	// Getters

	public float[] getXY() {
		return Arrays.copyOf(xyzw, xyzw.length - 2);
	}

	public float[] getXYZ() {
		return Arrays.copyOf(xyzw, xyzw.length - 1);
	}

	public float[] getXYZW() {
		return Arrays.copyOf(xyzw, xyzw.length);
	}

	public float[] getRGB() {
		return Arrays.copyOf(rgba, rgba.length - 1);
	}

	public float[] getRGBA() {
		return Arrays.copyOf(rgba, rgba.length);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Arrays.toString(xyzw));
		stringBuilder.append(", ");
		stringBuilder.append(Arrays.toString(rgba));
		return stringBuilder.toString();
	}
}