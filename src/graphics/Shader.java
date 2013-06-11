package graphics;

import java.io.IOException;

import util.GL;
import util.GLException;
import util.IO;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private int id;
	private int type;
	private String path;
	private String source;

	public Shader(String path, int type) throws IOException, GLException {
		this.path = path;
		this.type = type;
		id = glCreateShader(type);
		source = IO.getFileContent(path);
		glShaderSource(id, source);
		glCompileShader(id);
		GL.checkShaderInfo(id);
	}

	public void destroy() {
		glDeleteShader(id);
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public String getPath() {
		return path;
	}

	public String getSource() {
		return source;
	}
}
