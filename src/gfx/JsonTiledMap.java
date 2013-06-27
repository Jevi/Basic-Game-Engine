package gfx;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.lwjgl.util.vector.Vector2f;

import util.IO;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonTiledMap {

	public static final String TILED_MAP_ROTATION_90 = "2684354560";
	public static final String TILED_MAP_ROTATION_180 = "3221225472";
	public static final String TILED_MAP_ROTATION_270 = "1610612736";

	private String config;
	private Texture texture;
	private Vector2f position;

	private SpriteSheet[] spriteSheets;

	private JsonTiledMapLayer[] layers;
	private int width, height; // in tiles
	private int tilewidth, tileheight; // in pixels

	public JsonTiledMap(String config, Texture texture, Vector2f position) throws JsonSyntaxException, IOException {
		this.config = config;
		this.texture = texture;
		this.position = new Vector2f(position);

		JsonTiledMap jsonTiledMap = new Gson().fromJson(IO.getFileContent(this.config), JsonTiledMap.class);

		this.layers = jsonTiledMap.getLayers();
		this.width = jsonTiledMap.getWidth();
		this.height = jsonTiledMap.getHeight();
		this.tilewidth = jsonTiledMap.getTileWidth();
		this.tileheight = jsonTiledMap.getTileHeight();

		initData();
	}

	private void initData() {

		Set<SpriteSheet> spriteSheetSet = new LinkedHashSet<SpriteSheet>();

		for (JsonTiledMapLayer layer : layers) {
			JsonTiledMapLayerType type = layer.getType();

			switch (type) {
			case TILELAYER:
				int xoffset = 0;
				int yoffset = 0;
				for (long data : layer.getData()) {
					data--;
					if (xoffset == width) {
						xoffset = 0;
						yoffset++;
					}

					float angle = 0;
					long frame = data;

					if (data > 0) {
						if (Long.toString(data).startsWith(TILED_MAP_ROTATION_90.substring(0, 4))) {
							angle = -90f;
							frame = frame - Long.parseLong(TILED_MAP_ROTATION_90);
						}
						else if (Long.toString(data).startsWith(TILED_MAP_ROTATION_180.substring(0, 4))) {
							angle = -180f;
							frame = frame - Long.parseLong(TILED_MAP_ROTATION_180);
						}
						else if (Long.toString(data).startsWith(TILED_MAP_ROTATION_270.substring(0, 4))) {
							angle = -270f;
							frame = frame - Long.parseLong(TILED_MAP_ROTATION_270);
						}

						SpriteSheet spriteSheet = new SpriteSheet(texture, new Vector2f(tilewidth, tileheight), new Vector2f(position.x + (tilewidth * xoffset), position.y + (-tileheight * yoffset)),
								new Vector2f(tilewidth / 2, tileheight / 2));
						spriteSheet.setAngle(angle);
						spriteSheet.setFrame((int) frame);
						spriteSheetSet.add(spriteSheet);
					}
					xoffset++;
				}
				spriteSheets = spriteSheetSet.toArray(new SpriteSheet[spriteSheetSet.size()]);
				break;
			case OBJECTGROUP:
				System.out.println(layer);
				break;
			}

		}
	}

	public void render() {
		texture.bind();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		for (SpriteSheet spriteSheet : spriteSheets) {
			VBO vbo = spriteSheet.getVBO();
			float angle = spriteSheet.getAngle();

			vbo.bind();
			if (angle != 0) {
				glPushMatrix();

				Vector2f position = spriteSheet.getPosition();

				glTranslatef(position.x, position.y, 0);
				glRotatef(angle, 0.0f, 0.0f, 1);
				glTranslatef(-position.x, -position.y, 0);

				glVertexPointer(Vertex.positionElementCount, GL_FLOAT, Vertex.stride, Vertex.positionByteOffset);
				glColorPointer(Vertex.colorElementCount, GL_FLOAT, Vertex.stride, Vertex.colorByteOffset);
				glTexCoordPointer(Vertex.textureElementCount, GL_FLOAT, Vertex.stride, Vertex.textureByteOffset);

				glDrawArrays(vbo.getMode(), 0, Vertex.elementCount);

				glPopMatrix();
			}
			else {
				glVertexPointer(Vertex.positionElementCount, GL_FLOAT, Vertex.stride, Vertex.positionByteOffset);
				glColorPointer(Vertex.colorElementCount, GL_FLOAT, Vertex.stride, Vertex.colorByteOffset);
				glTexCoordPointer(Vertex.textureElementCount, GL_FLOAT, Vertex.stride, Vertex.textureByteOffset);

				glDrawArrays(vbo.getMode(), 0, Vertex.elementCount);
			}
			vbo.unbind();
		}

		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);

		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);

		texture.unbind();
	}

	public JsonTiledMapLayer[] getLayers() {
		return Arrays.copyOf(layers, layers.length);
	}

	public String getConfig() {
		return config;
	}

	public Texture getTexture() {
		return texture;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTileWidth() {
		return tilewidth;
	}

	public int getTileHeight() {
		return tileheight;
	}

	@Override
	public String toString() {
		return "JsonTiledMap [config=" + config + ", texture=" + texture + ", layers=" + Arrays.toString(layers) + ", width=" + width + ", height=" + height + ", tilewidth=" + tilewidth
				+ ", tileheight=" + tileheight + "]";
	}

}
