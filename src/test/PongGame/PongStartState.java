package test.PongGame;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import gfx.BitmapFont;
import core.GameContainer;
import core.GameState;
import core.StateBasedGame;
import core.TextureManager;

public class PongStartState extends GameState {

	private BitmapFont rules;

	public PongStartState(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame game) {

		try {
			rules = new BitmapFont(TextureManager.getTexture("Consolas"), new Vector2f(32, 32));
			rules.setPosition(new Vector2f(gameContainer.getWidth() / 3.5f, gameContainer.getHeight() - (gameContainer.getHeight() / 3)));
			rules.setFontSize(15);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		super.init(gameContainer, game);
	}

	@Override
	public void update(int delta) {
		input();
		super.update(delta);
	}

	@Override
	public void render() {
		rules.renderText("Player 1: Z (Up) X (Down)\nPlayer 2: Up Arrow (Up) Down Arrow (Down)\n\nPress P to PLAY!");
	}

	private void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
			game.enterNextGameState();
		}
	}
}
