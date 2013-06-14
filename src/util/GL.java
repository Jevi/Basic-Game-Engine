package util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class GL {

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

	public static Vector2f glCoordinatesToWindowCoordinates(Vector2f glCoordinate) {
		Vector2f windowCoordinates = new Vector2f();
		windowCoordinates.x = ((1 + glCoordinate.x) / 2) * Display.getWidth();
		windowCoordinates.y = ((1 + glCoordinate.y) / 2) * Display.getHeight();
		return windowCoordinates;
	}

	public static Vector2f windowCoordinatesToGlCoordinates(Vector2f windowCoordinate) {
		Vector2f glCoordinates = new Vector2f();
		glCoordinates.x = ((windowCoordinate.x / Display.getWidth()) * 2) - 1;
		glCoordinates.y = ((windowCoordinate.y / Display.getHeight()) * 2) - 1;
		return glCoordinates;
	}

	public static Vector2f glDimensionsToWindowDimensions(Vector2f dimension) {
		Vector2f glDimensions = new Vector2f(dimension);

		Vector2f windowDimensions = new Vector2f();
		windowDimensions.x = ((1 + glDimensions.x) / 2) * Display.getWidth();
		windowDimensions.y = ((1 + glDimensions.y) / 2) * Display.getHeight();

		/*
		 * if (windowDimensions.x > Display.getWidth() / 2) { windowDimensions.x
		 * -= Display.getWidth() / 2; }
		 * 
		 * if (windowDimensions.y > Display.getHeight() / 2) {
		 * windowDimensions.y -= Display.getHeight() / 2; }
		 */

		return windowDimensions;
	}

	public static Vector2f windowDimensionsToGlDimension(Vector2f dimension) {
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
}
