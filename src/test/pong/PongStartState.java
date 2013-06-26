package test.pong;

import gfx.BitmapFont;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import core.AppContainer;
import core.AppContext;
import core.AppState;
import core.StateBasedApp;

public class PongStartState extends AppState {

	private BitmapFont rules;

	public PongStartState(int id) {
		super(id);
	}

	@Override
	public void init(AppContainer appContainer, StateBasedApp app) {

		try {
			rules = new BitmapFont(AppContext.textureManager.get("Consolas"), new Vector2f(32, 32));
			rules.setPosition(new Vector2f(appContainer.getWidth() / 3.5f, appContainer.getHeight() - (appContainer.getHeight() / 3)));
			rules.setFontSize(15);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		super.init(appContainer, app);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void render() {
		rules.renderText("Player 1: Z (Up) X (Down)\nPlayer 2: Up Arrow (Up) Down Arrow (Down)\n\nPress P to PLAY!\nPress ESC to Exit");
	}

	@Override
	public void input() {
		if (!Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_P) {
				app.enterNextState();
			}
		}
	}
}
