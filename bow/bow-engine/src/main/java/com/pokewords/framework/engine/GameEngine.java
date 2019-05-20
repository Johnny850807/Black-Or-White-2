package com.pokewords.framework.engine;

import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.factories.SpriteInitializer;
import com.pokewords.framework.views.AppView;
import com.pokewords.framework.engine.asm.AppStateMachine;
import com.pokewords.framework.views.SoundPlayer;
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
    private AppStateMachine appStateMachine;
    private final Runnable gameLoopingTask;
    private ScheduledExecutorService scheduler;
    private int counter = 0;
    private double timePerFrame = 1 / 60.0;  // 60 fps

    public GameEngine(IocFactory iocFactory, InputManager inputManager, GameWindowsConfigurator gameWindowsConfigurator, SoundPlayer soundPlayer) {
        this.iocFactory = iocFactory;
        this.inputManager = inputManager;
        this.spriteInitializer = new SpriteInitializer(iocFactory);
        this.appStateMachine = new AppStateMachine(inputManager, spriteInitializer, gameWindowsConfigurator, soundPlayer);
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.gameLoopingTask = this::gameLooping;
    }

    private void gameLooping() {
        try {
            counter = (counter +1) % 300;
            inputManager.onUpdate(timePerFrame);
            appStateMachine.onUpdate(timePerFrame);
            gameView.onRender(appStateMachine.getCurrentStateWorld().getRenderedLayers());

            if (counter == 0)
                System.out.printf("Memory Used: %d\n" , Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void setGameView(AppView gameView) {
        this.gameView = gameView;
    }

    public void launchEngine() {
        gameView.onAppInit();
        appStateMachine.trigger(AppStateMachine.EVENT_LOADING);
        scheduler.scheduleAtFixedRate(gameLoopingTask, 0, (long) (timePerFrame*1000), TimeUnit.MILLISECONDS);
        gameView.onAppLoading();

		appStateMachine.trigger(AppStateMachine.EVENT_GAME_STARTED);
		gameView.onAppStarted();
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
