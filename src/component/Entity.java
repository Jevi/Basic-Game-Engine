package component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import util.Log;
import static util.DebugLevel.*;

import junit.framework.Assert;

import core.AppContainer;
import core.AppState;

public abstract class Entity {

	private String id;
	protected AppContainer gameContainer;
	protected AppState gameState;
	private Set<Component> components = new LinkedHashSet<Component>();

	public Entity(String id) {
		this.id = id;
	}

	public void init(AppContainer gameContainer, AppState gameState) {
		Assert.assertNotNull(gameContainer);
		Assert.assertNotNull(gameState);

		this.gameContainer = gameContainer;
		this.gameState = gameState;

		createComponents();
		for (Component component : components) {
			component.init(this);
		}

		Log.println(LOW_DEBUG, toString() + " Initialization Complete");
	}

	protected abstract void createComponents();

	public void destroy() {
		for (Component component : components) {
			component.destroy();
		}

		Log.println(LOW_DEBUG, toString() + " Destruction Complete");
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

		Log.println(LOW_DEBUG, toString() + " removed all components successfully");
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + "]";
	}

}
