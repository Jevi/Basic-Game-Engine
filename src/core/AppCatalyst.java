package core;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class AppCatalyst extends AppContainer {

	private boolean isCloseRequested = false;

	public AppCatalyst(App game, AppContainerConfig gameContainerConfig) {
		super(game, gameContainerConfig);
	}

	@Override
	protected void init() throws LWJGLException {
		initLibraries();
		super.init();
	}

	private void initLibraries() throws LWJGLException {

		System.setProperty("org.lwjgl.librarypath", new File(lwjglLibraryPath).getAbsolutePath());

		if (isFullScreen) {
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			for (DisplayMode displayMode : modes) {
				if (displayMode.getWidth() == width && displayMode.getHeight() == height && displayMode.isFullscreenCapable()) {
					Display.setDisplayMode(displayMode);
					break;
				}
			}
		}
		else {
			Display.setDisplayMode(new DisplayMode(width, height));
		}

		Display.setResizable(isResizable);
		Display.setFullscreen(isFullScreen);
		Display.setVSyncEnabled(isVSyncEnabled);
		Display.create();
		AL.create();

		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, 0, height, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glClearColor(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), backgroundColor.getAlpha());
	}

	public void stop() {
		isCloseRequested = true;
	}

	@Override
	protected void destroy() {
		AppContext.textureManager.dropAll();
		AppContext.audioManager.dropAll();
		Display.destroy();
		AL.destroy();

		super.destroy();
	}

	public void start() throws LWJGLException {
		init();
		while (!isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			input();
			update(getDelta());
		}
		destroy();
	}

	private boolean isCloseRequested() {
		return Display.isCloseRequested() || isCloseRequested;
	}

}
