package test;

import graphics.Image;

import java.io.IOException;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;

import util.GLException;

import component.Component;

import core.Entity;

public class EntityOne extends Entity {

	private Vec2 position = new Vec2(0.0f, 0.0f);

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

			Image smile;

			public void init(Entity entity) {
				super.init(entity);

				try {
					smile = new Image("res/img/smile.png", new Vec2(90, 90), 100, 100);
					smile.load();
				}
				catch (GLException | IOException e) {
					e.printStackTrace();
				}
			};

			@Override
			public void update(int delta) {
				System.out.println(String.format("Window Position (%s, %s)", position.x, position.y));
				smile.setPosition(position);
				smile.render();
			}

			@Override
			public void destroy() {
				smile.destroy();
			}
		});
	}
}
