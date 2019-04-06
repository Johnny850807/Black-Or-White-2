package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.sprites.Sprite;

import java.util.Collection;

/**
 * @author johnny850807
 */
public class FramesStateMachineComponent extends FiniteStateMachine<Frame>
		implements Component, PropertiesComponent.StateListener {
	private PropertiesComponent propertiesComponent;


	public FramesStateMachineComponent() {
	}

	public FramesStateMachineComponent(PropertiesComponent propertiesComponent) {
		this.propertiesComponent = propertiesComponent;
	}

	/**
	 * This boolean indicates if the state is triggered after modified, if it's not triggered, we expect it will be triggered
	 * at the next tick.
	 */
	private boolean stateTriggered = false;

	public void onInit() {

	}

	public void onBoundToSprite(Sprite sprite) {

	}


	public void onStart() {

	}


	public void onUpdate() {

	}

	public void onStateUpdated(String state) {

	}

}
