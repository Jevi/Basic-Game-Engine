package component;

import junit.framework.Assert;

public abstract class Component {

	protected Entity entity;
	private String id;
	private boolean enabled = false;

	public Component(String id, boolean enabled) {
		this.id = id;
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
