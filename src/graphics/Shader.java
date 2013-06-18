package graphics;

import java.io.IOException;

import util.GL;
import util.GLException;
import util.IO;
import util.Log;
import static util.DebugLevel.*;

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

		Log.println(LOW_DEBUG, toString() + " Construction Complete");
	}

	public void destroy() {
		glDeleteShader(id);
		Log.println(LOW_DEBUG, toString() + " Desctruction Complete");
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

	@Override
	public String toString() {
		return "Shader [id=" + id + ", type=" + type + ", path=" + path + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shader other = (Shader) obj;
		if (id != other.id)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		}
		else if (!path.equals(other.path))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		}
		else if (!source.equals(other.source))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
