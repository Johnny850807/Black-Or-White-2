package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.GameEngine;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.engine.FiniteStateMachine;

public abstract class AppStateMachine extends FiniteStateMachine {

	private AppState currentState;

	private GameEngine gameEngine;

	private AppState loadingState;

	private AppState startedState;

	public abstract void onUpdate();

	public abstract void onAppEnd();

	public abstract void setStartedState(AppState state);

	public void setLoadingState(AppState state) {

	}

	public abstract AppStateWorld getCurrentStateWorld();

}
