package audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.lwjgl.util.WaveData;

import static org.lwjgl.openal.AL10.*;

public class Audio {

	private int buffer;
	private int source;
	private String path;

	public Audio(String path) throws FileNotFoundException {
		this.path = path;

		init();
	}

	private void init() throws FileNotFoundException {
		WaveData waveData = WaveData.create(new BufferedInputStream(new FileInputStream(path)));
		buffer = alGenBuffers();
		alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
		waveData.dispose();
		source = alGenSources();

		alSourcei(source, AL_BUFFER, buffer);
	}

	public void destroy() {
		alDeleteBuffers(buffer);
	}

	public String getPath() {
		return path;
	}

	public void play() {
		alSourcePlay(source);
	}

	public void pause() {
		alSourcePause(source);
	}

	public void stop() {
		alSourceStop(source);
	}

	public boolean isPlaying() {
		if (alGetSourcei(source, AL_SOURCE_STATE) == AL_PLAYING) {
			return true;
		}
		return false;
	}

	public boolean isPaused() {
		if (alGetSourcei(source, AL_SOURCE_STATE) == AL_PAUSED) {
			return true;
		}
		return false;
	}

	public boolean isStopped() {
		if (alGetSourcei(source, AL_SOURCE_STATE) == AL_STOPPED) {
			return true;
		}
		return false;
	}

}
