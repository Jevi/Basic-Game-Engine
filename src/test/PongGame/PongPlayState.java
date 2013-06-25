package test.PongGame;

import static org.lwjgl.opengl.GL11.*;

import gfx.BitmapFont;

import java.io.IOException;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import util.Conversion;

import core.AudioManager;
import core.GameContainer;
import core.PhysicsGameState;
import core.StateBasedGame;
import core.TextureManager;

public class PongPlayState extends PhysicsGameState {

	private final int scoreLimit = 5;

	private int playerOneScore = 0;
	private int playerTwoScore = 0;
	private boolean playerOneScoredLast = false;

	private BitmapFont playerOneScoreText;
	private BitmapFont playerTwoScoreText;

	public PongPlayState(int id, Vec2 gravity, int pixelToMeterRatio) {
		super(id, gravity, pixelToMeterRatio);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame game) {
		addEntity(new PongWall("TopWall", new Vec2(gameContainer.getWidth() / 2, 1), new Vec2(gameContainer.getWidth() / 2, gameContainer.getHeight() + 1)));
		addEntity(new PongWall("BottomWall", new Vec2(gameContainer.getWidth() / 2, 1), new Vec2(gameContainer.getWidth() / 2, 0 - 1)));

		addEntity(new PongPlayer("Player1", new Vec2(2, 50), new Vec2(gameContainer.getWidth() / 35, gameContainer.getHeight() / 2), new int[] { Keyboard.KEY_Z, Keyboard.KEY_X }));
		addEntity(new PongPlayer("Player2", new Vec2(2, 50), new Vec2(gameContainer.getWidth() - (gameContainer.getWidth() / 35), gameContainer.getHeight() / 2), new int[] { Keyboard.KEY_UP, Keyboard.KEY_DOWN }));

		addEntity(new PongBall("PongBall"));

		try {
			playerOneScoreText = new BitmapFont(TextureManager.getTexture("Consolas"), new Vector2f(32, 32));
			playerOneScoreText.setPosition(new Vector2f(gameContainer.getWidth() / 3, gameContainer.getHeight() - (gameContainer.getHeight() / 25)));
			playerOneScoreText.setFontSize(14);

			playerTwoScoreText = new BitmapFont(TextureManager.getTexture("Consolas"), new Vector2f(32, 32));
			playerTwoScoreText.setPosition(new Vector2f((gameContainer.getWidth() / 2) + (gameContainer.getWidth() / 25), gameContainer.getHeight() - (gameContainer.getHeight() / 25)));
			playerTwoScoreText.setFontSize(14);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		world.setContactListener(new PongWorldContactListener());

		super.init(gameContainer, game);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		input();
		checkScore();
	}

	private void checkScore() {
		PongBall pongBall = (PongBall) getEntity("PongBall");

		Vec2 position = Conversion.MetersToPixels(pongBall.getBody().getPosition(), pixelToMeterRatio);

		if (position.x >= gameContainer.getWidth()) {
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
		AudioManager.getAudio("Score").play();

		if (playerOneScore == scoreLimit || playerTwoScore == scoreLimit) {
			playerOneScore = 0;
			playerTwoScore = 0;
			game.enterNextGameState();
		}
		else {
			serve();
		}
	}

	@Override
	public void render() {
		glBegin(GL_LINES);
		glVertex2f(gameContainer.getWidth() / 2.0f, gameContainer.getHeight());
		glVertex2f(gameContainer.getWidth() / 2.0f, 0);
		glEnd();

		playerOneScoreText.renderText("Player 1: " + Integer.toString(playerOneScore));
		playerTwoScoreText.renderText("Player 2: " + Integer.toString(playerTwoScore));
	}

	private void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			serve();
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

		pongBall.getBody().setLinearVelocity(new Vec2(((random.nextFloat() * 10f) + 3f) * direction.x, ((random.nextFloat() * 10f) + 3f) * direction.y));
	}
}
