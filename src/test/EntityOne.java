package test;

import java.util.Arrays;

import graphics.Graphics;
import graphics.VAO;
import graphics.VBO;
import graphics.Vertex;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import component.Component;

import core.Entity;

public class EntityOne extends Entity {

	private Vec2 position = new Vec2(0.0f, 0.0f);
	private Vec2 dimensions = new Vec2(0.1f, 0.1f);

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

			public void init(Entity entity) {
				super.init(entity);

				Vertex[] verticies = new Vertex[4];
				for (int i = 0; i < verticies.length; i++) {
					verticies[i] = new Vertex();
				}
				verticies[0].setXY(-0.5f, 0.5f);
				verticies[0].setRGB(1f, 0f, 0f);
				verticies[1].setXY(0.5f, 0.5f);
				verticies[2].setXY(0.5f, -0.5f);
				verticies[3].setXY(-0.5f, -0.5f);

				System.out.println(Arrays.toString(verticies));
				VBO vbo = new VBO(verticies, GL_LINE_LOOP, GL_STATIC_DRAW);
				vao.addVBO(vbo);
			};

			@Override
			public void update(int delta) {
				Graphics.drawVAO(vao);
			}

			@Override
			public void destroy() {
				vao.destroy();
			}
		};

		// ----- Add Components -----
		this.addComponent(movementComponent);
		this.addComponent(renderComponent);
	}
}
