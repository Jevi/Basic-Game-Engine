package graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashSet;
import java.util.Set;

import util.Utils;

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
	}

	public void bind() {
		glUseProgram(id);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public boolean attachShader(Shader shader) {
		if (!shaders.contains(shader) && !isLinked) {
			glAttachShader(id, shader.getId());
			shaders.add(shader);
			return true;
		}
		return false;
	}

	public boolean bindAttributeLocation(String attribute) {
		if (!attriutes.contains(attribute)) {
			glBindAttribLocation(id, attriutes.size(), attribute);
			attriutes.add(attribute);
			return true;
		}
		return false;
	}

	public void link() {
		if (!isLinked) {
			glLinkProgram(id);
		}
	}

	public boolean validate() {
		glValidateProgram(id);
		try {
			Utils.glCheckError();
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public int getId() {
		return id;
	}
}
