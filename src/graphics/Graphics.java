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

	public static void render(VBO vbo, ShaderProgram shaderProgram) {
		shaderProgram.bind();
		vbo.bind();

		int vertexAttribIndex = 0;

		glEnableVertexAttribArray(vertexAttribIndex);
		glEnableVertexAttribArray(vertexAttribIndex + 1);

		glDrawArrays(vbo.getMode(), 0, Vertex.elementCount);

		glDisableVertexAttribArray(vertexAttribIndex);
		glDisableVertexAttribArray(vertexAttribIndex + 1);

		vbo.unbind();
		shaderProgram.unbind();
	}

	public static void render(VBO vbo, Texture texture) {
		texture.bind();
		vbo.bind();

		glEnable(GL_TEXTURE_2D);
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
		glDisable(GL_TEXTURE_2D);

		vbo.unbind();
		texture.unbind();
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
		texture.unbind();
		shaderProgram.unbind();
	}

	public static void renderString(String string, int textureObject, int gridSize, float x, float y, float characterWidth, float characterHeight) {
		glPushAttrib(GL_TEXTURE_BIT | GL_ENABLE_BIT | GL_COLOR_BUFFER_BIT);
		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureObject);
		// Enable linear texture filtering for smoothed results.
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		// Enable additive blending. This means that the colours will be added
		// to already existing colours in the
		// frame buffer. In practice, this makes the black parts of the texture
		// become invisible.
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		// Store the current model-view matrix.
		glPushMatrix();
		// Offset all subsequent (at least up until 'glPopMatrix') vertex
		// coordinates.
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		// Iterate over all the characters in the string.
		for (int i = 0; i < string.length(); i++) {
			// Get the ASCII-code of the character by type-casting to integer.
			int asciiCode = (int) string.charAt(i);
			// There are 16 cells in a texture, and a texture coordinate ranges
			// from 0.0 to 1.0.
			final float cellSize = 1.0f / gridSize;
			// The cell's x-coordinate is the greatest integer smaller than
			// remainder of the ASCII-code divided by the
			// amount of cells on the x-axis, times the cell size.
			float cellX = ((int) asciiCode % gridSize) * cellSize;
			// The cell's y-coordinate is the greatest integer smaller than the
			// ASCII-code divided by the amount of
			// cells on the y-axis.
			float cellY = ((int) asciiCode / gridSize) * cellSize;
			glTexCoord2f(cellX, cellY + cellSize);
			glVertex2f(i * characterWidth / 3, y);
			glTexCoord2f(cellX + cellSize, cellY + cellSize);
			glVertex2f(i * characterWidth / 3 + characterWidth / 2, y);
			glTexCoord2f(cellX + cellSize, cellY);
			glVertex2f(i * characterWidth / 3 + characterWidth / 2, y + characterHeight);
			glTexCoord2f(cellX, cellY);
			glVertex2f(i * characterWidth / 3, y + characterHeight);
		}
		glEnd();
		glPopMatrix();
		glPopAttrib();
	}

}
