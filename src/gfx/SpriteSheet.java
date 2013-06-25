package gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;

import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;

public class SpriteSheet {

	private VBO vbo;
	private Texture texture;

	private Vertex[] vertices;

	// sprite position and dimension on screen
	private Vector2f position;
	private Vector2f dimension;

	// define subimage of spritesheet
	private Vector2f low = new Vector2f(0f, 0f); // gl
	private Vector2f high = new Vector2f(1f, 1f); // gl

	private int frame = 0;

	private Vector2f spriteDimension; // pixels
	private Vector2f frameCount;

	private Vector2f frameDimensions; // gl

	public SpriteSheet(String path, Vector2f spriteDimensions, Vector2f position, Vector2f dimension) throws IOException {
		this.spriteDimension = new Vector2f(spriteDimensions);
		this.position = new Vector2f(position);
		this.dimension = new Vector2f(dimension);

		texture = new Texture(path, GL_TEXTURE0);
		texture.load();

		init();
	}

	public SpriteSheet(Texture texture, Vector2f spriteDimensions, Vector2f position, Vector2f dimension) {
		this.texture = texture;
		this.spriteDimension = new Vector2f(spriteDimensions);
		this.position = new Vector2f(position);
		this.dimension = new Vector2f(dimension);

		init();
	}

	private void init() {
		frameCount = new Vector2f(texture.getWidth() / spriteDimension.x, texture.getHeight() / spriteDimension.y);
		frameDimensions = new Vector2f(1 / frameCount.x, 1 / frameCount.y);

		low = new Vector2f((frame % (int) frameCount.x) * frameDimensions.x, (frame / (int) frameCount.y) * frameDimensions.y);
		high = new Vector2f(low.x + frameDimensions.x, low.y + frameDimensions.y);

		vertices = new Vertex[4];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex();
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

		low = new Vector2f((frame % (int) frameCount.x) * frameDimensions.x, (frame / (int) frameCount.y) * frameDimensions.y);
		high = new Vector2f(low.x + frameDimensions.x, low.y + frameDimensions.y);

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

	public void destroy() {
		vbo.destroy();
		texture.destroy();
	}

	public void render() {
		Graphics.render(vbo, texture);
	}

	public String getPath() {
		return texture.getPath();
	}

	public Texture getTexture() {
		return texture;
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

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
		updateData();
	}

	public Vector2f getSpriteDimension() {
		return spriteDimension;
	}

	public Vector2f getFrameCount() {
		return frameCount;
	}
}
