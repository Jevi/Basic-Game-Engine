package core;

import junit.framework.Assert;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public abstract class GameContainer {

	private Game game;
	protected GameContainerConfig gameContainerConfig;
	protected DisplayMode initialDisplayMode;
	protected DisplayMode previousDisplayMode;

	private int fps = 0;
	private long lastFPS = 0;
	private long lastFrame = 0;

	public GameContainer(Game game, GameContainerConfig gameContainerConfig) {
		Assert.assertNotNull(game);
		this.game = game;
		this.gameContainerConfig = gameContainerConfig;
	}

	protected void init() throws LWJGLException {
		game.init(this);
		initialDisplayMode = Display.getDisplayMode();
		previousDisplayMode = Display.getDisplayMode();
	}

	protected void destroy() {
		game.destroy();
		Display.destroy();
	}

	protected void update(int delta) {
		updateFPS();
		game.update(delta);

		Display.update();
		Display.sync(gameContainerConfig.getSync());
	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	protected void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle(game.getTitle() + " - " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public void sync(int fps) {
		gameContainerConfig.setSync(fps);
	}

	public int getFPS() {
		return fps;
	}

	public int getWidth() {
		return gameContainerConfig.getWidth();
	}

	public int getHeight() {
		return gameContainerConfig.getHeight();
	}
}
