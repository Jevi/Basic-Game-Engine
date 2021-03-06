package gfx;

import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Sprite {

	private VBO vbo;
	private Texture texture;

	private Vertex[] vertices;
	private Vector2f position;
	private Vector2f dimension;

	private Vector2f low = new Vector2f(0f, 0f);
	private Vector2f high = new Vector2f(1f, 1f);

	public Sprite(Texture texture, Vector2f position, Vector2f dimension) {
		this.texture = texture;
		this.position = new Vector2f(position);
		this.dimension = new Vector2f(dimension);

		init();
	}

	private void init() {

		vertices = new Vertex[4];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex();
		}

		// top left
		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		vertices[0].setST(low.x, low.y);
		// bottom left
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		vertices[1].setST(low.x, high.y);
		// bottom right
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		vertices[2].setST(high.x, high.y);
		// top right
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);
		vertices[3].setST(high.x, low.y);

		vbo = new VBO(vertices, GL_QUADS, GL_STATIC_DRAW);
	}

	private void updateData() {
		// top left
		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		// bottom left
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		// bottom right
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		// top right
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);

		vbo.setVertices(vertices);
	}

	public void destroy() {
		vbo.destroy();
	}

	public void load() throws IOException {
		texture.load();
	}

	public void render() {
		Graphics.render(vbo, texture);
	}

	public String getPath() {
		return texture.getPath();
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
