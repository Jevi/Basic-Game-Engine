package core;

public abstract class Game {

	private String title;
	protected GameContainer gameContainer;
	protected GameState gameState;

	public Game(String title) {
		this.title = title;
	}

	public void init(GameContainer gameContainer) {
		this.gameContainer = gameContainer;
	}

	public abstract void destroy();

	public void update(int delta) {
		gameState.update(delta);
		gameState.render();
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "Game [title=" + title + ", gameState=" + gameState + "]";
	}

}
