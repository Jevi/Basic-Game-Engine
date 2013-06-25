package core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import util.Log;
import static util.DebugLevel.*;

import junit.framework.Assert;

public abstract class StateBasedApp extends App {

	protected Map<Integer, AppState> idToGameStateMap = new HashMap<Integer, AppState>();
	public static final int reservedId = 1337;

	public StateBasedApp(String title) {
		super(title);

		gameState = new AppState(reservedId) {
			@Override
			public void render() {
			}

			@Override
			public void input() {
			}
		};
	}

	@Override
	public void init(AppContainer gameContainer) {
		this.gameContainer = gameContainer;
		initGameStates();

		Log.println(LOW_DEBUG, toString() + " Initialization Complete");
	}

	public abstract void initGameStates();

	@Override
	public void destroy() {
		for (Entry<Integer, AppState> idToGameEntry : idToGameStateMap.entrySet()) {
			idToGameEntry.getValue().destroy();
		}

		Log.println(LOW_DEBUG, toString() + " Destruction Complete");
	}

	@Override
	public void update(int delta) {
		gameState.update(delta);
		gameState.render();
	}

	@Override
	public void input() {
		gameState.input();
	}

	public int getCurrentStateID() {
		return gameState.getID();
	}

	public void addGameState(AppState gameState) {
		Assert.assertNotNull(gameState);

		idToGameStateMap.put(new Integer(gameState.getID()), gameState);
		if (this.gameState.getID() == reservedId && gameState.getID() != reservedId) {
			this.gameState = gameState;
			this.gameState.init(gameContainer, this);
		}
		Log.println(LOW_DEBUG, gameState.toString() + " added successfully");
	}

	public boolean removeGameState(int id) {
		AppState gameState = idToGameStateMap.get(new Integer(id));

		if (id != gameState.getID() && gameState != null) {
			idToGameStateMap.remove(gameState);
			Log.println(LOW_DEBUG, gameState.toString() + " removed successfully");
			return true;
		}

		Log.println(LOW_DEBUG, gameState.toString() + " could not be removed");
		return false;
	}

	public boolean enterGameState(int id) {
		if (idToGameStateMap.containsKey(new Integer(id)) && id != gameState.getID()) {
			switchGameState(id);
			return true;
		}
		return false;
	}

	public boolean enterNextGameState() {
		int nextState = gameState.getID() + 1;
		if (idToGameStateMap.containsKey(new Integer(nextState))) {
			switchGameState(nextState);
			return true;
		}
		return false;
	}

	public boolean enterPreviousGameState() {
		int nextState = gameState.getID() - 1;
		if (idToGameStateMap.containsKey(new Integer(nextState))) {
			switchGameState(nextState);
			return true;
		}
		return false;
	}

	private void switchGameState(int id) {
		gameState.destroy();
		gameState = idToGameStateMap.get(id);
		gameState.init(gameContainer, this);

		Log.println(LOW_DEBUG, gameState.toString() + " was switched to successfully");
	}

}
