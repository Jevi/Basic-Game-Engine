package graphics;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.util.Arrays;

import junit.framework.Assert;

import org.lwjgl.BufferUtils;

public class VBO {

	private Vertex[] verticies;
	private FloatBuffer vertexData;

	private int id;

	private int mode;
	private int usage;

	public VBO(Vertex[] verticies, int mode, int usage) {
		this.verticies = Arrays.copyOf(verticies, verticies.length);
		this.mode = mode;
		this.usage = usage;
		vertexData = BufferUtils.createFloatBuffer(this.verticies.length * Vertex.elementCount);

		id = glGenBuffers();
		updateData();
	}

	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER, id);
	}

	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private void updateData() {
		for (Vertex vertex : verticies) {
			vertexData.put(vertex.getXYZW());
			vertexData.put(vertex.getRGBA());
		}
		vertexData.flip();

		bind();
		glBufferData(GL_ARRAY_BUFFER, vertexData, usage);
		unbind();
	}

	public void destroy() {
		glDeleteBuffers(id);
		verticies = null;
	}

	public Vertex[] getVerticies() {
		return Arrays.copyOf(verticies, verticies.length);
	}

	public void setVerticies(Vertex[] verticies) {
		Assert.assertEquals(this.verticies.length, verticies.length);
		this.verticies = Arrays.copyOf(verticies, verticies.length);
		updateData();
	}

	public int getId() {
		return id;
	}

	public int getMode() {
		return mode;
	}

	public int getUsage() {
		return usage;
	}
}
