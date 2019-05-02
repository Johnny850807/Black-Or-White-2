package com.pokewords.framework.engine;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.views.AppView;
import com.pokewords.framework.sprites.PrototypeFactory;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

public class GameEngine {
	private IocFactory iocFactory;
	private AppView gameView;
	private UserConfig userConfig;
	private PrototypeFactory prototypeFactory;
	private AppStateMachine appStateMachine;

	public GameEngine(IocFactory iocFactory) {
		this.iocFactory = iocFactory;
	}

	public void launchEngine() {
		gameView.onAppInit();
	}

	public void keyPressed(int key) {

	}

	public void mouseClicked(int x, int y) {

	}

	public void addAppState(AppState state) {

	}

	public void startLooping() {

	}

	public AppStateWorld getWorld() {
		return null;
	}

	public UserConfig getUserConfig() {
		return null;
	}

	public AppView getGameView() {
		return gameView;
	}

	public PrototypeFactory getPrototypeFactory() {
		return prototypeFactory;
	}

	public AppStateMachine getAppStateMachine() {
		return appStateMachine;
	}

	public void setGameView(AppView gameView) {
		this.gameView = gameView;
	}

	public void setUserConfig(UserConfig userConfig) {
		this.userConfig = userConfig;
	}

	public void setPrototypeFactory(PrototypeFactory prototypeFactory) {
		this.prototypeFactory = prototypeFactory;
	}

	public void setAppStateMachine(AppStateMachine asm) {
		this.appStateMachine = asm;
	}

}
