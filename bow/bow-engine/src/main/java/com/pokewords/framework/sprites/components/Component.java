package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

public interface Component extends Cloneable{
	String PROPERTIES = "properties";
	String FRAME_STATE_MACHINE = "Frame State Machine";
	String COLLIDABLE = "collidable";


	/**
	 * Triggered when the game is started.
	 * In this state, the component will be injected.
	 */
	void onStart();

	void onUpdate();

}
