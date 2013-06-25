package test.PongGame;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

import java.io.IOException;

import org.jbox2d.common.Vec2;
import org.lwjgl.LWJGLException;

import core.AudioManager;
import core.GameContainer;
import core.GameContainerConfig;
import core.GameRunner;
import core.StateBasedGame;
import core.TextureManager;

public class PongGame extends StateBasedGame {

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
	public void init(GameContainer gameContainer) {

		try {
			TextureManager.loadTexture("res/fonts/Consolas.png", GL_TEXTURE0);
			TextureManager.loadTexture("res/img/PongGame/TrollFace.png", GL_TEXTURE0);

			AudioManager.loadAudio("res/audio/PongGame/BallCollision.wav");
			AudioManager.loadAudio("res/audio/PongGame/Score.wav");
			AudioManager.loadAudio("res/audio/PongGame/Trololol.wav");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		super.init(gameContainer);
	}

	public static void main(String[] args) throws LWJGLException {

		GameContainerConfig containerConfig = new GameContainerConfig();
		containerConfig.setWidth(1280);
		containerConfig.setHeight(720);

		GameRunner gameRunner = new GameRunner(new PongGame("Pong Game"), containerConfig);
		gameRunner.start();
	}

}
