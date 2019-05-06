package com.pokewords.framework.sprites.components;


import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;

public abstract class Component implements AppStateLifeCycleListener {
	public static final String PROPERTIES = "PropertiesComponent";
	public static final String FRAME_STATE_MACHINE = "FrameStateMachineComponent";
	public static final String COLLIDABLE = "collidableComponent";
}
