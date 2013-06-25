package core;

import gfx.Texture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import static util.DebugLevel.*;
import util.Log;

public class TextureManager {

	private static Map<String, Texture> idToTextureMap = new HashMap<>();

	public static void loadTexture(String path, int textureUnit) throws IOException {
		String id = FilenameUtils.removeExtension(new File(path).getName());
		Texture texture = new Texture(path, textureUnit);
		texture.load();

		idToTextureMap.put(id, texture);

		Log.println(LOW_DEBUG, id + " Texture Loaded Successfully");
	}

	public static Texture getTexture(String id) {
		return idToTextureMap.get(id);
	}

	public static void destroyTexture(String id) {
		Texture texture = idToTextureMap.remove(id);

		if (texture != null) {
			texture.destroy();
			Log.println(LOW_DEBUG, id + " Texture Destroyed Successfully");
		}
	}

	public static void destroyAllTextures() {
		for (Texture texture : idToTextureMap.values()) {
			texture.destroy();
		}

		idToTextureMap.clear();
	}
}
