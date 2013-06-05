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
		GameState gameState = new StateOne(0, 0, -9.8f);
		GameState gameState2 = new StateTwo(1, 0, -9.8f);
		this.addGameState(gameState);
		this.addGameState(gameState2);
		this.enterGameState(gameState.getID());
	}

	public static void main(String[] args) throws LWJGLException {
		GameContainerEngine gameEngine = new GameContainerEngine(new StateBasedGameImpl("Test Game"), 640, 480);
		gameEngine.start();
	}
}
