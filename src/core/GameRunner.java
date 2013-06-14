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
		glClearColor(0.4f, 0.6f, 0.9f, 0f);
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

	protected void checkInput() throws LWJGLException {
		while (Keyboard.next()) {
			if (!Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					isCloseRequested = true;
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_F11) {
					gameContainerConfig.setFullScreen(!gameContainerConfig.isFullScreen());
					if (gameContainerConfig.isFullScreen()) {
						setDisplayMode(Display.getWidth(), Display.getHeight(), true);
					}
					else {
						Display.setFullscreen(false);
						Display.setDisplayMode(previousDisplayMode);
					}
				}
			}
		}
	}

	public boolean setDisplayMode(int width, int height, boolean fullscreen) {

		if (Display.getDisplayMode().getWidth() == width && Display.getDisplayMode().getHeight() == height && Display.isFullscreen() == fullscreen) {
			return true;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] availableDisplayModes = Display.getAvailableDisplayModes();
				int frequency = 0;

				for (DisplayMode displayMode : availableDisplayModes) {
					if (displayMode.getWidth() == width && displayMode.getHeight() == height) {
						if (targetDisplayMode == null && displayMode.getFrequency() >= frequency) {
							targetDisplayMode = displayMode;
							frequency = targetDisplayMode.getFrequency();
						}

						if (displayMode.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel() && displayMode.getFrequency() == Display.getDesktopDisplayMode().getFrequency()) {
							targetDisplayMode = displayMode;
							break;
						}
					}
				}
			}
			else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				System.err.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
				return false;
			}
			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
		}
		catch (LWJGLException e) {
			System.err.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
			return false;
		}
		return true;
	}
}
