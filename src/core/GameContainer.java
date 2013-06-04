package core;

import junit.framework.Assert;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public abstract class GameContainer {

	private Game game;
	private int width;
	private int height;
	private int fps = 0;
	private long lastFPS = 0;
	private long lastFrame = 0;
	private int sync = -1;

	public GameContainer(Game game, int width, int height) {
		Assert.assertNotNull(game);
		Assert.assertTrue(width > 0);
		Assert.assertTrue(height > 0);

		this.game = game;
		this.width = width;
		this.height = height;
	}

	protected void init() throws LWJGLException {
		game.init(this);
	}

	protected void destroy() {
		game.destroy();
		Display.destroy();
	}

	protected void update(int delta) {
		updateFPS();
		game.update(delta);
		Display.update();
		Display.sync(sync);
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
		sync = fps;
	}

	public int getFPS() {
		return fps;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
