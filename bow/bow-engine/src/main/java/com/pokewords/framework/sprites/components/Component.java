package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

public interface Component extends Cloneable{
	String PROPERTIES = "properties";
	String FRAME_STATE_MACHINE = "Frame State Machine";
	String COLLIDABLE = "collidable";

	/**
	 * Triggered when the component is bound to a Sprite,
	 * and all component fields have been injected.
	 * @param sprite Bound Sprite
	 */
	void onBoundToSprite(Sprite sprite);

	/**
	 * Triggered when the game is started
	 */
	void onStart();

	void onUpdate();

	Component clone();

}
