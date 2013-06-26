package core;

import static org.lwjgl.opengl.GL11.*;
import static util.DebugLevel.LOW_DEBUG;
import junit.framework.Assert;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import util.Log;

public abstract class AppContainer {

	protected App app;
	protected DisplayMode initialDisplayMode;
	protected DisplayMode previousDisplayMode;

	protected int width;
	protected int height;
	protected int sync;
	protected boolean isVSyncEnabled;
	protected boolean isResizable;
	protected boolean isFullScreen;
	protected String lwjglLibraryPath;
	protected Color backgroundColor;

	private int fps = 0;
	private int recordedFPS = 0;
	private long lastFPS = 0;
	private long lastFrame = 0;

	private final int SECOND = 1000;

	public AppContainer(App app, AppContainerConfig appContainerConfig) {
		Assert.assertNotNull(app);
		Assert.assertNotNull(appContainerConfig);
		this.app = app;

		width = appContainerConfig.getWidth();
		height = appContainerConfig.getHeight();
		sync = appContainerConfig.getSync();
		isVSyncEnabled = appContainerConfig.isVSyncEnabled();
		isResizable = appContainerConfig.isResizable();
		isFullScreen = appContainerConfig.isFullScreen();
		lwjglLibraryPath = appContainerConfig.getLwjglLibraryPath();
		backgroundColor = appContainerConfig.getBackgroundColor();

		Log.println(LOW_DEBUG, app.toString());
	}

	protected void init() throws LWJGLException {
		app.init(this);
		initialDisplayMode = Display.getDisplayMode();
		previousDisplayMode = Display.getDisplayMode();

		Log.println(LOW_DEBUG, toString() + " Initialization Complete");
	}

	protected void destroy() {
		app.destroy();

		Log.println(LOW_DEBUG, toString() + " Destruction Complete");
	}

	protected void update(int delta) {
		app.update(delta);
		Display.sync(sync);
		Display.update();
		resize();
		updateFPS();
	}

	protected void resize() {
		if (Display.wasResized()) {
			width = Display.getWidth();
			height = Display.getHeight();

			glViewport(0, 0, width, height);
			glMatrixMode(GL_PROJECTION);
			glLoadIdentity();
			glOrtho(0, width, 0, height, 1, -1);
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();

			AppContext.camera.translate(AppContext.camera.getX(), AppContext.camera.getY());
		}
	}

	protected abstract void input();

	public long getTime() {
		return (Sys.getTime() * SECOND) / Sys.getTimerResolution();
	}

	protected void updateFPS() {
		if (getTime() - lastFPS > SECOND) {
			Display.setTitle(app.getTitle() + " - " + recordedFPS);
			lastFPS = getTime();
			recordedFPS = fps;
			fps = 0;
		}
		fps++;
	}

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSync() {
		return sync;
	}

	public int getFPS() {
		return recordedFPS;
	}

	public void setSync(int sync) {
		this.sync = sync;
	}

	public boolean isVSyncEnabled() {
		return isVSyncEnabled;
	}

	public void setVSyncEnabled(boolean isVSyncEnabled) {
		this.isVSyncEnabled = isVSyncEnabled;
	}

	public boolean isResizable() {
		return isResizable;
	}

	public void setResizable(boolean isResizable) {
		this.isResizable = isResizable;
	}

	public boolean isFullScreen() {
		return isFullScreen;
	}

	public void setFullScreen(boolean isFullScreen) {
		this.isFullScreen = isFullScreen;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(ReadableColor color) {
		this.backgroundColor = new Color(color);
		glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
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

	@Override
	public String toString() {
		return "AppContainer [app=" + app + ", initialDisplayMode=" + initialDisplayMode + ", previousDisplayMode=" + previousDisplayMode + ", width=" + width + ", height=" + height + ", sync="
				+ sync + ", isVSyncEnabled=" + isVSyncEnabled + ", isResizable=" + isResizable + ", isFullScreen=" + isFullScreen + ", lwjglLibraryPath=" + lwjglLibraryPath + ", backgroundColor="
				+ backgroundColor + "]";
	}

}
