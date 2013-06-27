package gfx;

public enum JsonTiledMapLayerType {

	OBJECTGROUP("OBJECTGROUP"), TILELAYER("TILELAYER");

	private String id;

	private JsonTiledMapLayerType(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}
