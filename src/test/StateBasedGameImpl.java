package test;

import org.lwjgl.LWJGLException;

import core.GameContainerConfig;
import core.GameRunner;
import core.GameState;
import core.StateBasedGame;

public class StateBasedGameImpl extends StateBasedGame {

	public StateBasedGameImpl(String title) {
		super(title);
	}

	@Override
	public void initGameStates() {
		GameState gameState = new StateOne(0);
		this.addGameState(gameState);
		this.enterGameState(gameState.getID());
	}

	public static void main(String[] args) throws LWJGLException {
		GameContainerConfig config = new GameContainerConfig();
		config.setResizable(true);

		GameRunner gameEngine = new GameRunner(new StateBasedGameImpl("Test Game"), config);
		gameEngine.start();
	}
}
