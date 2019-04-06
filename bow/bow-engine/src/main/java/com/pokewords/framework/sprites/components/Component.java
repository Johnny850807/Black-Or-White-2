package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

public interface Component extends Cloneable{

	/**
	 * Triggered when the component is instantiated
	 */
	void onInit();

	/**
	 * Triggered when the component is bound to a Sprite
	 * @param sprite Bound Sprite
	 */
	void onBoundToSprite(Sprite sprite);

	/**
	 * Triggered when the game is started
	 */
	void onStart();

	void onUpdate();

}
