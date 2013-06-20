package test;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import graphics.Sprite;
import graphics.TexturedVertex;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import org.lwjgl.util.vector.Vector2f;

import util.Conversion;

import component.Component;
import component.Entity;

import core.GameContainer;
import core.GameState;

public class EntityTwo extends Entity {

	private Body body;
	private final float hx = 200;
	private final float hy = 10;

	public EntityTwo(String id) {
		super(id);
	}

	@Override
	public void init(GameContainer gameContainer, GameState gameState) {
		super.init(gameContainer, gameState);
	}

	@Override
	protected void createComponents() {

		addComponent(new Component("Physics", true) {

			@Override
			public void init(Entity entity) {
				super.init(entity);

				if (gameState instanceof StateOne) {
					StateOne state = (StateOne) gameState;

					BodyDef bodyDef = new BodyDef();
					bodyDef.position.set(Conversion.PixelsToMeters(gameContainer.getWidth(), state.pixelToMeterRatio) / 2.0f, Conversion.PixelsToMeters(gameContainer.getHeight(), state.pixelToMeterRatio) / 2.0f);
					bodyDef.type = BodyType.KINEMATIC;
					bodyDef.allowSleep = true;
					bodyDef.angle = (float) Math.toRadians(25);

					PolygonShape polygonShape = new PolygonShape();
					polygonShape.setAsBox(Conversion.PixelsToMeters(hx, state.pixelToMeterRatio), Conversion.PixelsToMeters(hy, state.pixelToMeterRatio));

					body = state.getWorld().createBody(bodyDef);

					FixtureDef fixtureDef = new FixtureDef();
					fixtureDef.density = 1.0f;
					fixtureDef.restitution = 0.3f;
					fixtureDef.shape = polygonShape;
					body.createFixture(fixtureDef);
				}
			}

			@Override
			public void update(int delta) {

			}

			@Override
			public void destroy() {
			}
		});

		addComponent(new Component("Render", true) {

			private Sprite sprite;
			private TexturedVertex[] vertices;
			private Vec2 position;

			public void init(Entity entity) {
				super.init(entity);

				vertices = new TexturedVertex[4];
				for (int i = 0; i < vertices.length; i++) {
					vertices[i] = new TexturedVertex();
				}

				updatePosition();

				try {
					sprite = new Sprite("res/img/book.png", new Vector2f(position.x, position.y), new Vector2f(hx, hy));
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			};

			@Override
			public void update(int delta) {
				updatePosition();
				sprite.setPosition(new Vector2f(position.x, position.y));

				glPushMatrix();
				glTranslatef(position.x, position.y, 0);
				glRotated(Math.toDegrees(body.getAngle()), 0, 0, 1);
				glTranslatef(-position.x, -position.y, 0);
				sprite.render();
				glPopMatrix();
			}

			@Override
			public void destroy() {
				sprite.destroy();
			}

			private void updatePosition() {
				if (gameState instanceof StateOne) {
					StateOne state = (StateOne) gameState;
					position = body.getPosition().mul(state.pixelToMeterRatio);
				}
			}
		});
	}
}
