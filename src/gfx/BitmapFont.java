package gfx;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.util.vector.Vector2f;

public class BitmapFont {

	private Texture fonTexture;
	private Set<SpriteSheet> spriteSheets = new HashSet<SpriteSheet>();

	private Vector2f characterDimension;
	private int fontSize = 8;
	private Vector2f position = new Vector2f();

	private String previousText = "";
	private boolean update = false;

	public BitmapFont(Texture texture, Vector2f characterDimensions) throws IOException {
		fonTexture = texture;
		this.characterDimension = new Vector2f(characterDimensions);
	}

	public void renderText(String text) {
		if (!previousText.equals(text) || update) {
			if (update) {
				update = false;
			}

			previousText = text;
			spriteSheets.clear();

			int yoffset = 0;
			int xoffset = 0;

			for (int i = 0; i < text.length(); i++) {
				char character = text.charAt(i);

				if (character == '\n') {
					yoffset++;
					xoffset = 0;
				}

				if (character != ' ' && character != '\n' && character != '\t') {
					SpriteSheet spriteSheet = new SpriteSheet(fonTexture, characterDimension, new Vector2f(position.x + ((characterDimension.x - characterDimension.x / 2) * xoffset), position.y - (characterDimension.y * yoffset)), new Vector2f(
							fontSize, fontSize));
					spriteSheet.setFrame(character);
					spriteSheets.add(spriteSheet);
				}

				if (character != '\n') {
					if (character == '\t') {
						xoffset += 3;
					}
					else {
						xoffset++;
					}
				}
			}
		}

		for (SpriteSheet spriteSheet : spriteSheets) {
			spriteSheet.render();
		}
	}

	public void setFontSize(int size) {
		fontSize = size;
		update = true;
	}

	public void setPosition(Vector2f position) {
		this.position = new Vector2f(position);
		update = true;
	}

}
