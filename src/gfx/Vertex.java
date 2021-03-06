package gfx;

import java.util.Arrays;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import util.Utils;

public class Vertex {
	// Vertex data
	private float[] xyzw = new float[] { 0f, 0f, 0f, 1f };
	private float[] rgba = new float[] { 1f, 1f, 1f, 1f };
	private float[] st = new float[] { 0f, 0f };

	// The amount of bytes an element has
	public static final int elementBytes = 4;

	// Elements per parameter
	public static final int positionElementCount = 4;
	public static final int colorElementCount = 4;
	public static final int textureElementCount = 2;

	// Bytes per parameter
	public static final int positionBytesCount = positionElementCount * elementBytes;
	public static final int colorByteCount = colorElementCount * elementBytes;
	public static final int textureByteCount = textureElementCount * elementBytes;

	// Byte offsets per parameter
	public static final int positionByteOffset = 0;
	public static final int colorByteOffset = positionByteOffset + positionBytesCount;
	public static final int textureByteOffset = colorByteOffset + colorByteCount;

	// The amount of elements that a vertex has
	public static final int elementCount = positionElementCount + colorElementCount + textureElementCount;
	// The size of a vertex in bytes, like in C/C++: sizeof(Vertex)
	public static final int stride = positionBytesCount + colorByteCount + textureByteCount;

	public void setXY(float x, float y) {
		this.setXYZW(x, y, 0f, 1f);
	}

	public void setXY(Vector2f vec) {
		this.setXYZW(vec.x, vec.y, 0f, 1f);
	}

	public void setXYZ(float x, float y, float z) {
		this.setXYZW(x, y, z, 1f);
	}

	public void setXYZ(Vector3f vec) {
		this.setXYZW(vec.x, vec.y, vec.z, 1f);
	}

	public void setXYZW(float x, float y, float z, float w) {
		this.xyzw = new float[] { x, y, z, w };
	}

	public void setXYZW(Vector4f vec) {
		this.xyzw = new float[] { vec.x, vec.y, vec.z, vec.w };
	}

	public void setRGB(float r, float g, float b) {
		this.setRGBA(r, g, b, 1f);
	}

	public void setRGB(Vector3f vec) {
		this.setRGBA(vec.x, vec.y, vec.z, 1f);
	}

	public void setRGBA(float r, float g, float b, float a) {
		this.rgba = new float[] { r, g, b, a };
	}

	public void setRGBA(Vector4f vec) {
		this.rgba = new float[] { vec.x, vec.y, vec.z, vec.w };
	}

	public void setST(float s, float t) {
		st = new float[] { s, t };
	}

	public void setST(Vector2f vec) {
		st = new float[] { vec.x, vec.y };
	}

	public float[] getElements() {
		return Utils.concatAll(xyzw, rgba, st);
	}

	public float[] getST() {
		return Arrays.copyOf(st, st.length);
	}

	public float getX() {
		return xyzw[0];
	}

	public float getY() {
		return xyzw[1];
	}

	public float getZ() {
		return xyzw[2];
	}

	public float getW() {
		return xyzw[3];
	}

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
		stringBuilder.append(", ");
		stringBuilder.append(Arrays.toString(st));
		return stringBuilder.toString();
	}
}