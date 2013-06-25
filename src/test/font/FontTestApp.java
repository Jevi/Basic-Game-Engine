package test.font;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import core.AppCatalyst;
import core.AppContainer;
import core.AppContainerConfig;
import core.AppContext;
import core.StateBasedApp;

public class FontTestApp extends StateBasedApp {

	private static AppCatalyst appCatalyst;

	public FontTestApp(String title) {
		super(title);
	}

	@Override
	public void init(AppContainer gameContainer) {
		AppContext.textureManager.register("res/fonts/Consolas.png");

		super.init(gameContainer);
	}

	@Override
	public void initGameStates() {
		addGameState(new FontTestState(0));
	}

	@Override
	public void input() {
		super.input();

		if (Keyboard.getEventKeyState()) {
			switch (Keyboard.getEventKey()) {
			case Keyboard.KEY_ESCAPE:
				appCatalyst.stop();
				break;
			case Keyboard.KEY_F11:
				if (Display.isFullscreen()) {
					appCatalyst.setDisplayMode(gameContainer.getWidth(), gameContainer.getHeight(), false);
				}
				else {
					appCatalyst.setDisplayMode(gameContainer.getWidth(), gameContainer.getHeight(), true);
				}
				break;
			}
		}
	}

	public static void main(String[] args) throws LWJGLException {
		appCatalyst = new AppCatalyst(new FontTestApp("Font Test"), new AppContainerConfig());
		appCatalyst.start();
	}
}
