package gfx;

import java.util.Arrays;

import junit.framework.Assert;

import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class Quad {

	private VBO vbo;

	private Vector2f position;
	private Vector2f dimension;
	private Vertex[] vertices = new Vertex[4];

	private Vector2f low = new Vector2f(0f, 0f);
	private Vector2f high = new Vector2f(1f, 1f);

	public Quad(Vector2f position, Vector2f dimension, int usage) {

		this.position = new Vector2f(position);
		this.dimension = new Vector2f(dimension);

		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		vertices[0].setST(low.x, low.y);
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		vertices[1].setST(low.x, high.y);
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		vertices[2].setST(high.x, high.y);
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);
		vertices[3].setST(high.x, low.y);
		vbo = new VBO(vertices, GL_QUADS, usage);
	}

	private void updateVertices() {
		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		vertices[0].setST(low.x, low.y);
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		vertices[1].setST(low.x, high.y);
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		vertices[2].setST(high.x, high.y);
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);
		vertices[3].setST(high.x, low.y);

		vbo.setVertices(vertices);
	}

	public void setPosition(Vector2f position) {
		this.position = new Vector2f(position);
		updateVertices();
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setDimension(Vector2f dimension) {
		this.dimension = new Vector2f(dimension);
		updateVertices();
	}

	public void setTextureRegions(Vector2f low, Vector2f high) {
		this.low = new Vector2f(low);
		this.high = new Vector2f(high);
		updateVertices();
	}

	public Vector2f[] getTextureRegions() {
		return new Vector2f[] { low, high };
	}

	public void setVertices(Vertex[] vertices) {
		Assert.assertEquals(this.vertices.length, vertices.length);
		this.vertices = Arrays.copyOf(vertices, vertices.length);
		vbo.setVertices(this.vertices);
	}

	public Vertex[] getVertices() {
		return Arrays.copyOf(vertices, vertices.length);
	}
}
