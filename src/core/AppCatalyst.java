package core;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

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

		setDisplayMode(width, height, isFullScreen);

		Display.setResizable(isResizable);
		Display.setFullscreen(isFullScreen);
		Display.setVSyncEnabled(isVSyncEnabled);
		Display.create();
		AL.create();

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, 0, height, 2, -2);
		glMatrixMode(GL_MODELVIEW);

		glClearColor(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), backgroundColor.getAlpha());

		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

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

	@Override
	protected void input() {
		while (Keyboard.next()) {
			if (!Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					isCloseRequested = true;
				}
			}
			app.input();
		}
	}

	public void start() throws LWJGLException {
		init();
		while (!isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			input();
			update(getDelta());
		}
		destroy();
	}

	private boolean isCloseRequested() {
		return Display.isCloseRequested() || isCloseRequested;
	}

}
