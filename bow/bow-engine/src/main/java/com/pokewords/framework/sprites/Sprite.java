package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;
import com.pokewords.framework.sprites.components.PropertiesComponent;

import java.util.Map;

public class Sprite implements Cloneable {

	private FrameStateMachineComponent viewComponent;

	private PropertiesComponent propertiesComponent;

	private Map<String,Component> components;

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
}
