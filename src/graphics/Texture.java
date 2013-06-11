package graphics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {

	private String path;
	private int textureUnit;
	private int id;

	private ByteBuffer data;
	private int width;
	private int height;

	public Texture(String path, int textureUnit) {
		this.path = path;
		this.textureUnit = textureUnit;
		id = glGenTextures();
	}

	public void destroy() {
		glDeleteTextures(id);
		data = null;
		path = null;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void load() throws IOException {
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
		// All RGB bytes are aligned to each other and each component is 1 byte
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		glGenerateMipmap(GL_TEXTURE_2D);

		// setup the ST coordinate system
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		// setup what to do when the texture has to be scaled
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
	}

	public String getPath() {
		return path;
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

}
