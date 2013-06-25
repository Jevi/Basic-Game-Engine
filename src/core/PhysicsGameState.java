package core;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public abstract class PhysicsGameState extends GameState {

	protected World world;
	protected float pixelToMeterRatio;
	private int worldStep = 0;

	public PhysicsGameState(int id, Vec2 gravity, int pixelToMeterRatio) {
		super(id);
		world = new World(gravity);
		this.pixelToMeterRatio = pixelToMeterRatio;
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		worldStep += delta;

		if (worldStep >= 15) {
			world.step(1 / 60f, 8, 3);
			worldStep = 0;
		}
	}

	public World getWorld() {
		return world;
	}

	public float getPixelToMeterRatio() {
		return pixelToMeterRatio;
	}
}
