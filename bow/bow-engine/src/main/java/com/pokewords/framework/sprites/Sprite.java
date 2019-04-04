package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FramesStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *  Current definition:
 *  1. Both FSM and Properties component are mandatory and unique
 *  2. Every components has a name, and it's unique.
 *  3. The setting of mandatory components
 *
 */
public class Sprite implements Cloneable {

	private Map<String, Component> components;

	public Sprite(final String customFSMComponentName, final FramesStateMachineComponent FSMComponent,
				  final String customPropertiesComponentName, final PropertiesComponent propertiesComponent) {
		components = new HashMap<String, Component>() {{
			put(customFSMComponentName, FSMComponent);
			put(customPropertiesComponentName, propertiesComponent);
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
}
