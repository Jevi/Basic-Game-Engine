package test.PongGame;

import org.lwjgl.util.vector.Vector2f;

import gfx.Sprite;
import core.AudioManager;
import core.GameContainer;
import core.GameState;
import core.StateBasedGame;
import core.TextureManager;

public class PongEndState extends GameState {

	private Sprite troll;

	public PongEndState(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame game) {
		troll = new Sprite(TextureManager.getTexture("TrollFace"), new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2), new Vector2f(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2));
		AudioManager.getAudio("Trololol").play();

		super.init(gameContainer, game);
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		if (AudioManager.getAudio("Trololol").isStopped()) {
			game.enterPreviousGameState();
		}
	}

	@Override
	public void render() {
		troll.render();
	}

}
