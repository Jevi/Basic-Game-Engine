package component;

import util.Log;
import static util.DebugLevel.*;

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

		Log.println(LOW_DEBUG, toString() + " Initilization Complete");
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
		Log.println(LOW_DEBUG, toString() + " enabled");
	}

	public void disable() {
		enabled = false;
		Log.println(LOW_DEBUG, toString() + " disabled");
	}

	@Override
	public String toString() {
		return "Component [entity=" + entity + ", id=" + id + ", enabled=" + enabled + "]";
	}

}
