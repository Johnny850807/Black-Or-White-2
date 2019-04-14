package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.MandatoryComponentIsRequiredException;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author johnny850807, nyngwang
 */
public class Sprite implements Cloneable {

	private Map<String, Component> components;

	/**
	 * The constructor of Sprite.
	 * @param FSMComponent The mandatory component to define the view of the Sprite.
	 * @param propertiesComponent The mandatory component to define the properties of the Sprite.
	 */
	public Sprite(final FrameStateMachineComponent FSMComponent,
				  final PropertiesComponent propertiesComponent) {
		components = new HashMap<String, Component>() {{
			put(Component.FRAME_STATE_MACHINE, FSMComponent);
			put(Component.PROPERTIES, propertiesComponent);
		}};
	}

	/**
	 * Return the entire component map.
	 * @return the component map.
	 */
	public Map<String, Component> getComponents() {
		return components;
	}

	/**
	 * The getter of FrameStateMachineComponent.
	 * @return The concrete FrameStateMachineComponent.
	 */
	public FrameStateMachineComponent getFrameStateMachineComponent() {
		return (FrameStateMachineComponent) components.get(Component.FRAME_STATE_MACHINE);
	}

	/**
	 * The getter of PropertiesComponent.
	 * @return The concrete PropertiesComponent.
	 */
	public PropertiesComponent getPropertiesComponent() {
		return (PropertiesComponent) components.get(Component.PROPERTIES);
	}

	/**
	 * Get the component by name.
	 * @param name the name of the component to get.
	 * @return the component to get if the name exist, null-object otherwise.
	 */
	public Optional<Component> getComponentByName(String name) {
		return Optional.of(components.get(name));
	}

	/**
	 * Put new component with name.
	 * @param name the name of the component to be added.
	 * @param component the component to be added.
	 */
	public void putComponent(String name, Component component) {
		components.put(name, component);
	}

	/**
	 * Remove the component by name.
	 * @param name the name of the component to be removed.
	 * @return the removed component if the name exist, null-object otherwise.
	 */
	public Optional<Component> removeComponentByName(String name) {
		if (name.equals(Component.FRAME_STATE_MACHINE))
			throw new MandatoryComponentIsRequiredException("Frame State Machine Component cannot be removed.");
		else if (name.equals(Component.PROPERTIES))
			throw new MandatoryComponentIsRequiredException("Properties Component cannot be removed.");

		return Optional.of(components.remove(name));
	}

	public void onUpdate() {
		for (Component component : components.values())
			component.onUpdate();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Sprite sprite = (Sprite) o;
		return Objects.equals(components, sprite.components);
	}

	@Override
	public int hashCode() {
		return Objects.hash(components);
	}


	public Sprite clone(){
		try {
			Sprite sprite = (Sprite) super.clone();
			sprite.components = deepCopyComponents();
			return sprite;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, Component> deepCopyComponents(){
		HashMap<String, Component> cloneComponents = new HashMap<>();
		for (String type : this.components.keySet())
			cloneComponents.put(type, components.get(type).clone());
		return cloneComponents;
	}
}
