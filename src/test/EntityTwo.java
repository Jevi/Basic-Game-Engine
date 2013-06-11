package test;

import java.io.IOException;
import java.util.Arrays;

import graphics.Graphics;
import graphics.Shader;
import graphics.ShaderProgram;
import graphics.Texture;
import graphics.TexturedVertex;
import graphics.VAO;
import graphics.VBO;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;

import util.GLException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import component.Component;

import core.Entity;

public class EntityTwo extends Entity {

	private Vec2 position = new Vec2(0.5f, 0.5f);
	private Vec2 dimension = new Vec2(0.1f, 0.1f);

	public EntityTwo(String id) {
		super(id);
	}

	@Override
	protected void createComponents() {
		
		this.addComponent(new Component("Movement", true) {
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
		});

		this.addComponent(new Component("Render", true) {

			private VAO vao = new VAO();
			private ShaderProgram shaderProgram = new ShaderProgram();
			private VBO vbo;
			private TexturedVertex[] verticies;
			private Texture texture;

			public void init(Entity entity) {
				super.init(entity);

				verticies = new TexturedVertex[4];
				for (int i = 0; i < verticies.length; i++) {
					verticies[i] = new TexturedVertex();
				}
				verticies[0].setXY(position.x - dimension.x, position.y + dimension.y); verticies[0].setRGB(1, 0, 0); verticies[0].setST(0, 0);
				verticies[1].setXY(position.x - dimension.x, position.y - dimension.y); verticies[1].setRGB(0, 1, 0); verticies[1].setST(0, 1);
				verticies[2].setXY(position.x + dimension.x, position.y - dimension.y); verticies[2].setRGB(0, 0, 1); verticies[2].setST(1, 1);
				verticies[3].setXY(position.x + dimension.x, position.y + dimension.y); verticies[3].setRGB(1, 1, 1); verticies[3].setST(1, 0);

				vbo = new VBO(verticies, GL_QUADS, GL_DYNAMIC_DRAW);
				System.out.println(Arrays.toString(vbo.getVertices()));
				vao.addVBO(vbo);

				try {
					shaderProgram.attachShader(new Shader("shaders/textureVertex.glsl", GL_VERTEX_SHADER));
					shaderProgram.attachShader(new Shader("shaders/textureFragment.glsl", GL_FRAGMENT_SHADER));
					shaderProgram.link();
					shaderProgram.bindAttributeLocation("in_Position");
					shaderProgram.bindAttributeLocation("in_Color");
					shaderProgram.bindAttributeLocation("in_TextureCoord");
					shaderProgram.validate();
				}
				catch (IOException | GLException e) {
					e.printStackTrace();
				}

				texture = new Texture("res/img/smile.png", GL_TEXTURE0);
				try {
					texture.load();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			};

			@Override
			public void update(int delta) {
				verticies[0].setXY(position.x - dimension.x, position.y + dimension.y);
				verticies[1].setXY(position.x - dimension.x, position.y - dimension.y);
				verticies[2].setXY(position.x + dimension.x, position.y - dimension.y);
				verticies[3].setXY(position.x + dimension.x, position.y + dimension.y);
				vbo.setVerticies(verticies);
				Graphics.render(vao, shaderProgram, texture);
				// Graphics.render(vao, shaderProgram);
			}

			@Override
			public void destroy() {
				vao.destroy();
				texture.destroy();
				shaderProgram.destroy();
			}
		});
	}
}
