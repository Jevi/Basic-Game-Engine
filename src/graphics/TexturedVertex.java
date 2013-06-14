package graphics;

import java.util.Arrays;

import org.lwjgl.util.vector.Vector2f;

import util.Utils;

public class TexturedVertex extends Vertex {

	protected float[] st = new float[] { 0f, 0f };

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

	@Override
	public String toString() {
		return super.toString() + ", " + Arrays.toString(st);
	}
}
