package core;

import static util.DebugLevel.LOW_DEBUG;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import util.Log;
import audio.Audio;

public class AudioManager implements Manager<Audio> {

	private Map<String, Audio> idToAudioMap = new HashMap<String, Audio>();

	public AudioManager() {
	}

	@Override
	public boolean register(String path) {
		String id = FilenameUtils.removeExtension(new File(path).getName());
		Audio audio = null;
		try {
			audio = new Audio(path);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		idToAudioMap.put(id, audio);
		Log.println(LOW_DEBUG, id + " Audio Loaded Successfully");
		return true;
	}

	@Override
	public boolean drop(String id) {
		Audio audio = idToAudioMap.remove(id);

		if (audio != null) {
			audio.destroy();
			Log.println(LOW_DEBUG, id + " Audio Destroyed Successfully");
			return true;
		}
		return false;
	}

	@Override
	public void dropAll() {
		for (Audio audio : idToAudioMap.values()) {
			audio.destroy();
		}
	}

	@Override
	public Audio get(String id) {
		return idToAudioMap.get(id);
	}
}
