package com.pokewords.framework.engine;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.SpriteInitializer;
import com.pokewords.framework.views.AppView;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.views.GameWindowsConfigurator;
import com.pokewords.framework.views.InputManager;

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
	private Thread gameLoopingThread;
	private boolean running = false;
	private int timePerFrame = 15;  //ms

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
		gameLoopingThread = new Thread(this::gameLooping);
		gameLoopingThread.start();
		gameView.onAppLoading();

		//Reveal below code will lead to errors, because AppStateWorld is not finished.
//		appStateMachine.trigger(AppStateMachine.EVENT_GAME_STARTED);
//		gameView.onAppStarted();
	}

	private void gameLooping() {
		running = true;
		try {
			while (running)
			{
				Thread.sleep(timePerFrame);
				inputManager.onUpdate(timePerFrame);
				appStateMachine.onUpdate(timePerFrame);
			}
		} catch (InterruptedException ignored) {

		}

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
