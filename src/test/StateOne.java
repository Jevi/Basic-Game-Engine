package test;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import core.GameContainer;
import core.GameState;
import core.StateBasedGame;

public class StateOne extends GameState {

	private World world = new World(new Vec2(0, -9.8f));
	public final float scale = 30.0f;

	public StateOne(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
		addEntity(new EntityOne("one"));
		addEntity(new EntityTwo("two"));
		super.init(gameContainer, stateBasedGame);
	}

	@Override
	public void update(int delta) {
		world.step(1.0f / 60.0f, 5, 5);
		super.update(delta);
	}

	@Override
	public void render() {

	}

	@Override
	public void destroy() {
		super.destroy();
	}

	public World getWorld() {
		return world;
	}
}
