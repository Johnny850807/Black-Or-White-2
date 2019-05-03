package com.pokewords.framework.views;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.pokewords.framework.engine.GameEngine;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.ioc.ReleaseIocFactory;
import com.pokewords.framework.sprites.SpriteInitializer;
import com.pokewords.framework.sprites.components.Frame;

/**
 * @author shawn
 * TODO windows exit
 * TODO GameApplication should give right of access to the GameEngine and its relevant classes (builders, ASM...)
 * TODO onAppInit - windows configurations
 * TODO onAppLoading -Template methods
 */
public abstract class GameApplication implements AppView {
	private IocFactory iocFactory;
	private GameEngine gameEngine;
	private GameFrame gameFrame;
	private SpriteInitializer spriteInitializer;

    public GameApplication(IocFactory iocFactory) {
        this.iocFactory = iocFactory;
        gameFrame = new GameFrame(new GamePanel());
    }

    /**
	 * Launch the game application.
	 */
	public void launch() {
		gameEngine = new GameEngine(iocFactory);
		gameEngine.setGameView(this);
		gameEngine.launchEngine();
	}

	@Override
	public void onAppInit() {
		gameFrame.onAppInit();
		onGameWindowsConfiguration(new GameWindowsConfigurator(gameFrame));
	}

	protected abstract void onGameWindowsConfiguration(GameWindowsConfigurator gameWindowsConfigurator);


	@Override
	public void onAppLoading() {
		gameFrame.onAppLoading();
		onSpriteInitializer(spriteInitializer = new SpriteInitializer(iocFactory));
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
