package core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

public abstract class StateBasedGame extends Game {

	protected Map<Integer, GameState> idToGameStateMap = new HashMap<Integer, GameState>();

	public StateBasedGame(String title) {
		super(title);

		gameState = new GameState(-1, 0, 0) {
			@Override
			public void render() {
			}
		};
	}

	@Override
	public void init(GameContainer gameContainer) {
		this.gameContainer = gameContainer;
		initGameStates();
	}

	public abstract void initGameStates();

	@Override
	public void destroy() {
		for (Entry<Integer, GameState> idToGameEntry : idToGameStateMap.entrySet()) {
			idToGameEntry.getValue().destroy();
		}
	}

	@Override
	public void update(int delta) {
		gameState.update(delta);
		gameState.render();
	}

	public int getCurrentStateID() {
		return gameState.getID();
	}

	public void addGameState(GameState gameState) {
		Assert.assertNotNull(gameState);

		idToGameStateMap.put(new Integer(gameState.getID()), gameState);
		if (this.gameState == null) {
			this.gameState = gameState;
			this.gameState.init(gameContainer, this);
		}
	}

	public boolean removeGameState(int id) {
		if (id != gameState.getID()) {
			return idToGameStateMap.remove(idToGameStateMap.get(new Integer(id))) != null;
		}
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
		System.out.println("LOADED STATE ID: " + id);
	}
}
