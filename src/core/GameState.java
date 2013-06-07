package core;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

public abstract class GameState {

	private int id;
	protected GameContainer gameContainer;
	protected StateBasedGame stateBasedGame;
	protected Set<Entity> entities = new HashSet<Entity>();

	public GameState(int id) {
		this.id = id;
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
