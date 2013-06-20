package util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.util.glu.GLU.*;

public class GL {

	public static void checkError() {
		int errorValue = glGetError();
		if (errorValue != GL_NO_ERROR) {
			try {
				throw new GLException(gluErrorString(errorValue));
			}
			catch (GLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void checkShaderInfo(int id) {
		String s = glGetShaderInfoLog(id, 1000);
		if (!s.isEmpty() && !s.trim().contains("No errors.")) {
			try {
				throw new GLException(s);
			}
			catch (GLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * public static Vector2f glToPixel(Vector2f glCoordinate) { Vector2f
	 * windowCoordinates = new Vector2f(); windowCoordinates.x = ((1.0f +
	 * glCoordinate.x) / 2.0f) * (float) Display.getWidth(); windowCoordinates.y
	 * = ((1.0f + glCoordinate.y) / 2.0f) * (float) Display.getHeight(); return
	 * windowCoordinates; }
	 * 
	 * public static Vector2f pixelToGl(Vector2f windowCoordinate) { Vector2f
	 * glCoordinates = new Vector2f(); glCoordinates.x = ((windowCoordinate.x /
	 * (float) Display.getWidth()) * 2.0f) - 1.0f; glCoordinates.y =
	 * ((windowCoordinate.y / (float) Display.getHeight()) * 2.0f) - 1.0f;
	 * return glCoordinates; }
	 * 
	 * public static Vector2f glDimensionsToPixelDimensions(Vector2f dimension)
	 * { Vector2f glDimensions = new Vector2f(dimension);
	 * 
	 * Vector2f windowDimensions = new Vector2f(); windowDimensions.x = ((1.0f +
	 * glDimensions.x) / 2.0f) * (float) Display.getWidth(); windowDimensions.y
	 * = ((1.0f + glDimensions.y) / 2.0f) * (float) Display.getHeight();
	 * 
	 * return windowDimensions; }
	 * 
	 * public static Vector2f pixelDimensionsToGlDimension(Vector2f dimension) {
	 * Vector2f windowDimensions = new Vector2f(dimension);
	 * 
	 * if (windowDimensions.x <= (float) Display.getWidth() / 2.0f) {
	 * windowDimensions.x += (float) Display.getWidth() / 2.0f; }
	 * 
	 * if (windowDimensions.y <= (float) Display.getHeight() / 2.0f) {
	 * windowDimensions.y += (float) Display.getHeight() / 2.0f; }
	 * 
	 * Vector2f glDimensions = new Vector2f(); glDimensions.x =
	 * ((windowDimensions.x / (float) Display.getWidth()) * 2.0f) - 1.0f;
	 * glDimensions.y = ((windowDimensions.y / (float) Display.getHeight()) *
	 * 2.0f) - 1.0f; return glDimensions; }
	 * 
	 * //
	 * ------------------------------------------------------------------------
	 * -----------------
	 * 
	 * public static Vector2f textureToWindow(Vector2f glCoordinate, Vector2f
	 * textureDimension) { Vector2f windowCoordinates = new Vector2f();
	 * windowCoordinates.x = glCoordinate.x * textureDimension.x;
	 * windowCoordinates.y = Math.abs(-(glCoordinate.y - 1.0f) *
	 * textureDimension.y); return windowCoordinates; }
	 * 
	 * public static Vector2f windowToTexture(Vector2f windowCoordinate,
	 * Vector2f textureDimension) { Vector2f glCoordinates = new Vector2f();
	 * glCoordinates.x = windowCoordinate.x / textureDimension.x;
	 * glCoordinates.y = -(windowCoordinate.y / textureDimension.y) + 1.0f;
	 * return glCoordinates; }
	 */
}
