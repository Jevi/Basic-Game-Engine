package gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.lwjgl.util.vector.Vector2f;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class JsonSpriteSheet {

	private String config;
	private Map<String, JsonSprite> idToSpriteMap = new HashMap<String, JsonSprite>();

	private VBO vbo;
	private Texture texture;

	private Vertex[] vertices;
	private Vector2f position;
	private Vector2f dimension;

	private String currentFrame;

	public JsonSpriteSheet(String config, String texture, Vector2f position, Vector2f dimension) throws IOException {
		this.config = config;
		this.texture = new Texture(texture, GL_TEXTURE0);
		this.position = new Vector2f(position);
		this.dimension = new Vector2f(dimension);

		this.texture.load();

		JsonReader jsonReader = new JsonReader(new InputStreamReader(new FileInputStream(this.config)));
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = jsonParser.parse(jsonReader).getAsJsonArray();

		for (JsonElement jsonElement : jsonArray) {
			JsonSprite sprite = new Gson().fromJson(jsonElement, JsonSprite.class);

			String spriteId = FilenameUtils.removeExtension(new File(sprite.getFilename()).getName());
			idToSpriteMap.put(spriteId, sprite);

			if (currentFrame == null) {
				currentFrame = spriteId;
			}
		}

		initData();
	}

	public void destroy() {
		vbo.destroy();
		texture.destroy();
	}

	private void initData() {
		vertices = new Vertex[4];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex();
		}

		JsonSprite currentSprite = idToSpriteMap.get(currentFrame);
		JsonFrame currentSpriteFrame = currentSprite.getFrame();

		Vector2f low = new Vector2f(currentSpriteFrame.getX() / texture.getWidth(), currentSpriteFrame.getY() / texture.getHeight());
		Vector2f high = new Vector2f(low.x + (currentSpriteFrame.getW() / texture.getWidth()), low.y + (currentSpriteFrame.getH() / texture.getHeight()));

		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		vertices[0].setST(low.x, low.y);
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		vertices[1].setST(low.x, high.y);
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		vertices[2].setST(high.x, high.y);
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);
		vertices[3].setST(high.x, low.y);

		vbo = new VBO(vertices, GL_QUADS, GL_STATIC_DRAW);
	}

	private void updateData() {
		JsonSprite currentSprite = idToSpriteMap.get(currentFrame);
		JsonFrame currentSpriteFrame = currentSprite.getFrame();

		Vector2f low = new Vector2f(currentSpriteFrame.getX() / texture.getWidth(), currentSpriteFrame.getY() / texture.getHeight());
		Vector2f high = new Vector2f(low.x + (currentSpriteFrame.getW() / texture.getWidth()), low.y + (currentSpriteFrame.getH() / texture.getHeight()));

		vertices[0].setXY(position.x - dimension.x, position.y + dimension.y);
		vertices[0].setST(low.x, low.y);
		vertices[1].setXY(position.x - dimension.x, position.y - dimension.y);
		vertices[1].setST(low.x, high.y);
		vertices[2].setXY(position.x + dimension.x, position.y - dimension.y);
		vertices[2].setST(high.x, high.y);
		vertices[3].setXY(position.x + dimension.x, position.y + dimension.y);
		vertices[3].setST(high.x, low.y);

		vbo.setVertices(vertices);
	}

	public void render() {
		Graphics.render(vbo, texture);
	}

	public Texture getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return new Vector2f(position);
	}

	public void setPosition(Vector2f position) {
		this.position = new Vector2f(position);
		updateData();
	}

	public Vector2f getDimension() {
		return new Vector2f(dimension);
	}

	public void setDimension(Vector2f dimension) {
		this.dimension = new Vector2f(dimension);
		updateData();
	}

	public String getFrame() {
		return currentFrame;
	}

	public void setFrame(String frame) {
		currentFrame = frame;
		updateData();
	}
}
