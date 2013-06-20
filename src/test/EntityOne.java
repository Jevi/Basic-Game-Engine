package test;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;

import graphics.Graphics;
import graphics.Shader;
import graphics.ShaderProgram;
import graphics.VAO;
import graphics.VBO;
import graphics.Vertex;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import org.lwjgl.util.vector.Vector2f;

import util.IO;
import util.Conversion;

import component.Component;
import component.Entity;

import core.GameContainer;
import core.GameState;

public class EntityOne extends Entity {

	private Body body;
	private final float hx = 30;
	private final float hy = 30;

	public EntityOne(String id) {
		super(id);
	}

	@Override
	public void init(GameContainer gameContainer, GameState gameState) {
		super.init(gameContainer, gameState);
	}

	@Override
	protected void createComponents() {

		addComponent(new Component("Physics", true) {

			@Override
			public void init(Entity entity) {
				super.init(entity);

				if (gameState instanceof StateOne) {
					StateOne state = (StateOne) gameState;

					BodyDef bodyDef = new BodyDef();
					bodyDef.position.set(Conversion.PixelsToMeters(gameContainer.getWidth(), state.pixelToMeterRatio) / 2.0f, Conversion.PixelsToMeters(gameContainer.getHeight(), state.pixelToMeterRatio));
					bodyDef.type = BodyType.DYNAMIC;
					bodyDef.angle = (float) Math.toRadians(0);

					PolygonShape polygonShape = new PolygonShape();
					polygonShape.setAsBox(Conversion.PixelsToMeters(hx, state.pixelToMeterRatio), Conversion.PixelsToMeters(hy, state.pixelToMeterRatio));

					body = state.getWorld().createBody(bodyDef);

					FixtureDef fixtureDef = new FixtureDef();
					fixtureDef.density = 0.1f;
					fixtureDef.shape = polygonShape;
					body.createFixture(fixtureDef);
				}
			}

			@Override
			public void update(int delta) {

			}

			@Override
			public void destroy() {
			}
		});

		addComponent(new Component("Render", true) {

			private VAO vao = new VAO();
			private VBO vbo;
			private ShaderProgram shaderProgram = new ShaderProgram();
			private Vertex[] vertices;
			private Vec2 position;

			public void init(Entity entity) {
				super.init(entity);

				vertices = new Vertex[4];
				for (int i = 0; i < vertices.length; i++) {
					vertices[i] = new Vertex();
				}

				// top left
				vertices[0].setRGB(1, 0, 0);
				// top right (1, 0, 1);
				vertices[1].setRGB(0, 1, 0);
				// bottom right (1, 0, 1);
				vertices[2].setRGB(0, 0, 1);
				// bottom left (1, 0, 1);
				vertices[3].setRGB(0, 0, 0);

				updateVertices();

				vbo = new VBO(vertices, GL_QUADS, GL_DYNAMIC_DRAW);
				vao.addVBO(vbo);

				try {
					shaderProgram.attachShader(new Shader(IO.getFileContent("shaders/vertex.glsl"), GL_VERTEX_SHADER));
					shaderProgram.attachShader(new Shader(IO.getFileContent("shaders/fragment.glsl"), GL_FRAGMENT_SHADER));
					shaderProgram.bindAttributeLocation("inPosition");
					shaderProgram.bindAttributeLocation("inColor");
					shaderProgram.link();
					shaderProgram.validate();
				}
				catch (IOException e) {
					e.printStackTrace();
				}

			};

			@Override
			public void update(int delta) {
				updateVertices();
				vbo.setVertices(vertices);

				glPushMatrix();
				glTranslatef(position.x, position.y, 0);
				glRotated(Math.toDegrees(body.getAngle()), 0, 0, 1);
				glTranslatef(-position.x, -position.y, 0);
				Graphics.render(vbo);
				glPopMatrix();

			}

			@Override
			public void destroy() {
				vbo.destroy();
				vao.destroy();
				shaderProgram.destroy();
			}

			private void updateVertices() {
				if (gameState instanceof StateOne) {
					StateOne state = (StateOne) gameState;
					position = body.getPosition().mul(state.pixelToMeterRatio);

					// top left
					vertices[0].setXY(new Vector2f(position.x - hx, position.y + hy));
					// top right
					vertices[1].setXY(new Vector2f(position.x + hx, position.y + hy));
					// bottom right
					vertices[2].setXY(new Vector2f(position.x + hx, position.y - hy));
					// bottom left
					vertices[3].setXY(new Vector2f(position.x - hx, position.y - hy));
				}
			}
		});
	}
}
