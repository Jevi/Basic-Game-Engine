package test.pong;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import util.Conversion;

import component.Component;
import component.Entity;

import core.util.PhysicsAppState;

public class PongWall extends Entity {

	private Body body;

	private Vec2 dimension;
	private Vec2 position;

	public PongWall(String id, Vec2 dimension, Vec2 position) {
		super(id);

		this.dimension = dimension;
		this.position = position;
	}

	@Override
	protected void createComponents() {

		addComponent(new Component("Physics", false) {

			@Override
			public void init(Entity entity) {
				super.init(entity);

				if (gameState instanceof PhysicsAppState) {
					PhysicsAppState state = (PhysicsAppState) gameState;

					BodyDef bodyDef = new BodyDef();
					bodyDef.position.set(Conversion.PixelsToMeters(position.x, state.getPixelToMeterRatio()), Conversion.PixelsToMeters(position.y, state.getPixelToMeterRatio()));
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
			}

			@Override
			public void destroy() {
				body.getWorld().destroyBody(body);
			}
		});
	}
}
