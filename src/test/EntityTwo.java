package test;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;

import graphics.Graphics;
import graphics.Shader;
import graphics.ShaderProgram;
import graphics.VBO;
import graphics.Vertex;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import org.lwjgl.util.vector.Vector2f;

import util.GL;
import util.GLException;

import component.Component;
import component.Entity;

import core.GameContainer;
import core.GameState;

public class EntityTwo extends Entity {

	private Body body;
	private final float width = 800;
	private final float height = 15;

	public EntityTwo(String id) {
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
					bodyDef.position.set(gameContainer.getWidth() / state.scale / 2.0f, (gameContainer.getHeight() - 200.0f) / state.scale / 2.0f);
					bodyDef.type = BodyType.STATIC;

					PolygonShape polygonShape = new PolygonShape();
					polygonShape.setAsBox((width / 2.0f) / state.scale, (height / 2.0f) / state.scale);

					body = state.getWorld().createBody(bodyDef);

					FixtureDef fixtureDef = new FixtureDef();
					fixtureDef.density = 0.1f;
					fixtureDef.restitution = 0.5f;
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

				updateVertices();

				// top left
				vertices[0].setRGB(1, 1, 1);
				// top right
				vertices[1].setRGB(1, 1, 1);
				// bottom right
				vertices[2].setRGB(1, 1, 1);
				// bottom left
				vertices[3].setRGB(1, 1, 1);

				vbo = new VBO(vertices, GL_QUADS, GL_STATIC_DRAW);

				try {
					shaderProgram.attachShader(new Shader("shaders/vertex.glsl", GL_VERTEX_SHADER));
					shaderProgram.attachShader(new Shader("shaders/fragment.glsl", GL_FRAGMENT_SHADER));
					shaderProgram.bindAttributeLocation("inPosition");
					shaderProgram.bindAttributeLocation("inColor");
					shaderProgram.link();
					shaderProgram.validate();
				}
				catch (GLException | IOException e) {
					e.printStackTrace();
				}

			};

			@Override
			public void update(int delta) {
				updateVertices();
				vbo.setVertices(vertices);
				Graphics.render(vbo);
			}

			@Override
			public void destroy() {
				vbo.destroy();
				shaderProgram.destroy();
			}

			private void updateVertices() {
				if (gameState instanceof StateOne) {
					StateOne state = (StateOne) gameState;
					position = body.getPosition().mul(state.scale);
				}

				// top left
				vertices[0].setXY(GL.pixelToGl(new Vector2f(position.x - width / 2.0f, position.y + height / 2.0f)));
				// top right
				vertices[1].setXY(GL.pixelToGl(new Vector2f(position.x + width / 2.0f, position.y + height / 2.0f)));
				// bottom right
				vertices[2].setXY(GL.pixelToGl(new Vector2f(position.x + width / 2.0f, position.y - height / 2.0f)));
				// bottom left
				vertices[3].setXY(GL.pixelToGl(new Vector2f(position.x - width / 2.0f, position.y - height / 2.0f)));
			}
		});
	}
}
