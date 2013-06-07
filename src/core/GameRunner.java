package core;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GameRunner extends GameContainer {

	public GameRunner(Game game, GameContainerConfig gameContainerConfig) {
		super(game, gameContainerConfig);
	}

	@Override
	protected void init() throws LWJGLException {
		initGL();
		super.init();
	}

	private void initGL() throws LWJGLException {
		if (gameContainerConfig.isFullScreen()) {
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			for (DisplayMode displayMode : modes) {
				System.out.println(String.format("%s, %s", displayMode.getWidth(), displayMode.getHeight()));
				if (displayMode.getWidth() == gameContainerConfig.getWidth() && displayMode.getHeight() == gameContainerConfig.getHeight() && displayMode.isFullscreenCapable()) {
					Display.setDisplayMode(displayMode);
					break;
				}
			}
		}
		else {
			Display.setDisplayMode(new DisplayMode(gameContainerConfig.getWidth(), gameContainerConfig.getHeight()));
		}

		Display.setResizable(gameContainerConfig.isResizable());
		Display.setFullscreen(gameContainerConfig.isFullScreen());
		Display.create();

		glViewport(0, 0, gameContainerConfig.getWidth(), gameContainerConfig.getWidth());
	}

	public void start() throws LWJGLException {
		init();
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			update(getDelta());
		}
		destroy();
	}
}
