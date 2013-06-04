package component;

public enum ComponentType {

	POSITION("POSITION"), MOVEMENT("MOVEMENT"), GEOMETRY("GEOMETRY"), PROPERTY("PROPERTY"), SPRITE("SPRITE"), RELATIONAL("RELATIONAL");

	private String type;

	private ComponentType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
