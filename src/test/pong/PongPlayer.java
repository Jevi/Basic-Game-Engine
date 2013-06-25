package test.pong;

import static org.lwjgl.opengl.GL11.*;

import java.util.Arrays;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.input.Keyboard;

import util.Conversion;

import component.Component;
import component.Entity;

import core.util.PhysicsAppState;

public class PongPlayer extends Entity {

	private Body body;
	private float pixelToMeterRatio;

	private Vec2 dimension;
	private Vec2 position;
	private Vec2 startingPosition;
	private int[] keys = new int[2];

	private final float speed = 0.5f;

	public PongPlayer(String id, Vec2 dimension, Vec2 position, int[] keys) {
		super(id);

		this.dimension = dimension;
		this.position = position;
		this.keys = Arrays.copyOf(keys, this.keys.length);
	}

	@Override
	protected void createComponents() {

		addComponent(new Component("Physics", true) {

			@Override
			public void init(Entity entity) {
				super.init(entity);

				if (gameState instanceof PhysicsAppState) {
					PhysicsAppState state = (PhysicsAppState) gameState;

					pixelToMeterRatio = state.getPixelToMeterRatio();
					BodyDef bodyDef = new BodyDef();

					startingPosition = new Vec2(Conversion.PixelsToMeters(position.x, state.getPixelToMeterRatio()), Conversion.PixelsToMeters(position.y, state.getPixelToMeterRatio()));
					bodyDef.position.set(startingPosition.x, startingPosition.y);
					bodyDef.type = BodyType.STATIC;
					bodyDef.userData = getEntity().getId();

					PolygonShape polygonShape = new PolygonShape();
					polygonShape.setAsBox(Conversion.PixelsToMeters(dimension.x, state.getPixelToMeterRatio()), Conversion.PixelsToMeters(dimension.y, state.getPixelToMeterRatio()));

					body = state.getWorld().createBody(bodyDef);

					FixtureDef fixtureDef = new FixtureDef();
					fixtureDef.density = 1.0f;
					fixtureDef.shape = polygonShape;
					fixtureDef.friction = 0;
					body.createFixture(fixtureDef);
				}
			}

			@Override
			public void update(int delta) {
				input(delta);
			}

			private void input(int delta) {
				if (Keyboard.isKeyDown(keys[0]) && Conversion.MetersToPixels(body.getPosition(), pixelToMeterRatio).y < gameContainer.getHeight() - dimension.y) {
					body.setTransform(body.getPosition().add(new Vec2(0, Conversion.PixelsToMeters(speed * delta, pixelToMeterRatio))), 0);
				}
				else if (Keyboard.isKeyDown(keys[1]) && Conversion.MetersToPixels(body.getPosition(), pixelToMeterRatio).y > 0 + dimension.y) {
					body.setTransform(body.getPosition().sub(new Vec2(0, Conversion.PixelsToMeters(speed * delta, pixelToMeterRatio))), 0);
				}

			}

			@Override
			public void destroy() {
				body.getWorld().destroyBody(body);
			}
		});

		addComponent(new Component("Render", true) {

			public void init(Entity entity) {
				super.init(entity);
			};

			@Override
			public void update(int delta) {
				updatePosition();
				render();
			}

			@Override
			public void destroy() {

			}

			private void updatePosition() {
				position = Conversion.MetersToPixels(body.getPosition(), pixelToMeterRatio);
			}

			private void render() {
				glBegin(GL_QUADS);
				glVertex2f(position.x - dimension.x, position.y + dimension.y);
				glVertex2f(position.x + dimension.x, position.y + dimension.y);
				glVertex2f(position.x + dimension.x, position.y - dimension.y);
				glVertex2f(position.x - dimension.x, position.y - dimension.y);
				glEnd();
			}
		});
	}

	public void reset() {
		body.setTransform(startingPosition, 0);
	}

	public Body getBody() {
		return body;
	}
}
