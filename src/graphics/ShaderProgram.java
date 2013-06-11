package graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashSet;
import java.util.Set;

import util.GL;
import util.GLException;

public class ShaderProgram {

	private int id;
	private Set<Shader> shaders = new HashSet<Shader>();
	private Set<String> attriutes = new HashSet<String>();

	private boolean isLinked = false;

	public ShaderProgram() {
		id = glCreateProgram();
	}

	public void destroy() {
		for (Shader shader : shaders) {
			glDetachShader(id, shader.getId());
			shader.destroy();
		}
		glDeleteProgram(id);
		shaders = new HashSet<Shader>();
		attriutes = new HashSet<String>();
	}

	public void bind() {
		glUseProgram(id);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public boolean attachShader(Shader shader) throws GLException {
		if (!shaders.contains(shader) && !isLinked) {
			glAttachShader(id, shader.getId());
			GL.checkError();
			shaders.add(shader);
			return true;
		}
		return false;
	}

	public boolean bindAttributeLocation(String attribute) throws GLException {
		if (!attriutes.contains(attribute)) {
			glBindAttribLocation(id, attriutes.size(), attribute);
			GL.checkError();
			attriutes.add(attribute);
			return true;
		}
		return false;
	}

	public void link() throws GLException {
		if (!isLinked) {
			glLinkProgram(id);
			GL.checkError();
		}
	}

	public void validate() throws GLException {
		glValidateProgram(id);
		GL.checkError();
	}

	public int getId() {
		return id;
	}
}
