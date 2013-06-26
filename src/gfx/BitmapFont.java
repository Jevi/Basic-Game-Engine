package gfx;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.util.vector.Vector2f;

public class BitmapFont {

	private Texture texture;
	private Set<SpriteSheet> spriteSheets = new HashSet<SpriteSheet>();

	private Vector2f characterDimension;
	private int fontSize = 8;
	private Vector2f position = new Vector2f();

	private String previousText = "";
	private boolean update = false;

	public BitmapFont(Texture texture, Vector2f characterDimensions) throws IOException {
		this.texture = texture;
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
					SpriteSheet spriteSheet = new SpriteSheet(texture, characterDimension, new Vector2f(position.x + ((characterDimension.x - characterDimension.x / 2) * xoffset), position.y
							- (characterDimension.y * yoffset)), new Vector2f(fontSize, fontSize));
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

		texture.bind();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		for (SpriteSheet spriteSheet : spriteSheets) {
			VBO vbo = spriteSheet.getVBO();

			vbo.bind();
			glVertexPointer(Vertex.positionElementCount, GL_FLOAT, Vertex.stride, Vertex.positionByteOffset);
			glColorPointer(Vertex.colorElementCount, GL_FLOAT, Vertex.stride, Vertex.colorByteOffset);
			glTexCoordPointer(Vertex.textureElementCount, GL_FLOAT, Vertex.stride, Vertex.textureByteOffset);

			glDrawArrays(vbo.getMode(), 0, Vertex.elementCount);
			vbo.unbind();
		}

		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);

		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);

		texture.unbind();
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
