package component;

import org.jbox2d.common.Vec2;

public abstract class PositionComponent extends Component {

	protected Vec2 position;
	protected float angle;

	public PositionComponent(String id, boolean enabled, Vec2 position, float angle) {
		super(id, enabled, ComponentType.POSITION);

		this.position = position;
		this.angle = angle;
	}
	
	@Override
	public void update(int delta) {
	}

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		this.position = position;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
}
