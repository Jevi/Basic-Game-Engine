package core;

public abstract class App {

	private String title;
	protected AppContainer gameContainer;
	protected AppState gameState;

	public App(String title) {
		this.title = title;
	}

	public void init(AppContainer gameContainer) {
		this.gameContainer = gameContainer;
	}

	public abstract void destroy();

	public void update(int delta) {
		gameState.update(delta);
		gameState.render();
	}

	public abstract void input();

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "Game [title=" + title + ", gameState=" + gameState + "]";
	}

}
