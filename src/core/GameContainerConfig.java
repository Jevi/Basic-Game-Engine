package core;

public class GameContainerConfig {

	private int width;
	private int height;
	private int sync;
	private boolean isResizable;
	private boolean isFullScreen;

	public GameContainerConfig() {
		width = 800;
		height = 600;
		sync = -1;
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
}
