package core;

import java.util.HashMap;
import java.util.Map;

import util.Log;
import static util.DebugLevel.*;

import component.Entity;

import junit.framework.Assert;

public abstract class GameState {

	private int id;
	protected GameContainer gameContainer;
	protected StateBasedGame game;
	protected Map<String, Entity> idToEntityMap = new HashMap<String, Entity>();

	public GameState(int id) {
		this.id = id;
	}

	public void init(GameContainer gameContainer, StateBasedGame game) {
		Assert.assertNotNull(gameContainer);
		Assert.assertNotNull(game);

		this.gameContainer = gameContainer;
		this.game = game;

		for (Entity entity : idToEntityMap.values()) {
			entity.init(this.gameContainer, this);
		}

		Log.println(LOW_DEBUG, toString() + " Initialization Complete");
	}

	public void destroy() {
		for (Entity entity : idToEntityMap.values()) {
			entity.destroy();
		}

		Log.println(LOW_DEBUG, toString() + " Desctruction Complete");
	}

	public void update(int delta) {
		for (Entity entity : idToEntityMap.values()) {
			entity.update(delta);
		}
	}

	public abstract void render();

	public void addEntity(Entity entity) {
		Assert.assertNotNull(entity);
		idToEntityMap.put(entity.getId(), entity);
	}

	public Entity getEntity(String id) {
		return idToEntityMap.get(id);
	}

	public int getID() {
		return id;
	}

	@Override
	public String toString() {
		return "GameState [id=" + id + "]";
	}

}
