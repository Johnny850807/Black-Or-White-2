package com.pokewords.framework.engine.asm;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

public abstract class AppState implements AppStateLifeCycleListener {
	protected AppStateMachine appStateMachine;
	protected IocFactory iocFactory;
	protected AppStateWorld appStateWorld;

	public AppState(AppStateMachine appStateMachine) {
		this.appStateMachine = appStateMachine;
		this.iocFactory = this.appStateMachine.getIocFactory();
		this.appStateWorld = new AppStateWorld();

		this.onAppStateInit(appStateWorld);
		appStateWorld.onAppStateInit(appStateWorld);
	}

	public AppStateMachine getAppStateMachine() {
		return appStateMachine;
	}

	public AppStateWorld getStateWorld() {
		return appStateWorld;
	}

}
