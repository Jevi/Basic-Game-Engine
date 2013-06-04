package system;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import component.Component;
import component.ComponentType;

import core.Entity;

public abstract class System {
	protected Map<Entity, Set<Component>> entityToComponentsMap = new HashMap<Entity, Set<Component>>();
	protected Set<ComponentType> requiredComponentTypes = new HashSet<ComponentType>();

	public System() {
	}

	public boolean register(Entity entity) {
		Assert.assertNotNull(entity);

		if (hasRequiredComponentTypes(entity) && !entityToComponentsMap.containsKey(entity)) {
			Set<Component> entityComponents = new HashSet<Component>();
			for (Component component : entity.getComponents()) {
				if (requiredComponentTypes.contains(component)) {
					entityComponents.add(component);
				}
				entityToComponentsMap.put(entity, entityComponents);
				return true;
			}
		}
		return false;
	}

	public boolean withdraw(Entity entity) {
		Assert.assertNotNull(entity);
		return entityToComponentsMap.remove(entity) != null;
	}

	public boolean withdraw(int index) {
		Assert.assertTrue(index >= 0);
		Assert.assertTrue(index < entityToComponentsMap.keySet().size());

		return entityToComponentsMap.remove(entityToComponentsMap.keySet().toArray(new Entity[entityToComponentsMap.keySet().size()])[index]) != null;
	}

	public abstract void update();

	public Set<ComponentType> getRequiredComponentTypes() {
		return requiredComponentTypes;
	}

	public Set<Entity> getRegisteredEntities() {
		return entityToComponentsMap.keySet();
	}

	public boolean hasRequiredComponentTypes(Entity entity) {
		Set<ComponentType> entityComponentTypes = entity.getComponentTypes();
		for (ComponentType componentType : requiredComponentTypes) {
			if (!entityComponentTypes.contains(componentType)) {
				return false;
			}
		}
		return true;
	}
}
