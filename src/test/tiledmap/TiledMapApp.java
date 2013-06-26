package test.tiledmap;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

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

	public static void main(String[] args) throws LWJGLException {
		AppContainerConfig appContainerConfig = new AppContainerConfig();
		appContainerConfig.setWidth(1280);
		appContainerConfig.setHeight(720);
		appContainerConfig.setResizable(true);

		AppCatalyst appCatalyst = new AppCatalyst(new TiledMapApp("TiledMapApp"), appContainerConfig);
		appCatalyst.start();
	}
}
