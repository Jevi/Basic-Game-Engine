package core;

import junit.framework.Assert;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import util.Log;
import static util.DebugLevel.*;

public abstract class GameContainer {

	private Game game;
	protected GameContainerConfig gameContainerConfig;
	protected DisplayMode initialDisplayMode;
	protected DisplayMode previousDisplayMode;

	private int fps = 0;
	private long lastFPS = 0;
	private long lastFrame = 0;

	private final int SECOND = 1000;

	public GameContainer(Game game, GameContainerConfig gameContainerConfig) {
		Assert.assertNotNull(game);
		this.game = game;
		this.gameContainerConfig = gameContainerConfig;

		Log.println(LOW_DEBUG, game.toString());
		Log.println(LOW_DEBUG, gameContainerConfig.toString());
	}

	protected void init() throws LWJGLException {
		game.init(this);
		initialDisplayMode = Display.getDisplayMode();
		previousDisplayMode = Display.getDisplayMode();

		Log.println(LOW_DEBUG, toString() + " Initialization Complete");
	}

	protected void destroy() {
		game.destroy();

		Log.println(LOW_DEBUG, toString() + " Destruction Complete");
	}

	protected void update(int delta) {
		game.update(delta);
		Display.sync(gameContainerConfig.getSync());
		Display.update();
		updateFPS();
	}

	public long getTime() {
		return (Sys.getTime() * SECOND) / Sys.getTimerResolution();
	}

	protected void updateFPS() {
		if (getTime() - lastFPS > SECOND) {
			Display.setTitle(game.getTitle() + " - " + fps);
			fps = 0;
			lastFPS += SECOND;
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

	@Override
	public String toString() {
		return "GameContainer [game=" + game + ", gameContainerConfig=" + gameContainerConfig + "]";
	}

}
