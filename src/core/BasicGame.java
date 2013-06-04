package core;

import junit.framework.Assert;

public abstract class BasicGame extends Game {

	public BasicGame(String title, GameState gameState) {
		super(title);
		Assert.assertNotNull(gameState);
		this.gameState = gameState;
	}

}
