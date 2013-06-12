package graphics;

import java.io.IOException;

import org.jbox2d.common.Vec2;

import util.GL;
import util.GLException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Image {

	private VAO vao = new VAO();
	private Texture texture;
	private ShaderProgram shaderProgram = new ShaderProgram();

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

		/*System.out.println(String.format("Window Position (%s, %s)", position.x, position.y));
		System.out.println(String.format("Window Dimension (%s, %s)", width, height));*/

		this.position = GL.windowCoordinatesToGlCoordinates(position);
		dimension = GL.windowDimensionsToGlDimension(new Vec2(width, height));

		/*System.out.println(String.format("GL Position (%s, %s)", this.position.x, this.position.y));
		System.out.println(String.format("GL Dimension (%s, %s)", dimension.x, dimension.y));

		Vec2 windowDimensions = GL.glDimensionsToWindowDimensions(dimension);

		System.out.println(String.format("Calculated Window Dimension (%s, %s)", windowDimensions.x, windowDimensions.y));*/

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

		vao.addVBO(new VBO(vertices, GL_QUADS, GL_STATIC_DRAW));
		texture = new Texture(path, GL_TEXTURE0);

		shaderProgram.attachShader(new Shader("shaders/textureVertex.glsl", GL_VERTEX_SHADER));
		shaderProgram.attachShader(new Shader("shaders/textureFragment.glsl", GL_FRAGMENT_SHADER));
		shaderProgram.link();
		shaderProgram.bindAttributeLocation("in_Position");
		shaderProgram.bindAttributeLocation("in_Color");
		shaderProgram.bindAttributeLocation("in_TextureCoord");
		shaderProgram.validate();
	}

	private void updateData() {
		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);

		vao.getVBO(0).setVertices(vertices);
	}

	public void destroy() {
		vao.destroy();
		shaderProgram.destroy();
		texture.destroy();
	}

	public void load() throws IOException {
		texture.load();
	}

	public void render() {
		Graphics.render(vao, shaderProgram, texture);
	}

	public Vec2 getPosition() {
		return GL.glCoordinatesToWindowCoordinates(position);
	}

	public void setPosition(Vec2 position) {
		this.position = GL.windowCoordinatesToGlCoordinates(position);
		updateData();
	}

	public Vec2 getDimension() {
		return dimension;
	}

	public void setDimension(Vec2 dimension) {
		this.dimension = GL.windowDimensionsToGlDimension(dimension);
		updateData();
	}
}
