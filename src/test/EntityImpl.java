package test;

import org.jbox2d.common.Vec2;

import component.Component;
import component.ComponentType;

import core.Entity;

public class EntityImpl extends Entity {

	private Vec2 position = new Vec2(0, 0);

	public EntityImpl(String id) {
		super(id);

		Component renderComponent = new Component("Render Component", true, ComponentType.GEOMETRY) {
			@Override
			public void update(int delta) {
				position.x += 10;
			}

			@Override
			public void destroy() {
			}
		};
		this.addComponent(renderComponent);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		System.out.println(position.x);
	}
}
