package graphics;

import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;

import util.GL;
import util.GLException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.ARBTextureRectangle.*;

public class Sprite {

	private VBO vbo;
	private Texture texture;

	private TexturedVertex[] vertices;
	private Vector2f position = new Vector2f(0.0f, 0.0f);
	private Vector2f dimension = new Vector2f(0.1f, 0.1f);

	public Sprite(String path) throws GLException, IOException {
		init(path);
	}

	public Sprite(String path, Vector2f position, Vector2f dimension) throws GLException, IOException {
		this.position = GL.windowCoordinatesToGlCoordinates(position);
		this.dimension = GL.windowDimensionsToGlDimension(dimension);
		init(path);
	}

	private void init(String path) throws GLException, IOException {
		vertices = new TexturedVertex[4];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new TexturedVertex();
		}

		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		vertices[0].setST(0, 0);
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		vertices[1].setST(0, 1);
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		vertices[2].setST(1, 1);
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);
		vertices[3].setST(1, 0);

		vbo = new VBO(vertices, GL_QUADS, GL_STATIC_DRAW);
		texture = new Texture(path, GL_TEXTURE_2D, GL_TEXTURE0);

	}

	private void updateData() {
		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);

		vbo.setVertices(vertices);
	}

	public void destroy() {
		vbo.destroy();
		texture.destroy();
	}

	public void load() throws IOException {
		texture.load();
	}

	public void render() {
		Graphics.render(vbo, texture);
	}

	public Vector2f getPosition() {
		return GL.glCoordinatesToWindowCoordinates(position);
	}

	public void setPosition(Vector2f position) {
		this.position = GL.windowCoordinatesToGlCoordinates(position);
		updateData();
	}

	public Vector2f getDimension() {
		return GL.glDimensionsToWindowDimensions(dimension);
	}

	public void setDimension(Vector2f dimension) {
		this.dimension = GL.windowDimensionsToGlDimension(dimension);
		updateData();
	}
}
