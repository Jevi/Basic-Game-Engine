package test;

import graphics.SpriteSheet;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import util.GLException;

import component.Component;
import component.Entity;

public class EntityOne extends Entity {

	private Vector2f position = new Vector2f(Display.getWidth() / 2, Display.getHeight() / 2);

	public EntityOne(String id) {
		super(id);
	}

	@Override
	protected void createComponents() {

		addComponent(new Component("Movement", true) {
			@Override
			public void update(int delta) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					position.x -= 1 * delta;
				}
				else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					position.x += 1 * delta;
				}

				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					position.y += 1 * delta;
				}
				else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					position.y -= 1 * delta;
				}
			}

			@Override
			public void destroy() {
			}
		});

		addComponent(new Component("Render", true) {

			SpriteSheet font;

			public void init(Entity entity) {
				super.init(entity);

				try {
					font = new SpriteSheet("res/img/spritesheet.png", new Vector2f(100, 68), position, new Vector2f(48.333333333333f, 68));
				}
				catch (GLException | IOException e) {
					e.printStackTrace();
				}
			};

			int deltaTime = 0;

			@Override
			public void update(int delta) {
				if (deltaTime < 300) {
					deltaTime += delta;
				}
				else {
					font.setFrame(font.getFrame() + 1);
					deltaTime = 0;
				}
				font.render();
			}

			@Override
			public void destroy() {
				font.destroy();
			}
		});
	}
}
