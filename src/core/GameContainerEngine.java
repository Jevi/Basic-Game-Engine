package core;

import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GameContainerEngine extends GameContainer {

	public GameContainerEngine(Game game, int width, int height) {
		super(game, width, height);
	}

	@Override
	public void init() throws LWJGLException {
		initGL();
		super.init();
	}

	private void initGL() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(getWidth(), getHeight()));
		Display.create();

		glViewport(0, 0, getWidth(), getHeight());
	}

	public void start() throws LWJGLException {
		init();
		while (!Display.isCloseRequested()) {
			update(getDelta());
		}
		destroy();
	}
}
