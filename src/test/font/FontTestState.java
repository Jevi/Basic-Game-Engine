package test.font;

import gfx.BitmapFont;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import core.AppContainer;
import core.AppContext;
import core.AppState;
import core.StateBasedApp;

public class FontTestState extends AppState {

	private BitmapFont bitmapFont;
	private StringBuilder renderString = new StringBuilder("Enter Text");

	public FontTestState(int id) {
		super(id);
	}

	@Override
	public void init(AppContainer gameContainer, StateBasedApp game) {

		try {
			bitmapFont = new BitmapFont(AppContext.textureManager.get("Consolas"), new Vector2f(32, 32));
			bitmapFont.setPosition(new Vector2f(gameContainer.getWidth() / 3.5f, gameContainer.getHeight() - (gameContainer.getHeight() / 3)));
			bitmapFont.setFontSize(15);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		super.init(gameContainer, game);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void render() {
		bitmapFont.renderText(renderString.toString());
	}

	@Override
	public void input() {
		if (Keyboard.getEventKeyState()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_DELETE) {
				renderString.setLength(0);
			}

			if (Keyboard.getEventKey() != Keyboard.KEY_BACK) {
				if (Keyboard.getEventKey() != Keyboard.KEY_LSHIFT) {
					if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
						renderString.append("\n");
					}
					else {
						renderString.append(Keyboard.getEventCharacter());
					}
				}
			}
			else if (renderString.length() > 0) {
				renderString.setLength(renderString.length() - 1);
			}
		}
	}
}
