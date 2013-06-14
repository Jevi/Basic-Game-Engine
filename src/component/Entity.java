package component;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import core.GameContainer;

public abstract class Entity {

	private String id;
	protected GameContainer gameContainer;
	private Set<Component> components = new HashSet<Component>();

	public Entity(String id) {
		this.id = id;
	}

	public void init(GameContainer gameContainer) {
		Assert.assertNotNull(gameContainer);
		this.gameContainer = gameContainer;

		createComponents();
		for (Component component : components) {
			if (component.isEnabled()) {
				component.init(this);
			}
		}
	}

	protected abstract void createComponents();

	public void destroy() {
		for (Component component : components) {
			component.destroy();
		}
	}

	public void update(int delta) {
		for (Component component : components) {
			if (component.isEnabled()) {
				component.update(delta);
			}
		}
	}

	public String getId() {
		return id;
	}

	public Component getComponent(String id) {
		for (Component component : components) {
			if (component.getId().equals(id)) {
				return component;
			}
		}
		return null;
	}

	public Component getComponent(int index) {
		Assert.assertTrue(index >= 0);
		Assert.assertTrue(index < components.size());

		return components.toArray(new Component[components.size()])[index];
	}

	public Set<Component> getComponents() {
		return components;
	}

	public boolean addComponent(Component newComponent) {
		return components.add(newComponent);
	}

	public boolean removeComponent(String id) {
		for (Component component : components) {
			if (component.getId().equals(id)) {
				components.remove(component);
				return true;
			}
		}
		return false;
	}

	public boolean removeComponent(int index) {
		Assert.assertTrue(index >= 0);
		Assert.assertTrue(index < components.size());

		return components.remove(getComponent(index));
	}

	public void removeAllComponents() {
		components = new HashSet<Component>();
	}
}
