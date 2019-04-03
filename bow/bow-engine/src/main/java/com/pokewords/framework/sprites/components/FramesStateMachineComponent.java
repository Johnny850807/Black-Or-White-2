package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.sprites.Sprite;

import java.util.Collection;

public class FramesStateMachineComponent extends FiniteStateMachine<Frame>
		implements Component, PropertiesComponent.StateListener {
	private PropertiesComponent propertiesComponent;

	/**
	 * This boolean indicates if the
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
