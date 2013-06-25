package test.pong;

import static org.lwjgl.opengl.GL11.*;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import util.Conversion;

import component.Component;
import component.Entity;

import core.AppContainer;
import core.AppState;
import core.util.PhysicsAppState;

public class PongBall extends Entity {

	private Body body;
	private float pixelToMeterRatio;
	private final int radius = 2;
	private Vec2 startingPosition;

	public PongBall(String id) {
		super(id);
	}

	@Override
	public void init(AppContainer gameContainer, AppState gameState) {
		super.init(gameContainer, gameState);
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
					startingPosition = new Vec2(Conversion.PixelsToMeters(gameContainer.getWidth(), pixelToMeterRatio) / 2.0f, Conversion.PixelsToMeters(gameContainer.getHeight(), pixelToMeterRatio) / 2.0f);

					BodyDef bodyDef = new BodyDef();
					bodyDef.position.set(startingPosition);
					bodyDef.type = BodyType.DYNAMIC;
					bodyDef.bullet = true;
					bodyDef.userData = getEntity().getId();

					CircleShape circleShape = new CircleShape();
					circleShape.setRadius(Conversion.PixelsToMeters(radius, pixelToMeterRatio));

					body = state.getWorld().createBody(bodyDef);

					FixtureDef fixtureDef = new FixtureDef();
					fixtureDef.density = 0.1f;
					fixtureDef.shape = circleShape;
					fixtureDef.restitution = 1.1f;
					fixtureDef.friction = 0f;
					body.createFixture(fixtureDef);
				}
			}

			@Override
			public void update(int delta) {

			}

			@Override
			public void destroy() {
				body.m_world.destroyBody(body);
			}
		});

		addComponent(new Component("Render", true) {

			private Vec2 position;

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
				glVertex2f(position.x - radius, position.y + radius);
				glVertex2f(position.x + radius, position.y + radius);
				glVertex2f(position.x + radius, position.y - radius);
				glVertex2f(position.x - radius, position.y - radius);
				glEnd();
			}
		});
	}

	public void reset() {
		body.setLinearVelocity(new Vec2(0, 0));
		body.setAngularVelocity(0);
		body.setTransform(startingPosition, 0);
	}

	public Body getBody() {
		return body;
	}
}
