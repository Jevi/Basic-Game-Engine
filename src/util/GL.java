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
}
