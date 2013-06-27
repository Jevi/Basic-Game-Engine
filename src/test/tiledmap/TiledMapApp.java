package test.tiledmap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import core.AppCatalyst;
import core.AppContainerConfig;
import core.AppContext;
import core.StateBasedApp;

public class TiledMapApp extends StateBasedApp {

	public TiledMapApp(String title) {
		super(title);
	}

	@Override
	public void initResources() {
		AppContext.textureManager.register("res/img/spritesheets/GymsTileset.png");
		AppContext.textureManager.register("res/fonts/Consolas.png");
	}

	@Override
	public void initStates() {
		addState(new TiledMapState(0));
	}

	@Override
	public void update(int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			AppContext.camera.translate(AppContext.camera.getX(), AppContext.camera.getY() + (0.1f * delta));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			AppContext.camera.translate(AppContext.camera.getX(), AppContext.camera.getY() - (0.1f * delta));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			AppContext.camera.translate(AppContext.camera.getX() - (0.1f * delta), AppContext.camera.getY());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			AppContext.camera.translate(AppContext.camera.getX() + (0.1f * delta), AppContext.camera.getY());
		}

		super.update(delta);
	}

	@Override
	public void input() {
		if (!Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_F11) {
				if (!Display.isFullscreen()) {
					appContainer.setDisplayMode(appContainer.getWidth(), appContainer.getHeight(), true);
				}
				else {
					appContainer.setDisplayMode(appContainer.getWidth(), appContainer.getHeight(), false);
				}
			}
		}
		super.input();
	}

	public static void main(String[] args) throws LWJGLException {
		AppContainerConfig appContainerConfig = new AppContainerConfig();

		AppCatalyst appCatalyst = new AppCatalyst(new TiledMapApp("TiledMapApp"), appContainerConfig);
		appCatalyst.start();
	}
}
