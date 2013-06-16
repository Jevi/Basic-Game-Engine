package test;

import graphics.SpriteSheet;

import java.awt.Frame;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
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
					font = new SpriteSheet("res/fonts/Consolas.png", new Vector2f(32, 32));
				}
				catch (GLException | IOException e) {
					e.printStackTrace();
				}
			};

			@Override
			public void update(int delta) {
				font.render();
			}

			@Override
			public void destroy() {
				font.destroy();
			}
		});
	}
}
