package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;

import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;

import util.GL;
import util.GLException;

public class SpriteSheet {

	private VBO vbo;
	private Texture texture;

	private TexturedVertex[] vertices;

	// sprite position and dimension on screen
	private Vector2f position = new Vector2f(0.0f, 0.0f); // gl
	private Vector2f dimension = new Vector2f(0.5f, 0.5f); // gl

	// define subimage of spritesheet
	private Vector2f low = new Vector2f(0f, 0f); // gl
	private Vector2f high = new Vector2f(1f, 1f); // gl

	private Vector2f frame = new Vector2f(0, 0);

	private Vector2f spriteDimension; // pixels
	private Vector2f frameCount;

	private Vector2f frameDimensions; // gl

	public SpriteSheet(String path, Vector2f spriteDimensions) throws GLException, IOException {
		this.spriteDimension = new Vector2f(spriteDimensions);
		init(path);
	}

	public SpriteSheet(String path, Vector2f spriteDimensions, Vector2f position, Vector2f dimension) throws GLException, IOException {
		this.position = GL.pixelToGl(position);
		this.dimension = GL.pixelDimensionsToGlDimension(dimension);
		this.spriteDimension = new Vector2f(spriteDimensions);
		init(path);
	}

	private void init(String path) throws GLException, IOException {
		texture = new Texture(path, GL_TEXTURE0);
		texture.load();

		frameCount = new Vector2f(texture.getWidth() / spriteDimension.x, texture.getHeight() / spriteDimension.y);
		frameDimensions = new Vector2f(1 / frameCount.x, 1 / frameCount.y);

		low = new Vector2f((frame.x % frameCount.x) * frameDimensions.x, (frame.y % frameCount.y) * frameDimensions.y);
		high = new Vector2f(low.x + frameDimensions.x, low.y + frameDimensions.y);

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

		low = new Vector2f((frame.x % frameCount.x) * frameDimensions.x, (frame.y % frameCount.y) * frameDimensions.y);
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

	public Vector2f getFrame() {
		return frame;
	}

	public void setFrame(Vector2f frame) {
		this.frame = new Vector2f(frame);
		updateData();
	}

	public Vector2f getLow() {
		return low;
	}

	public Vector2f getHigh() {
		return high;
	}

	public Vector2f getSpriteDimension() {
		return spriteDimension;
	}

	public Vector2f getFrameCount() {
		return frameCount;
	}

	public Vector2f getFrameDimensions() {
		return frameDimensions;
	}
}
