package util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class GL {

	private static int currentAvailableAttributeIndex = 0;

	public static synchronized int getNextAvailableAttribIndex() {
		int index = currentAvailableAttributeIndex;
		currentAvailableAttributeIndex++;
		return index;
	}

	public static void checkError() throws GLException {
		int errorValue = glGetError();
		if (errorValue != GL_NO_ERROR) {
			throw new GLException(gluErrorString(errorValue));
		}
	}

	public static void checkShaderInfo(int id) throws GLException {
		String s = glGetShaderInfoLog(id, 1000);
		if (!s.isEmpty() && !s.trim().contains("No errors.")) {
			throw new GLException(s);
		}
	}

	public static Vector2f glToPixel(Vector2f glCoordinate) {
		Vector2f windowCoordinates = new Vector2f();
		windowCoordinates.x = ((1 + glCoordinate.x) / 2) * Display.getWidth();
		windowCoordinates.y = ((1 + glCoordinate.y) / 2) * Display.getHeight();
		return windowCoordinates;
	}

	public static Vector2f pixelToGl(Vector2f windowCoordinate) {
		Vector2f glCoordinates = new Vector2f();
		glCoordinates.x = ((windowCoordinate.x / Display.getWidth()) * 2) - 1;
		glCoordinates.y = ((windowCoordinate.y / Display.getHeight()) * 2) - 1;
		return glCoordinates;
	}

	public static Vector2f glDimensionsToPixelDimensions(Vector2f dimension) {
		Vector2f glDimensions = new Vector2f(dimension);

		Vector2f windowDimensions = new Vector2f();
		windowDimensions.x = ((1 + glDimensions.x) / 2) * Display.getWidth();
		windowDimensions.y = ((1 + glDimensions.y) / 2) * Display.getHeight();

		return windowDimensions;
	}

	public static Vector2f pixelDimensionsToGlDimension(Vector2f dimension) {
		Vector2f windowDimensions = new Vector2f(dimension);

		if (windowDimensions.x <= Display.getWidth() / 2) {
			windowDimensions.x += Display.getWidth() / 2;
		}

		if (windowDimensions.y <= Display.getHeight() / 2) {
			windowDimensions.y += Display.getHeight() / 2;
		}

		Vector2f glDimensions = new Vector2f();
		glDimensions.x = ((windowDimensions.x / Display.getWidth()) * 2) - 1;
		glDimensions.y = ((windowDimensions.y / Display.getHeight()) * 2) - 1;
		return glDimensions;
	}

	// -----------------------------------------------------------------------------------------

	public static Vector2f textureToWindow(Vector2f glCoordinate, Vector2f textureDimension) {
		Vector2f windowCoordinates = new Vector2f();
		windowCoordinates.x = glCoordinate.x * textureDimension.x;
		windowCoordinates.y = Math.abs(-(glCoordinate.y - 1) * textureDimension.y);
		return windowCoordinates;
	}

	public static Vector2f windowToTexture(Vector2f windowCoordinate, Vector2f textureDimension) {
		Vector2f glCoordinates = new Vector2f();
		glCoordinates.x = windowCoordinate.x / textureDimension.x;
		glCoordinates.y = -(windowCoordinate.y / textureDimension.y) + 1;
		return glCoordinates;
	}
}
