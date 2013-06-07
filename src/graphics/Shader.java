package graphics;

import java.io.IOException;

import util.Utils;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private int id;
	private int type;
	private String path;
	private String source;

	public Shader(String path, int type) throws IOException {
		this.path = path;
		this.type = type;
		id = glCreateShader(type);
		source = Utils.getFileContent(path);
		glShaderSource(id, source);
		glCompileShader(id);
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
