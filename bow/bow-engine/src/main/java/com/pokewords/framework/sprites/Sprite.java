package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FramesStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

import java.util.Map;

public class Sprite implements Cloneable {

	private FramesStateMachineComponent viewComponent;

	private PropertiesComponent propertiesComponent;

	private Map<String,Component> components;

	public void onUpdate() {

	}

	public FramesStateMachineComponent getViewComponent() {
		return viewComponent;
	}

	public PropertiesComponent getPropertiesComponent() {
		return propertiesComponent;
	}

	public Map<String, Component> getComponents() {
		return components;
	}

	public void setViewComponent(FramesStateMachineComponent viewComponent) {
		this.viewComponent = viewComponent;
	}

	public void setPropertiesComponent(PropertiesComponent propertiesComponent) {
		this.propertiesComponent = propertiesComponent;
	}

	public void setComponents(Map<String, Component> components) {
		this.components = components;
	}
}
