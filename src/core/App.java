package core;

public abstract class App {

	private String title;
	protected AppContainer appContainer;
	protected AppState appState;

	public App(String title) {
		this.title = title;
	}

	public void init(AppContainer gameContainer) {
		this.appContainer = gameContainer;
	}

	public abstract void destroy();

	public void update(int delta) {
		appState.update(delta);
		appState.render();
	}

	public abstract void input();

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "App [title=" + title + "]";
	}

}
