package core;

public abstract class Game {

	private String title;
	protected GameContainer gameContainer;
	protected GameState gameState;

	public Game(String title) {
		this.title = title;
	}

	public abstract void init(GameContainer gameContainer);

	public abstract void destroy();

	public abstract void update(int delta);

	public String getTitle() {
		return title;
	}
}
