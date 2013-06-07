package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.Map.Entry;

public class Graphics {

	public static void drawVAO(VAO vao) {
		vao.bind();
		for (Entry<Integer, VBO> entry : vao.getVBOs()) {
			glEnableVertexAttribArray(entry.getKey());
			glEnableVertexAttribArray(entry.getKey() + 1);
			glDrawArrays(entry.getValue().getMode(), 0, Vertex.elementCount / 2);
			glDisableVertexAttribArray(entry.getKey());
			glDisableVertexAttribArray(entry.getKey() + 1);
		}
		vao.unbind();
	}
}
