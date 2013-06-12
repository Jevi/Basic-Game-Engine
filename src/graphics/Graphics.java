package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.Map.Entry;

public class Graphics {

	public static void render(VBO vbo, ShaderProgram shaderProgram, Texture texture) {
		shaderProgram.bind();
		glDrawArrays(vbo.getMode(), 0, TexturedVertex.elementCount);
		shaderProgram.unbind();
	}

	public static void render(VAO vao, ShaderProgram shaderProgram) {
		shaderProgram.bind();
		vao.bind();
		for (Entry<Integer, VBO> entry : vao.getVBOs()) {
			Integer index = entry.getKey();
			VBO vbo = entry.getValue();
			glEnableVertexAttribArray(index);
			glEnableVertexAttribArray(index + 1);
			glDrawArrays(vbo.getMode(), 0, Vertex.elementCount);
			glDisableVertexAttribArray(index);
			glDisableVertexAttribArray(index + 1);
		}
		vao.unbind();
		shaderProgram.unbind();
	}

	public static void render(VAO vao, ShaderProgram shaderProgram, Texture texture) {
		shaderProgram.bind();
		glActiveTexture(texture.getTextureUnit());
		texture.bind();

		vao.bind();
		for (Entry<Integer, VBO> entry : vao.getVBOs()) {
			Integer index = entry.getKey();
			VBO vbo = entry.getValue();
			glEnableVertexAttribArray(index);
			glEnableVertexAttribArray(index + 1);
			glEnableVertexAttribArray(index + 2);
			glDrawArrays(vbo.getMode(), 0, TexturedVertex.elementCount);
			glDisableVertexAttribArray(index);
			glDisableVertexAttribArray(index + 1);
			glDisableVertexAttribArray(index + 2);
		}
		vao.unbind();
		shaderProgram.unbind();
	}

}
