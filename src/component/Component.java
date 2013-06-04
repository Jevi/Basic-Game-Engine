package component;

import junit.framework.Assert;
import core.Entity;

public abstract class Component {

	protected Entity entity;
	private String id;
	private ComponentType type;
	private boolean enabled = false;

	public Component(String id, boolean enabled, ComponentType type) {
		this.id = id;
		this.type = type;
		this.enabled = enabled;
	}

	public void init(Entity entity) {
		Assert.assertNotNull(entity);

		this.entity = entity;
	}

	public abstract void destroy();

	public abstract void update(int delta);

	public String getId() {
		return id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public ComponentType getType() {
		return type;
	}

	public Entity getEntity() {
		return entity;
	}

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}
}