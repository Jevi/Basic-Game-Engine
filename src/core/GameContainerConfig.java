package core;

public class GameContainerConfig {

	// properties
	private int width;
	private int height;
	private int sync;
	private boolean isVSyncEnabled;
	private boolean isResizable;
	private boolean isFullScreen;

	public GameContainerConfig() {
		width = 1280;
		height = 720;
		sync = -1;
		isVSyncEnabled = false;
		isResizable = false;
		isFullScreen = false;
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

	@Override
	public String toString() {
		return "GameContainerConfig [width=" + width + ", height=" + height + ", sync=" + sync + ", isVSyncEnabled=" + isVSyncEnabled + ", isResizable=" + isResizable + ", isFullScreen=" + isFullScreen + "]";
	}

}
