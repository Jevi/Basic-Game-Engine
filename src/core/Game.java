package core;

import junit.framework.Assert;

public abstract class Game {

	private String title;
	protected GameContainer gameContainer;
	protected GameState gameState;

	public Game(String title) {
		this.title = title;
	}

	public void init(GameContainer gameContainer) {
		Assert.assertNotNull(gameContainer);

		this.gameContainer = gameContainer;
	}

	public abstract void destroy();

	public abstract void update(int delta);

	public String getTitle() {
		return title;
	}
}
