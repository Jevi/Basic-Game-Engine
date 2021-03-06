package test.pong;

import gfx.Sprite;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import core.AppContainer;
import core.AppContext;
import core.AppState;
import core.StateBasedApp;

public class PongEndState extends AppState {

	private Sprite troll;

	public PongEndState(int id) {
		super(id);
	}

	@Override
	public void init(AppContainer appContainer, StateBasedApp app) {
		troll = new Sprite(AppContext.textureManager.get("TrollFace"), new Vector2f(appContainer.getWidth() / 2, appContainer.getHeight() / 2), new Vector2f(appContainer.getWidth() / 2,
				appContainer.getHeight() / 2));
		AppContext.audioManager.get("Trololol").play();

		super.init(appContainer, app);
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		if (AppContext.audioManager.get("Trololol").isStopped()) {
			app.enterPreviousState();
		}
	}

	@Override
	public void render() {
		troll.render();
	}

	@Override
	public void input() {
		if (!Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_P) {
				AppContext.audioManager.get("Trololol").stop();
			}
		}
	}

}
