package component;

import org.jbox2d.collision.shapes.PolygonShape;

public abstract class GeometryComponent extends Component {

	protected PolygonShape polygonShape;

	public GeometryComponent(String id, boolean enabled, PolygonShape polygonShape) {
		super(id, enabled, ComponentType.GEOMETRY);
		this.polygonShape = polygonShape;
	}

	public PolygonShape getPolygonShape() {
		return polygonShape;
	}

	public void setPolygonShape(PolygonShape polygonShape) {
		this.polygonShape = polygonShape;
	}
}
