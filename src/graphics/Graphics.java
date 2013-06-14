package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.Map.Entry;

public class Graphics {

	public static void render(VBO vbo) {
		vbo.bind();

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);

		glVertexPointer(Vertex.positionElementCount, GL_FLOAT, Vertex.stride, Vertex.positionByteOffset);
		glColorPointer(Vertex.colorElementCount, GL_FLOAT, Vertex.stride, Vertex.colorByteOffset);

		glDrawArrays(vbo.getMode(), 0, Vertex.elementCount);

		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);

		vbo.unbind();
	}

	public static void render(VBO vbo, Texture texture) {
		texture.bind();
		vbo.bind();

		glEnable(texture.getTarget());
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		glVertexPointer(TexturedVertex.positionElementCount, GL_FLOAT, TexturedVertex.stride, TexturedVertex.positionByteOffset);
		glColorPointer(TexturedVertex.colorElementCount, GL_FLOAT, TexturedVertex.stride, TexturedVertex.colorByteOffset);
		glTexCoordPointer(TexturedVertex.textureElementCount, GL_FLOAT, TexturedVertex.stride, TexturedVertex.textureByteOffset);

		glDrawArrays(vbo.getMode(), 0, TexturedVertex.elementCount);

		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);

		glDisable(GL_BLEND);
		glDisable(texture.getTarget());

		vbo.unbind();
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
