package core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import component.Component;
import component.ComponentType;

public class Entity {

	private String id;
	private Set<Component> components = new HashSet<Component>();
	private Map<ComponentType, Component> componentTypeToComponentMap = new HashMap<ComponentType, Component>();

	public Entity(String id) {
		this.id = id;
	}

	public void init() {
		for (Component component : components) {
			if (component.isEnabled()) {
				component.init(this);
			}
		}
	}

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

	public Set<ComponentType> getComponentTypes() {
		return componentTypeToComponentMap.keySet();
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

	public Component getComponent(ComponentType type) {
		return componentTypeToComponentMap.get(type);
	}

	public Set<Component> getComponents() {
		return components;
	}

	public boolean addComponent(Component newComponent) {
		if (components.add(newComponent)) {
			componentTypeToComponentMap.put(newComponent.getType(), newComponent);
			return true;
		}
		return false;
	}

	public boolean removeComponent(String id) {
		for (Component component : components) {
			if (component.getId().equals(id)) {
				components.remove(component);
				componentTypeToComponentMap.remove(component.getType());
				return true;
			}
		}
		return false;
	}

	public boolean removeComponent(int index) {
		Assert.assertTrue(index >= 0);
		Assert.assertTrue(index < components.size());

		Component component = getComponent(index);
		if (components.remove(component)) {
			componentTypeToComponentMap.remove(component.getType());
			return true;
		}
		return false;
	}
}
