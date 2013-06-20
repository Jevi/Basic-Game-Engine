package graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static util.DebugLevel.*;
import util.GL;
import util.Log;

public class ShaderProgram {

	private int id;
	private Set<Shader> shaders = new LinkedHashSet<Shader>();
	private Set<String> attriutes = new LinkedHashSet<String>();

	public ShaderProgram() {
		id = glCreateProgram();
		GL.checkError();
		Log.println(LOW_DEBUG, toString() + " Initialization Complete");
	}

	public void destroy() {
		for (Shader shader : shaders) {
			glDetachShader(id, shader.getId());
			GL.checkError();
			shader.destroy();
		}
		glDeleteProgram(id);
		GL.checkError();
		shaders.removeAll(shaders);
		attriutes.removeAll(attriutes);

		Log.println(LOW_DEBUG, toString() + " Destruction Complete");
	}

	public void bind() {
		glUseProgram(id);
		GL.checkError();
	}

	public void unbind() {
		glUseProgram(0);
		GL.checkError();
	}

	public void attachShader(Shader shader) {
		if (!shaders.contains(shader)) {
			glAttachShader(id, shader.getId());
			GL.checkError();
			shaders.add(shader);
		}
	}

	public void attachShaders(Shader... shaders) {
		for (Shader shader : shaders) {
			if (!this.shaders.contains(shader)) {
				glAttachShader(id, shader.getId());
				GL.checkError();
				this.shaders.add(shader);
			}
		}
	}

	public boolean bindAttributeLocation(String attribute) {
		if (!attriutes.contains(attribute)) {
			glBindAttribLocation(id, attriutes.size(), attribute);
			GL.checkError();
			attriutes.add(attribute);
			return true;
		}
		return false;
	}

	public void link() {
		glLinkProgram(id);
		GL.checkError();
	}

	public void validate() {
		glValidateProgram(id);
		GL.checkError();
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ShaderProgram [id=" + id + "]";
	}

}
