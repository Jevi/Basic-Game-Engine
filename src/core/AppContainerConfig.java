package core;

import org.lwjgl.util.Color;

public class AppContainerConfig {

	// properties
	private int width;
	private int height;
	private int sync;
	private boolean isVSyncEnabled;
	private boolean isResizable;
	private boolean isFullScreen;
	private String lwjglLibraryPath;
	private Color backgroundColor;

	public AppContainerConfig() {
		width = 800;
		height = 600;
		sync = -1;
		isVSyncEnabled = false;
		isResizable = false;
		isFullScreen = false;
		lwjglLibraryPath = "lib/native/windows";
		backgroundColor = new Color(Color.BLACK);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSync() {
		return sync;
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

	public String getLwjglLibraryPath() {
		return lwjglLibraryPath;
	}

	public void setLwjglLibraryPath(String lwjglLibraryPath) {
		this.lwjglLibraryPath = lwjglLibraryPath;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
