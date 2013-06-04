package test;

import org.lwjgl.LWJGLException;

import core.GameContainerEngine;
import core.GameState;
import core.StateBasedGame;

public class StateBasedGameImpl extends StateBasedGame {

	public StateBasedGameImpl(String title) {
		super(title);
	}

	@Override
	public void initGameStates() {
		GameState gameState = new GameStateImpl(0, 0, -9.8f);
		this.addGameState(gameState);
		this.enterGameState(gameState.getID());
	}

	public static void main(String[] args) throws LWJGLException {
		GameContainerEngine gameEngine = new GameContainerEngine(new StateBasedGameImpl("Test Game"), 640, 480);
		gameEngine.start();
	}
}
