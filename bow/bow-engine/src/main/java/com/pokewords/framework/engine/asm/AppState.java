package com.pokewords.framework.engine.asm;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

public abstract class AppState implements AppStateLifeCycleListener {
	protected AppStateMachine appStateMachine;
	protected IocFactory iocFactory;
	protected AppStateWorld appStateWorld;
	protected boolean started = false;

	public AppState(AppStateMachine appStateMachine) {
		this.appStateMachine = appStateMachine;
		this.iocFactory = this.appStateMachine.getIocFactory();
	}

	@Override
	public void onAppStateStart(AppStateWorld world) {
		this.started = true;
		this.appStateWorld = world;
		this.appStateWorld.onAppStateStart(world);
	}

	public boolean isStarted() {
		return started;
	}

	public AppStateMachine getAppStateMachine() {
		return appStateMachine;
	}

	public AppStateWorld getAppStateWorld() {
		return appStateWorld;
	}

	public IocFactory getIocFactory() {
		return iocFactory;
	}
}
