package graphics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {

	private String path;
	private int id;
	private int target;
	private int textureUnit;

	private ByteBuffer data;
	private int width;
	private int height;

	private boolean isLoaded = false;

	public Texture(String path, int target, int textureUnit) throws FileNotFoundException {
		if (!Files.exists(Paths.get(path), LinkOption.NOFOLLOW_LINKS)) {
			throw new FileNotFoundException(path + " not found!");
		}

		this.path = path;
		this.target = target;
		this.textureUnit = textureUnit;
		id = glGenTextures();
	}

	public void destroy() {
		glDeleteTextures(id);
		data = null;
		path = null;
	}

	public void bind() {
		glBindTexture(target, id);
	}

	public void unbind() {
		glBindTexture(target, id);
	}

	public void load() throws IOException {
		if (!isLoaded) {
			InputStream inputStream = new FileInputStream(path);
			PNGDecoder pngDecoder = new PNGDecoder(inputStream);

			width = pngDecoder.getWidth();
			height = pngDecoder.getHeight();

			data = ByteBuffer.allocateDirect(4 * width * height);
			pngDecoder.decode(data, width * 4, Format.RGBA);
			data.flip();
			inputStream.close();

			glActiveTexture(textureUnit);
			bind();
			// All RGB bytes are aligned to each other and each component is 1
			// byte
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
			glGenerateMipmap(target);

			// setup the ST coordinate system
			glTexParameteri(target, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(target, GL_TEXTURE_WRAP_T, GL_REPEAT);

			// setup what to do when the texture has to be scaled
			glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

			isLoaded = true;
		}
	}

	public String getPath() {
		return path;
	}

	public int getTarget() {
		return target;
	}

	public int getTextureUnit() {
		return textureUnit;
	}

	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + height;
		result = prime * result + id;
		result = prime * result + (isLoaded ? 1231 : 1237);
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + textureUnit;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Texture other = (Texture) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		}
		else if (!data.equals(other.data))
			return false;
		if (height != other.height)
			return false;
		if (id != other.id)
			return false;
		if (isLoaded != other.isLoaded)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		}
		else if (!path.equals(other.path))
			return false;
		if (textureUnit != other.textureUnit)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

}
