package core;

import static util.DebugLevel.LOW_DEBUG;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import util.Log;

import audio.Audio;

public class AudioManager {

	private static Map<String, Audio> idToAudioMap = new HashMap<String, Audio>();

	public static void loadAudio(String path) throws FileNotFoundException {
		Audio audio = new Audio(path);
		String id = FilenameUtils.removeExtension(new File(path).getName());

		idToAudioMap.put(id, audio);

		Log.println(LOW_DEBUG, id + " Audio Loaded Successfully");
	}

	public static Audio getAudio(String id) {
		return idToAudioMap.get(id);
	}

	public static void destroyTexture(String id) {
		Audio audio = idToAudioMap.remove(id);

		if (audio != null) {
			audio.destroy();
			Log.println(LOW_DEBUG, id + " Audio Destroyed Successfully");
		}
	}

	public static void destroyAllAudio() {
		for (Audio audio : idToAudioMap.values()) {
			audio.destroy();
		}

		idToAudioMap.clear();
	}
}
