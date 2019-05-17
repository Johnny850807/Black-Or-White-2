package com.pokewords.framework.engine;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.AppView;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;
import com.pokewords.framework.views.inputs.InputManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author johnny850807 (waterball)
 */
public class GameEngine {
	private IocFactory iocFactory;
	private AppView gameView;
	private SpriteInitializer spriteInitializer;
	private InputManager inputManager;
	private UserConfig userConfig;
	private AppStateMachine appStateMachine;
	private ScheduledExecutorService looper = Executors.newScheduledThreadPool(3);
	private boolean running = false;
	private int timePerFrame = 30;  //ms

	public GameEngine(IocFactory iocFactory, InputManager inputManager, GameWindowsConfigurator gameWindowsConfigurator) {
		this.iocFactory = iocFactory;
		this.inputManager = inputManager;
		this.spriteInitializer = new SpriteInitializer(iocFactory);
		this.appStateMachine = new AppStateMachine(inputManager, spriteInitializer, gameWindowsConfigurator);
	}

	public void setGameView(AppView gameView) {
		this.gameView = gameView;
	}

	public void launchEngine() {
		gameView.onAppInit();
		appStateMachine.trigger(AppStateMachine.EVENT_LOADING);
		looper.scheduleAtFixedRate(this::gameLooping,0, timePerFrame, TimeUnit.MILLISECONDS);
		gameView.onAppLoading();

		//Reveal below code will lead to errors, because AppStateWorld is not finished.
//		appStateMachine.trigger(AppStateMachine.EVENT_GAME_STARTED);
//		gameView.onAppStarted();
	}

	private void gameLooping() {
		appStateMachine.onUpdate(timePerFrame);
		gameView.onRender(appStateMachine.getCurrentStateWorld().getRenderedLayers());
	}

	public AppView getGameView() {
		return gameView;
	}

	public AppStateMachine getAppStateMachine() {
		return appStateMachine;
	}

	public SpriteInitializer getSpriteInitializer() {
		return spriteInitializer;
	}

}
