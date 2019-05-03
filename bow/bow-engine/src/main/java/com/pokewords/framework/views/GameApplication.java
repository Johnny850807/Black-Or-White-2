package com.pokewords.framework.views;

import com.pokewords.framework.engine.GameEngine;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.SpriteInitializer;

/**
 * @author johnny850807 (waterball), shawn
 */
public abstract class GameApplication implements AppView {
	private GameEngine gameEngine;
	private GameFrame gameFrame;
	private GameWindowsConfigurator gameWindowsConfigurator;

    public GameApplication(IocFactory iocFactory) {
		gameFrame = new GameFrame(new GamePanel());
		gameWindowsConfigurator = new GameFrameWindowsConfigurator(gameFrame);
        gameEngine = new GameEngine(iocFactory, gameWindowsConfigurator);
        gameEngine.setGameView(this);
    }

    /**
	 * Launch the game application.
	 */
	public void launch() {
		gameEngine.launchEngine();
	}

	@Override
	public void onAppInit() {
		gameFrame.onAppInit();
		onGameWindowsConfiguration(gameWindowsConfigurator);
	}

	protected abstract void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator);


	@Override
	public void onAppLoading() {
		gameFrame.onAppLoading();
		onSpriteInitializer(gameEngine.getSpriteInitializer());
		onAppStatesConfiguration(gameEngine.getAppStateMachine());
	}

	protected abstract void onSpriteInitializer(SpriteInitializer spriteInitializer);
	protected abstract void onAppStatesConfiguration(AppStateMachine asm);

	@Override
	public void onAppStarted() {
		// hooked
	}

	@Override
	public void onRender(RenderedLayers renderedLayers) {
		gameFrame.onRender(renderedLayers);
	}




}
