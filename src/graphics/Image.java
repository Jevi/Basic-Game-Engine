package graphics;

import java.io.IOException;

import org.jbox2d.common.Vec2;

import util.GL;
import util.GLException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;

public class Image {

	private VBO vbo;
	private Texture texture;

	private TexturedVertex[] vertices;
	private Vec2 position = new Vec2(0.0f, 0.0f);
	private Vec2 dimension = new Vec2(0.1f, 0.1f);

	public Image(String path) throws GLException, IOException {
		init(path);
	}

	public Image(String path, int width, int height) throws GLException, IOException {
		dimension = GL.windowCoordinatesToGlCoordinates(new Vec2(width, height));
		init(path);
	}

	public Image(String path, Vec2 position, int width, int height) throws GLException, IOException {
		this.position = GL.windowCoordinatesToGlCoordinates(position);
		dimension = GL.windowDimensionsToGlDimension(new Vec2(width, height));
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
		texture = new Texture(path, GL_TEXTURE0);
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

	public Vec2 getPosition() {
		return GL.glCoordinatesToWindowCoordinates(position);
	}

	public void setPosition(Vec2 position) {
		this.position = GL.windowCoordinatesToGlCoordinates(position);
		updateData();
	}

	public Vec2 getDimension() {
		return GL.glDimensionsToWindowDimensions(dimension);
	}

	public void setDimension(Vec2 dimension) {
		this.dimension = GL.windowDimensionsToGlDimension(dimension);
		updateData();
	}
}
