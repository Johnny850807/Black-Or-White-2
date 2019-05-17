package com.pokewords.framework.views;

import com.pokewords.framework.engine.GameEngine;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.windows.GameFrame;
import com.pokewords.framework.views.windows.GameFrameWindowsConfigurator;
import com.pokewords.framework.views.windows.GamePanel;
import com.pokewords.framework.views.windows.GameWindowsConfigurator;

/**
 * @author johnny850807 (waterball), shawn
 */
public abstract class GameApplication implements AppView {
	private GameEngine gameEngine;
	private GameFrame gameFrame;
	private GameWindowsConfigurator gameWindowsConfigurator;

    public GameApplication(IocFactory iocFactory) {
    	InputManager inputManager = iocFactory.inputManager();
		gameFrame = new GameFrame(new GamePanel(inputManager));
		gameWindowsConfigurator = new GameFrameWindowsConfigurator(gameFrame);
        gameEngine = new GameEngine(iocFactory, inputManager, gameWindowsConfigurator);
        gameEngine.setGameView(this);
    }

    /**
	 * Launch the game application.
	 */
	public void launch() {
		gameEngine.launchEngine();
	}

	/**
	 * This method is invoked during the app's initiation.
	 */
	@Override
	public void onAppInit() {
		gameFrame.onAppInit();
		onGameWindowsConfiguration(gameWindowsConfigurator);
	}

	protected abstract void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator);


	/**
	 * This method is invoked during the ProgressBarLoadingState.
	 *
	 * onAppLoading() will be executed asynchronously by the GameEngine,
	 * at the moment executing this method, the GameEngine will render the loading-state.
	 *
	 * Hence, it's fine to put heavy tasks as many as possible within this method.
	 */
	@Override
	public void onAppLoading() {
		gameFrame.onAppLoading();
		onSpriteDeclaration(gameEngine.getSpriteInitializer());
		onAppStatesConfiguration(gameEngine.getAppStateMachine());
	}

	protected abstract void onSpriteDeclaration(SpriteInitializer spriteInitializer);
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
