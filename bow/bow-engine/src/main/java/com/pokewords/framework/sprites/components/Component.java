package com.pokewords.framework.sprites.components;


public abstract class Component implements AppStateLifeCycleListener, Cloneable{
	public static final String PROPERTIES = "properties";
	public static final String FRAME_STATE_MACHINE = "Frame State Machine";
	public static final String COLLIDABLE = "collidable";

	public Component clone(){
		try {
			return (Component) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
