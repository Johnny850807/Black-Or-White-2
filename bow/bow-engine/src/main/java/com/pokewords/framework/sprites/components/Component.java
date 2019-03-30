package com.pokewords.framework.sprites.components;

public interface Component extends Cloneable{

	void onWake();

	void onStart();

	void onUpdate();

}
