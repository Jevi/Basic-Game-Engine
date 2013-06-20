package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.Arrays;

import junit.framework.Assert;

import org.lwjgl.BufferUtils;

import util.GL;

public class VBO {

	private Vertex[] vertices;
	private FloatBuffer vertexData;

	private int id;

	private int mode;
	private int usage;

	private boolean isTextured = false;

	public VBO(Vertex[] vertices, int mode, int usage) {
		this.vertices = Arrays.copyOf(vertices, vertices.length);
		this.mode = mode;
		this.usage = usage;
		vertexData = BufferUtils.createFloatBuffer(vertices.length * Vertex.elementCount);

		id = glGenBuffers();
		initData();
	}

	public VBO(TexturedVertex[] vertices, int mode, int usage) {
		this.vertices = Arrays.copyOf(vertices, vertices.length);
		this.mode = mode;
		this.usage = usage;
		isTextured = true;
		vertexData = BufferUtils.createFloatBuffer(vertices.length * TexturedVertex.elementCount);

		id = glGenBuffers();
		initData();
	}

	private void initData() {
		for (Vertex vertex : vertices) {
			if (isTextured) {
				TexturedVertex v = (TexturedVertex) vertex;
				vertexData.put(v.getElements());
			}
			else {
				vertexData.put(vertex.getXYZW());
				vertexData.put(vertex.getRGBA());
			}
		}
		vertexData.flip();

		bind();
		glBufferData(GL_ARRAY_BUFFER, vertexData, usage);
		GL.checkError();

		int vertexAttribIndex = 0;
		int colorAttribIndex = vertexAttribIndex + 1;

		if (this.isTextured()) {
			int textureAttribIndex = vertexAttribIndex + 2;
			glVertexAttribPointer(vertexAttribIndex, TexturedVertex.positionElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
			GL.checkError();
			glVertexAttribPointer(colorAttribIndex, TexturedVertex.colorElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.colorByteOffset);
			GL.checkError();
			glVertexAttribPointer(textureAttribIndex, TexturedVertex.textureElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.textureByteOffset);
			GL.checkError();
		}
		else {
			glVertexAttribPointer(vertexAttribIndex, Vertex.positionElementCount, GL_FLOAT, false, Vertex.stride, Vertex.positionByteOffset);
			GL.checkError();
			glVertexAttribPointer(colorAttribIndex, Vertex.colorElementCount, GL_FLOAT, false, Vertex.stride, Vertex.colorByteOffset);
			GL.checkError();
		}
		unbind();
	}

	private void updateData() {
		for (Vertex vertex : vertices) {
			if (isTextured) {
				TexturedVertex v = (TexturedVertex) vertex;
				vertexData.put(v.getElements());
			}
			else {
				vertexData.put(vertex.getXYZW());
				vertexData.put(vertex.getRGBA());
			}
		}
		vertexData.flip();

		bind();
		glBufferSubData(GL_ARRAY_BUFFER, 0, vertexData);
		GL.checkError();
		unbind();
	}

	public void bind() {
		glBindBuffer(GL_ARRAY_BUFFER, id);
	}

	public void unbind() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void destroy() {
		glDeleteBuffers(id);
		GL.checkError();
		vertices = null;
		vertexData = null;
	}

	public Vertex[] getVertices() {
		return Arrays.copyOf(vertices, vertices.length);
	}

	public void setVertices(Vertex[] vertices) {
		Assert.assertEquals(this.vertices.length, vertices.length);
		this.vertices = Arrays.copyOf(vertices, vertices.length);
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

	public boolean isTextured() {
		return isTextured;
	}

}
