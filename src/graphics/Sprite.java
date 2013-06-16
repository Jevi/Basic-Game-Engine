package graphics;

import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;

import util.GL;
import util.GLException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;

public class Sprite {

	private VBO vbo;
	private Texture texture;

	private TexturedVertex[] vertices;
	private Vector2f position = new Vector2f(0.0f, 0.0f);
	private Vector2f dimension = new Vector2f(0.1f, 0.1f);

	private Vector2f low = new Vector2f(0f, 0f);
	private Vector2f high = new Vector2f(1f, 1f);

	public Sprite(String path) throws GLException, IOException {
		init(path);
	}

	public Sprite(String path, Vector2f position, Vector2f dimension) throws GLException, IOException {
		this.position = GL.pixelToGl(position);
		this.dimension = GL.pixelDimensionsToGlDimension(dimension);
		init(path);
	}

	private void init(String path) throws GLException, IOException {
		texture = new Texture(path, GL_TEXTURE0);
		load();

		vertices = new TexturedVertex[4];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new TexturedVertex();
		}

		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y); // top
																				// left
		vertices[0].setST(low.x, low.y);
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y); // bottom
																				// left
		vertices[1].setST(low.x, high.y);
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y); // bottom
																				// right
		vertices[2].setST(high.x, high.y);
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y); // top
																				// right
		vertices[3].setST(high.x, low.y);

		vbo = new VBO(vertices, GL_QUADS, GL_STATIC_DRAW);
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
		return GL.glToPixel(position);
	}

	public void setPosition(Vector2f position) {
		this.position = GL.pixelToGl(position);
		updateData();
	}

	public Vector2f getDimension() {
		return GL.glDimensionsToPixelDimensions(dimension);
	}

	public void setDimension(Vector2f dimension) {
		this.dimension = GL.pixelDimensionsToGlDimension(dimension);
		updateData();
	}
}
