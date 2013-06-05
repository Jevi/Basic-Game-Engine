package test;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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

		Component movementComponent = new Component("Movement Component", true) {
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

		Component renderComponent = new Component("Render Component", true) {

			@Override
			public void update(int delta) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				GL11.glBegin(GL11.GL_LINES);
				{
					GL11.glVertex2f(position.x, position.y);
					GL11.glVertex2f(0.0f, 0.0f);
				}
				GL11.glEnd();
			}

			@Override
			public void destroy() {
			}
		};

		// ----- Add Components -----

		this.addComponent(movementComponent);
		this.addComponent(renderComponent);
	}
}
