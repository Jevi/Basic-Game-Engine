package test;

import java.io.IOException;

import graphics.Graphics;
import graphics.Shader;
import graphics.ShaderProgram;
import graphics.VAO;
import graphics.VBO;
import graphics.Vertex;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import component.Component;

import core.Entity;

public class EntityOne extends Entity {

	private Vec2 position = new Vec2(0.0f, 0.0f);

	public EntityOne(String id) {
		super(id);
	}

	@Override
	protected void createComponents() {
		// ----- Create Components -----

		Component movementComponent = new Component("Movement", true) {
			@Override
			public void update(int delta) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					position.x -= 0.001f * delta;
				}
				else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					position.x += 0.001f * delta;
				}

				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					position.y += 0.001f * delta;
				}
				else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					position.y -= 0.001f * delta;
				}
			}

			@Override
			public void destroy() {
			}
		};

		Component renderComponent = new Component("Render", true) {

			VAO vao = new VAO();
			ShaderProgram shaderProgram = new ShaderProgram();

			public void init(Entity entity) {
				super.init(entity);

				Vertex[] verticies = new Vertex[4];
				for (int i = 0; i < verticies.length; i++) {
					verticies[i] = new Vertex();
				}
				verticies[0].setXY(-0.5f, 0.5f);
				verticies[0].setRGB(1f, 0f, 0f);
				verticies[1].setXY(0.5f, 0.5f);
				verticies[1].setRGB(0f, 1f, 0f);
				verticies[2].setXY(0.5f, -0.5f);
				verticies[2].setRGB(0f, 0f, 1f);
				verticies[3].setXY(-0.5f, -0.5f);
				verticies[3].setRGB(0.5f, 0.5f, 0.5f);

				VBO vbo = new VBO(verticies, GL_LINE_LOOP, GL_STATIC_DRAW);
				vao.addVBO(vbo);

				try {
					shaderProgram.attachShader(new Shader("shaders/vertex.glsl", GL_VERTEX_SHADER));
					shaderProgram.attachShader(new Shader("shaders/fragment.glsl", GL_FRAGMENT_SHADER));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				shaderProgram.link();
				shaderProgram.bindAttributeLocation("in_Position");
				shaderProgram.bindAttributeLocation("in_Color");
				shaderProgram.validate();
			};

			@Override
			public void update(int delta) {
				Graphics.drawVAO(vao, shaderProgram);
			}

			@Override
			public void destroy() {
				vao.destroy();
				shaderProgram.destroy();
			}
		};

		// ----- Add Components -----
		this.addComponent(movementComponent);
		this.addComponent(renderComponent);
	}
}
