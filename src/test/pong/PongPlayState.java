package test.pong;

import static org.lwjgl.opengl.GL11.*;
import gfx.BitmapFont;

import java.io.IOException;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import util.Conversion;
import core.AppContainer;
import core.AppContext;
import core.StateBasedApp;
import core.util.PhysicsAppState;

public class PongPlayState extends PhysicsAppState {

	private final int scoreLimit = 5;

	private int playerOneScore = 0;
	private int playerTwoScore = 0;
	private boolean playerOneScoredLast = false;

	private BitmapFont scoreBoard;

	public PongPlayState(int id, Vec2 gravity, int pixelToMeterRatio) {
		super(id, gravity, pixelToMeterRatio);
	}

	@Override
	public void init(AppContainer appContainer, StateBasedApp app) {
		addEntity(new PongWall("TopWall", new Vec2(appContainer.getWidth() / 2, 1), new Vec2(appContainer.getWidth() / 2, appContainer.getHeight() + 1)));
		addEntity(new PongWall("BottomWall", new Vec2(appContainer.getWidth() / 2, 1), new Vec2(appContainer.getWidth() / 2, 0 - 1)));

		addEntity(new PongPlayer("Player1", new Vec2(2, 50), new Vec2(appContainer.getWidth() / 35, appContainer.getHeight() / 2), new int[] { Keyboard.KEY_Z, Keyboard.KEY_X }));
		addEntity(new PongPlayer("Player2", new Vec2(2, 50), new Vec2(appContainer.getWidth() - (appContainer.getWidth() / 35), appContainer.getHeight() / 2), new int[] { Keyboard.KEY_UP,
				Keyboard.KEY_DOWN }));

		addEntity(new PongBall("PongBall"));

		try {
			scoreBoard = new BitmapFont(AppContext.textureManager.get("Consolas"), new Vector2f(32, 32));
			scoreBoard.setPosition(new Vector2f(appContainer.getWidth() / 3, appContainer.getHeight() - (appContainer.getHeight() / 25)));
			scoreBoard.setFontSize(14);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		world.setContactListener(new PongWorldContactListener());

		super.init(appContainer, app);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		checkScore();
	}

	private void checkScore() {
		PongBall pongBall = (PongBall) getEntity("PongBall");

		Vec2 position = Conversion.MetersToPixels(pongBall.getBody().getPosition(), getPixelToMeterRatio());

		if (position.x >= appContainer.getWidth()) {
			playerOneScore++;
			playerOneScoredLast = true;
			reset();
		}
		else if (position.x <= 0) {
			playerTwoScore++;
			playerOneScoredLast = false;
			reset();
		}

	}

	private void reset() {
		PongBall pongBall = (PongBall) getEntity("PongBall");
		PongPlayer player1 = (PongPlayer) getEntity("Player1");
		PongPlayer player2 = (PongPlayer) getEntity("Player2");

		pongBall.reset();
		player1.reset();
		player2.reset();
		AppContext.audioManager.get("Score").play();

		if (playerOneScore == scoreLimit || playerTwoScore == scoreLimit) {
			playerOneScore = 0;
			playerTwoScore = 0;
			app.enterNextState();
		}
		else {
			serve();
		}
	}

	@Override
	public void render() {
		glBegin(GL_LINES);
		glVertex2f(appContainer.getWidth() / 2.0f, appContainer.getHeight());
		glVertex2f(appContainer.getWidth() / 2.0f, 0);
		glEnd();

		scoreBoard.renderText(String.format("Player 1: %s\t\tPlayer 2: %s", Integer.toString(playerOneScore), Integer.toString(playerTwoScore)));
	}

	public void input() {
		if (!Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_P) {
				serve();
			}
		}
	}

	private void serve() {
		Random random = new Random();

		PongBall pongBall = (PongBall) getEntity("PongBall");
		PongPlayer player1 = (PongPlayer) getEntity("Player1");
		PongPlayer player2 = (PongPlayer) getEntity("Player2");

		pongBall.reset();
		player1.reset();
		player2.reset();

		Vec2 direction = new Vec2();

		if (playerOneScoredLast) {
			direction.x = -1;
		}
		else {
			direction.x = 1;
		}

		if (random.nextBoolean()) {
			direction.y = -1;
		}
		else {
			direction.y = 1;
		}

		int factor = 10;
		int min = 5;

		pongBall.getBody().setLinearVelocity(new Vec2(((random.nextFloat() * factor) + min) * direction.x, ((random.nextFloat() * factor) + min) * direction.y));
	}
}
