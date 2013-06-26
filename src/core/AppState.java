package core;

import java.util.HashMap;
import java.util.Map;

import util.Log;
import static util.DebugLevel.*;

import component.Entity;

import junit.framework.Assert;

public abstract class AppState {

	private int id;
	protected AppContainer appContainer;
	protected StateBasedApp app;
	protected Map<String, Entity> idToEntityMap = new HashMap<String, Entity>();

	public AppState(int id) {
		this.id = id;
	}

	public void init(AppContainer gameContainer, StateBasedApp game) {
		Assert.assertNotNull(gameContainer);
		Assert.assertNotNull(game);

		this.appContainer = gameContainer;
		this.app = game;

		for (Entity entity : idToEntityMap.values()) {
			entity.init(this.appContainer, this);
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

	public abstract void input();

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
