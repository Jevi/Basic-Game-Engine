package graphics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VAO {

	public final int MAX_VBO_COUNT = 16;

	private int vaoId;
	private Map<Integer, VBO> attributeListIndexToVboMap = new HashMap<Integer, VBO>();

	public VAO() {
		vaoId = glGenVertexArrays();
	}

	public void bind() {
		glBindVertexArray(vaoId);
	}

	public void unbind() {
		glBindVertexArray(0);
	}

	public void destroy() {
		for (VBO vbo : attributeListIndexToVboMap.values()) {
			vbo.destroy();
		}
		attributeListIndexToVboMap = new HashMap<Integer, VBO>();
	}

	public boolean addVBO(VBO vbo) {
		if (attributeListIndexToVboMap.size() >= MAX_VBO_COUNT / 2) {
			return false;
		}

		int vertexAttribIndex = attributeListIndexToVboMap.size();
		int colorAttribIndex = vertexAttribIndex + 1;
		attributeListIndexToVboMap.put(vertexAttribIndex, vbo);

		bind();
		vbo.bind();
		if (vbo.isTextured()) {
			int textureAttribIndex = vertexAttribIndex + 2;
			glVertexAttribPointer(vertexAttribIndex, TexturedVertex.positionElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
			glVertexAttribPointer(colorAttribIndex, TexturedVertex.colorElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.colorByteOffset);
			glVertexAttribPointer(textureAttribIndex, TexturedVertex.textureElementCount, GL_FLOAT, false, TexturedVertex.stride, TexturedVertex.textureByteOffset);
		}
		else {
			glVertexAttribPointer(vertexAttribIndex, Vertex.positionElementCount, GL_FLOAT, false, Vertex.stride, Vertex.positionByteOffset);
			glVertexAttribPointer(colorAttribIndex, Vertex.colorElementCount, GL_FLOAT, false, Vertex.stride, Vertex.colorByteOffset);
		}
		vbo.unbind();
		unbind();
		return true;
	}

	public int getId() {
		return vaoId;
	}

	public VBO getVBO(int index) {
		return attributeListIndexToVboMap.get(index);
	}

	public Set<Entry<Integer, VBO>> getVBOs() {
		return attributeListIndexToVboMap.entrySet();
	}

}
