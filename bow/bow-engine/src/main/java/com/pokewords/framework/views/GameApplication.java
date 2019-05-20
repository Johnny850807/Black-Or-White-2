package com.pokewords.framework.views;

import com.pokewords.framework.commons.utils.ThreadUtility;
import com.pokewords.framework.engine.GameEngine;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.inputs.InputManager;
import com.pokewords.framework.views.sound.SwingSoundPlayer;
import com.pokewords.framework.views.windows.*;

/**
 * @author johnny850807 (waterball), shawn
 */
public abstract class GameApplication implements AppView {
	private GameEngine gameEngine;
	private GameFrame gameFrame;
	private GameWindowsConfigurator gameWindowsConfigurator;
	private SoundPlayer soundPlayer;

    public GameApplication(IocFactory iocFactory) {
    	InputManager inputManager = iocFactory.inputManager();
		gameFrame = new GameFrame(new GamePanel(), inputManager);
		soundPlayer = new SwingSoundPlayer();
		gameWindowsConfigurator = new GameFrameWindowsConfigurator(gameFrame);
        gameEngine = new GameEngine(iocFactory, inputManager, gameWindowsConfigurator, soundPlayer);
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
		ThreadUtility.delay(getLoadingStateDelayTime()); //delay on purpose to show loading scene
		onSpriteDeclaration(gameEngine.getSpriteInitializer());
		onSoundPlayerConfiguration(soundPlayer);
		onAppStatesConfiguration(gameEngine.getAppStateMachine());
	}

	protected int getLoadingStateDelayTime() {
		return 3000;
	}
	protected abstract void onSpriteDeclaration(SpriteInitializer spriteInitializer);

	protected void onSoundPlayerConfiguration(SoundPlayer soundPlayer) {
		//hook
	}
	protected abstract void onAppStatesConfiguration(AppStateMachine asm);

	public GameWindowDefinition getGameWindowDefinition() {
		return gameWindowsConfigurator.getGameWindowDefinition();
	}

	@Override
	public void onAppStarted() {
		// hooked
	}

	@Override
	public void onRender(RenderedLayers renderedLayers) {
		gameFrame.onRender(renderedLayers);
	}

	public GameWindowsConfigurator getGameWindowsConfigurator() {
		return gameWindowsConfigurator;
	}

	public SoundPlayer getSoundPlayer() {
		return soundPlayer;
	}
}
