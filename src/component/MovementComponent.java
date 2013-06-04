package component;

import org.jbox2d.common.Vec2;

public abstract class MovementComponent extends Component {

	protected Vec2 linearVelocity;
	protected float angularVelocity;

	public MovementComponent(String id, boolean enabled) {
		super(id, enabled, ComponentType.MOVEMENT);
		linearVelocity = new Vec2(0.0f, 0.0f);
		angularVelocity = 0.0f;
	}

	public MovementComponent(String id, boolean enabled, Vec2 linearVelocity, float angularVelocity) {
		super(id, enabled, ComponentType.MOVEMENT);
		this.linearVelocity = linearVelocity;
		this.angularVelocity = angularVelocity;
	}

	public Vec2 getLinearVelocity() {
		return linearVelocity;
	}

	public void setLinearVelocity(Vec2 linearVelocity) {
		this.linearVelocity = linearVelocity;
	}

	public float getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

}
