package test.pong;

import org.jbox2d.common.Vec2;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import core.AppCatalyst;
import core.AppContainer;
import core.AppContainerConfig;
import core.AppContext;
import core.StateBasedApp;

public class PongGame extends StateBasedApp {

	private static AppCatalyst appCatalyst;

	public PongGame(String title) {
		super(title);
	}

	@Override
	public void initGameStates() {
		addGameState(new PongStartState(0));
		addGameState(new PongPlayState(1, new Vec2(), 30));
		addGameState(new PongEndState(2));
		enterGameState(0);
	}

	@Override
	public void init(AppContainer gameContainer) {

		AppContext.textureManager.register("res/fonts/Consolas.png");
		AppContext.textureManager.register("res/img/PongGame/TrollFace.png");

		AppContext.audioManager.register("res/audio/PongGame/BallCollision.wav");
		AppContext.audioManager.register("res/audio/PongGame/Score.wav");
		AppContext.audioManager.register("res/audio/PongGame/Trololol.wav");

		super.init(gameContainer);
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

		AppContainerConfig gameContainerConfig = new AppContainerConfig();
		gameContainerConfig.setWidth(1280);
		gameContainerConfig.setHeight(720);

		appCatalyst = new AppCatalyst(new PongGame("Pong Game"), gameContainerConfig);
		appCatalyst.start();
	}

}
