package component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class RelationalComponent extends Component {

	protected Set<ComponentType> requiredComponentTypes;

	public RelationalComponent(String id, boolean enabled, ComponentType... requiredComponentTypes) {
		super(id, enabled, ComponentType.RELATIONAL);
		this.requiredComponentTypes = new HashSet<ComponentType>(Arrays.asList(requiredComponentTypes));
	}

	@Override
	public void update(int delta) {
		if (!hasRequiredComponentTypes()) {
			return;
		}
	}

	public Set<ComponentType> getRequiredComponentTypes() {
		return requiredComponentTypes;
	}

	public boolean hasRequiredComponentTypes() {
		Set<ComponentType> entityComponentTypes = entity.getComponentTypes();
		for (ComponentType componentType : requiredComponentTypes) {
			if (!entityComponentTypes.contains(componentType)) {
				return false;
			}
		}
		return true;
	}
}
