package core;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GameRunner extends GameContainer {

	private boolean isCloseRequested = false;

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
		Display.setVSyncEnabled(gameContainerConfig.isVSyncEnabled());
		Display.create();

		glViewport(0, 0, gameContainerConfig.getWidth(), gameContainerConfig.getHeight());
	}

	public void start() throws LWJGLException {
		init();
		while (!isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			checkInput();
			update(getDelta());
		}
		destroy();
	}

	public boolean isCloseRequested() {
		return Display.isCloseRequested() || isCloseRequested;
	}

	protected void checkInput() {
		if (Keyboard.isKeyDown(gameContainerConfig.getEscKey())) {
			isCloseRequested = true;
		}
	}
}
