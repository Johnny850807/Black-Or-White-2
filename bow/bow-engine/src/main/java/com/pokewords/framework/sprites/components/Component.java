package com.pokewords.framework.sprites.components;


public interface Component extends AppStateLifeCycleListener, Cloneable{
	String PROPERTIES = "properties";
	String FRAME_STATE_MACHINE = "Frame State Machine";
	String COLLIDABLE = "collidable";

	Component clone();
}
