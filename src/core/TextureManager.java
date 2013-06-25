package core;

import static util.DebugLevel.LOW_DEBUG;
import gfx.Texture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL13;

import util.Log;

public class TextureManager implements Manager<Texture> {

	private Map<String, Texture> idToTextureMap = new HashMap<>();

	public TextureManager() {
	}

	@Override
	public boolean register(String path) {
		String id = FilenameUtils.removeExtension(new File(path).getName());
		Texture texture = null;
		try {
			texture = new Texture(path, GL13.GL_TEXTURE0);
			texture.load();
			idToTextureMap.put(id, texture);
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean drop(String id) {
		Texture texture = idToTextureMap.remove(id);

		if (texture != null) {
			texture.destroy();
			Log.println(LOW_DEBUG, id + " Texture Destroyed Successfully");
			return true;
		}
		return false;
	}

	@Override
	public void dropAll() {
		for (Texture texture : idToTextureMap.values()) {
			texture.destroy();
		}
	}

	@Override
	public Texture get(String id) {
		return idToTextureMap.get(id);
	}
}
