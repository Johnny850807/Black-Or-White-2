package com.pokewords.framework.views;

import com.pokewords.framework.commons.utils.ThreadUtility;
import com.pokewords.framework.engine.DefaultGameEngine;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocContainer;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.windows.*;

/**
 * @author johnny850807 (waterball), shawn
 */
public abstract class GameApplication implements AppView {
	private DefaultGameEngine gameEngine;
	private GameFrame gameFrame;
	private GameWindowsConfigurator gameWindowsConfigurator;
	private SpriteInitializer spriteInitializer;
	private SoundPlayer soundPlayer;

    public GameApplication(IocContainer iocContainer) {
    	this.spriteInitializer = iocContainer.spriteInitializer();
		this.gameFrame = new GameFrame(new GamePanel(iocContainer.inputManager()));
		this.soundPlayer = iocContainer.soundPlayer();
		this.gameWindowsConfigurator = new GameFrameWindowsConfigurator(gameFrame);
		this.gameEngine = new DefaultGameEngine(iocContainer, gameWindowsConfigurator);
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
	 * This method is invoked during the LoadingState.
	 *
	 * onAppLoading() will be executed asynchronously by the DefaultGameEngine,
	 * at the moment executing this method, the DefaultGameEngine will render the loading-state.
	 *
	 * Hence, it's fine to put heavy tasks as many as possible within this method.
	 */
	@Override
	public void onAppLoading() {
		gameFrame.onAppLoading();
		ThreadUtility.delay(getLoadingStateDelayTime()); //delay on purpose to show loading scene
		onSpriteDeclaration(spriteInitializer);
		onSoundPlayerConfiguration(soundPlayer);
		onAppStatesConfiguration(gameEngine.getAppStateMachine());
	}

	protected int getLoadingStateDelayTime() {
		return 1800;
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
