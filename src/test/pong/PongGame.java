package test.pong;

import org.jbox2d.common.Vec2;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import core.AppCatalyst;
import core.AppContainerConfig;
import core.AppContext;
import core.StateBasedApp;

public class PongGame extends StateBasedApp {

	private static AppCatalyst appCatalyst;

	public PongGame(String title) {
		super(title);
	}

	@Override
	public void initResources() {
		AppContext.textureManager.register("res/fonts/Consolas.png");
		AppContext.textureManager.register("res/img/PongGame/TrollFace.png");

		AppContext.audioManager.register("res/audio/PongGame/BallCollision.wav");
		AppContext.audioManager.register("res/audio/PongGame/Score.wav");
		AppContext.audioManager.register("res/audio/PongGame/Trololol.wav");
	}

	@Override
	public void initStates() {
		addState(new PongStartState(0));
		addState(new PongPlayState(1, new Vec2(), 30));
		addState(new PongEndState(2));
		enterState(0);
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
					appCatalyst.setDisplayMode(appContainer.getWidth(), appContainer.getHeight(), false);
				}
				else {
					appCatalyst.setDisplayMode(appContainer.getWidth(), appContainer.getHeight(), true);
				}
				break;
			}
		}
	}

	public static void main(String[] args) throws LWJGLException {

		AppContainerConfig appContainerConfig = new AppContainerConfig();
		appContainerConfig.setWidth(1280);
		appContainerConfig.setHeight(720);

		appCatalyst = new AppCatalyst(new PongGame("Pong Game"), appContainerConfig);
		appCatalyst.start();
	}

}
