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

		int vboVertexIndex = attributeListIndexToVboMap.size();
		int vboColorIndex = vboVertexIndex + 1;
		attributeListIndexToVboMap.put(vboVertexIndex, vbo);

		bind();
		vbo.bind();
		glVertexAttribPointer(vboVertexIndex, 4, GL_FLOAT, false, Vertex.sizeInBytes, 0);
		glVertexAttribPointer(vboColorIndex, 4, GL_FLOAT, false, Vertex.sizeInBytes, Vertex.elementBytes * 4);
		vbo.unbind();
		unbind();
		return true;
	}

	public int getId() {
		return vaoId;
	}

	public Set<Entry<Integer, VBO>> getVBOs() {
		return attributeListIndexToVboMap.entrySet();
	}

}
