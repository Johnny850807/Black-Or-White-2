package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.Components;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 *  Current definition:
 *  1. Both FSM and Properties component are mandatory and unique
 *  2. Every components has a name, and it's unique.
 *  3. The setting of mandatory components
 *
 *  New requirement @ 4/6:
 *  1. Now the two mandatory components have predetermined names by us,
 *     so the user doesn't have to name it.
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
			put(Components.FSMComponentName, FSMComponent);
			put(Components.propertiesComponentName, propertiesComponent);
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
		return (FrameStateMachineComponent) components.get(Components.FSMComponentName);
	}

	/**
	 * The getter of PropertiesComponent.
	 * @return The concrete PropertiesComponent.
	 */
	public PropertiesComponent getPropertiesComponent() {
		return (PropertiesComponent) components.get(Components.propertiesComponentName);
	}

	/**
	 * Get the component by name.
	 * @param name the name of the component to get.
	 * @return the component to get if the name exist, null-object otherwise.
	 */
	public Optional<Component> getComponentByName(String name) {
		return new Optional<>(components.get(name));
	}

	/**
	 * Put new component with name.
	 * @param name the name of the component to be added.
	 * @param component the component to be added.
	 */
	public void putCompnent(String name, Component component) {
		components.put(name, component);
	}

	/**
	 * Remove the component by name.
	 * @param name the name of the component to be removed.
	 * @return the removed component if the name exist, null-object otherwise.
	 */
	public Optional<Component> removeComponentByName(String name) {
		return new Optional<>(components.remove(name));
	}

	public void onUpdate() {

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
}
