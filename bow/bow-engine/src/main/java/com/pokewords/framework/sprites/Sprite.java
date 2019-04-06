package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Sprite implements Cloneable {

	private FrameStateMachineComponent viewComponent;

	private PropertiesComponent propertiesComponent;

	private Map<String,Component> components = new HashMap<>();

	public void onUpdate() {

	}

	public FrameStateMachineComponent getViewComponent() {
		return viewComponent;
	}

	public PropertiesComponent getPropertiesComponent() {
		return propertiesComponent;
	}

	public Map<String, Component> getComponents() {
		return components;
	}

	public void setViewComponent(FrameStateMachineComponent viewComponent) {
		this.viewComponent = viewComponent;
	}

	public void setPropertiesComponent(PropertiesComponent propertiesComponent) {
		this.propertiesComponent = propertiesComponent;
	}

	public void setComponents(Map<String, Component> components) {
		this.components = components;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Sprite sprite = (Sprite) o;
		return viewComponent.equals(sprite.viewComponent) &&
				propertiesComponent.equals(sprite.propertiesComponent) &&
				components.equals(sprite.components);
	}

	@Override
	public int hashCode() {
		return Objects.hash(viewComponent, propertiesComponent, components);
	}
}
