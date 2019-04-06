package com.pokewords.framework.sprites.components;

import com.pokewords.framework.engine.FiniteStateMachine;
import com.pokewords.framework.sprites.Sprite;

import java.util.Collection;

/**
 * @author johnny850807
 */
public class FrameStateMachineComponent extends FiniteStateMachine<Frame>
		implements Component {
	private PropertiesComponent propertiesComponent;


	public FrameStateMachineComponent() {
		
	}

	public FrameStateMachineComponent(PropertiesComponent propertiesComponent) {
		this.propertiesComponent = propertiesComponent;
	}

	/**
	 * This boolean indicates if the state is triggered after modified, if it's not triggered, we expect it will be triggered
	 * at the next tick.
	 */
	private boolean stateTriggered = false;

	@Override
	public void onBoundToSprite(Sprite sprite) {
		propertiesComponent.addStateListener( this.new StateListener());
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onUpdate() {
		if (!stateTriggered)
			trigger(propertiesComponent.getState());
	}

	private class StateListener implements PropertiesComponent.StateListener{

		@Override
		public void onStateUpdated(String state) {

		}
	}

}
