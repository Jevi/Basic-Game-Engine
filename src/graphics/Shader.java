package graphics;

import java.io.IOException;

import util.GL;
import util.Log;
import static util.DebugLevel.*;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private int id;
	private int type;
	private String source;

	public Shader(String source, int type) throws IOException {
		this.source = source;
		this.type = type;
		id = glCreateShader(type);
		GL.checkError();
		glShaderSource(id, source);
		GL.checkError();
		glCompileShader(id);
		GL.checkShaderInfo(id);

		Log.println(LOW_DEBUG, toString() + " Construction Complete");
	}

	public void destroy() {
		glDeleteShader(id);
		GL.checkError();
		Log.println(LOW_DEBUG, toString() + " Desctruction Complete");
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public String getSource() {
		return source;
	}

	@Override
	public String toString() {
		return "Shader [id=" + id + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
