package graphics;

import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;

public class Sprite {

	private VBO vbo;
	private Texture texture;

	private TexturedVertex[] vertices;
	private Vector2f position;
	private Vector2f dimension;

	private Vector2f low = new Vector2f(0f, 0f);
	private Vector2f high = new Vector2f(1f, 1f);

	public Sprite(String path, Vector2f position, Vector2f dimension) throws IOException {
		this.position = new Vector2f(position);
		this.dimension = new Vector2f(dimension);
		init(path);
	}

	private void init(String path) throws IOException {
		texture = new Texture(path, GL_TEXTURE0);
		load();

		vertices = new TexturedVertex[4];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new TexturedVertex();
		}

		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		vertices[0].setST(low.x, low.y);
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		vertices[1].setST(low.x, high.y);
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		vertices[2].setST(high.x, high.y);
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);
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
		return new Vector2f(position);
	}

	public void setPosition(Vector2f position) {
		this.position = new Vector2f(position);
		updateData();
	}

	public Vector2f getDimension() {
		return new Vector2f(dimension);
	}

	public void setDimension(Vector2f dimension) {
		this.dimension = new Vector2f(dimension);
		updateData();
	}
}
