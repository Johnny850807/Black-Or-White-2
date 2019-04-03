package com.pokewords.framework.engine.asm;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

public abstract class AppState {

	protected AppStateMachine appStateMachine;

	private AppStateWorld appStateWorld;

	public abstract void onEnter();

	public abstract void onUpdate();

	public abstract void onExit();

	public AppStateWorld getStateWorld() {
		return null;
	}

}
