package core;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public abstract class GameState {

	private int id;
	protected World world;
	protected GameContainer gameContainer;
	protected StateBasedGame stateBasedGame;
	protected Set<Entity> entities = new HashSet<Entity>();

	public GameState(int id, float ygravity, float xgravity) {
		this.id = id;
		world = new World(new Vec2(ygravity, xgravity));
	}

	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
		Assert.assertNotNull(gameContainer);
		Assert.assertNotNull(stateBasedGame);

		this.gameContainer = gameContainer;
		this.stateBasedGame = stateBasedGame;

		for (Entity entity : entities) {
			entity.init(this.gameContainer);
		}
	}

	public void destroy() {
		for (Entity entity : entities) {
			entity.destroy();
		}
	}

	public void update(int delta) {
		world.step(1.0f / gameContainer.getFPS(), 8, 3);

		for (Entity entity : entities) {
			entity.update(delta);
		}
	}

	public abstract void render();

	public boolean addEntity(Entity entity) {
		Assert.assertNotNull(entity);
		return entities.add(entity);
	}

	public int getID() {
		return id;
	}
}
