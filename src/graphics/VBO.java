package graphics;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.util.Arrays;

import junit.framework.Assert;

import org.lwjgl.BufferUtils;

public class VBO {

	private Vertex[] vertices;
	private FloatBuffer vertexData;

	private int id;

	private int mode;
	private int usage;

	private boolean isTextured = false;

	public VBO(Vertex[] verticies, int mode, int usage) {
		this.vertices = Arrays.copyOf(verticies, verticies.length);
		this.mode = mode;
		this.usage = usage;
		vertexData = BufferUtils.createFloatBuffer(verticies.length * Vertex.elementCount);

		id = glGenBuffers();
		initData();
	}

	public VBO(TexturedVertex[] verticies, int mode, int usage) {
		this.vertices = Arrays.copyOf(verticies, verticies.length);
		this.mode = mode;
		this.usage = usage;
		isTextured = true;
		vertexData = BufferUtils.createFloatBuffer(verticies.length * TexturedVertex.elementCount);

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
		vertices = null;
		vertexData = null;
	}

	public Vertex[] getVertices() {
		return Arrays.copyOf(vertices, vertices.length);
	}

	public void setVerticies(Vertex[] verticies) {
		Assert.assertEquals(this.vertices.length, verticies.length);
		this.vertices = Arrays.copyOf(verticies, verticies.length);
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
