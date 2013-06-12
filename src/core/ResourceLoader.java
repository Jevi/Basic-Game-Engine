package core;

import graphics.Texture;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

public class ResourceLoader {
	private static Map<String, Texture> nameToTextureMap = new HashMap<String, Texture>();

	public static Texture loadTexture(String path, int textureUnit) throws FileNotFoundException {
		String key = FilenameUtils.removeExtension(Paths.get(path).getFileName().toString());
		if (!nameToTextureMap.containsKey(key)) {
			Texture texture = new Texture(path, textureUnit);
			nameToTextureMap.put(key, texture);
			return texture;
		}
		return nameToTextureMap.get(key);
	}

	public static boolean destroyTexture(String name) {
		Texture texture = nameToTextureMap.get(name);
		texture.destroy();
		return nameToTextureMap.remove(name) != null;
	}

	public static Texture getTexture(String name) {
		return nameToTextureMap.get(name);
	}

	public static Set<Texture> getTextures() {
		return (Set<Texture>) nameToTextureMap.values();
	}
}
