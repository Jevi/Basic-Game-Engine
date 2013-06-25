package gfx;

public class JsonSprite {

	private String filename;
	private JsonFrame frame;
	private boolean rotated;
	private boolean trimmed;

	public JsonSprite() {
	}

	public String getFilename() {
		return filename;
	}

	public JsonFrame getFrame() {
		return frame;
	}

	public boolean isRotated() {
		return rotated;
	}

	public boolean isTrimmed() {
		return trimmed;
	}

	@Override
	public String toString() {
		return "JsonSprite [filename=" + filename + ", frame=" + frame + ", rotated=" + rotated + ", trimmed=" + trimmed + "]";
	}

}
