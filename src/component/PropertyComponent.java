package component;

public abstract class PropertyComponent extends Component {

	protected float density;
	protected float restitution;
	protected float friction;

	public PropertyComponent(String id, boolean enabled) {
		super(id, enabled, ComponentType.PROPERTY);
		friction = 0.2f;
		restitution = 0f;
		density = 0f;
	}

	public PropertyComponent(String id, boolean enabled, float density, float restitution, float friction) {
		super(id, enabled, ComponentType.PROPERTY);
		this.density = density;
		this.restitution = restitution;
		this.friction = friction;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getRestitution() {
		return restitution;
	}

	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

}
