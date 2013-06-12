package util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.util.glu.GLU.*;

import org.jbox2d.common.Vec2;
import org.lwjgl.opengl.Display;

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

	public static Vec2 glCoordinatesToWindowCoordinates(Vec2 glCoordinate) {
		Vec2 windowCoordinates = new Vec2();
		windowCoordinates.x = ((1 + glCoordinate.x) / 2) * Display.getWidth();
		windowCoordinates.y = ((1 + glCoordinate.y) / 2) * Display.getHeight();
		return windowCoordinates;
	}

	public static Vec2 windowCoordinatesToGlCoordinates(Vec2 windowCoordinate) {
		Vec2 glCoordinates = new Vec2();
		glCoordinates.x = ((windowCoordinate.x / Display.getWidth()) * 2) - 1;
		glCoordinates.y = ((windowCoordinate.y / Display.getHeight()) * 2) - 1;
		return glCoordinates;
	}

	public static Vec2 glDimensionsToWindowDimensions(Vec2 dimension) {
		Vec2 glDimensions = new Vec2(dimension);

		Vec2 windowDimensions = new Vec2();
		windowDimensions.x = ((1 + glDimensions.x) / 2) * Display.getWidth();
		windowDimensions.y = ((1 + glDimensions.y) / 2) * Display.getHeight();

		if (windowDimensions.x > Display.getWidth() / 2) {
			windowDimensions.x -= Display.getWidth() / 2;
		}

		if (windowDimensions.y > Display.getHeight() / 2) {
			windowDimensions.y -= Display.getHeight() / 2;
		}

		return windowDimensions;
	}

	public static Vec2 windowDimensionsToGlDimension(Vec2 dimension) {
		Vec2 windowDimensions = new Vec2(dimension);

		if (windowDimensions.x <= Display.getWidth() / 2) {
			windowDimensions.x += Display.getWidth() / 2;
		}

		if (windowDimensions.y <= Display.getHeight() / 2) {
			windowDimensions.y += Display.getHeight() / 2;
		}

		Vec2 glDimensions = new Vec2();
		glDimensions.x = ((windowDimensions.x / Display.getWidth()) * 2) - 1;
		glDimensions.y = ((windowDimensions.y / Display.getHeight()) * 2) - 1;
		return glDimensions;
	}
}
