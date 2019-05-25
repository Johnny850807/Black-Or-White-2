package com.pokewords.framework.engine;

import com.pokewords.framework.ioc.IocContainer;
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
public class DefaultGameEngine implements GameEngine {
    private InputManager inputManager;
    private AppView gameView;
    private AppStateMachine appStateMachine;
    private final Runnable gameLoopingTask;
    private ScheduledExecutorService scheduler;
    private FpsCounter fpsCounter = new FpsCounter();
    private int loopingCounter = 0;
    private double timePerFrame = 1 / 60.0;  // 60 fps

    public DefaultGameEngine(IocContainer iocContainer, GameWindowsConfigurator gameWindowsConfigurator) {
        this.inputManager = iocContainer.inputManager();
        this.appStateMachine = new AppStateMachine(iocContainer,
                new GameEngineFacade(iocContainer, this, gameWindowsConfigurator));
        this.scheduler = Executors.newScheduledThreadPool(8);
        this.gameLoopingTask = this::gameLooping;
    }


    @Override
    public void setGameView(AppView appView) {
        this.gameView = appView;
    }

    @Override
    public void launchEngine() {
        gameView.onAppInit();
        appStateMachine.trigger(AppStateMachine.EVENT_LOADING);
        scheduler.schedule(gameLoopingTask,  (long) (timePerFrame*1000), TimeUnit.MILLISECONDS);
        gameView.onAppLoading();

		appStateMachine.trigger(AppStateMachine.EVENT_GAME_STARTED);
		gameView.onAppStarted();
    }

    private void gameLooping() {
        try {
            fpsCounter.update();
            loopingCounter = (loopingCounter +1) % Integer.MAX_VALUE;
            inputManager.onUpdate(timePerFrame);
            appStateMachine.onUpdate(timePerFrame);
            gameView.onRender(appStateMachine.getCurrentStateWorld().getRenderedLayers());
            printProfileEveryCertainLoops();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            scheduler.schedule(gameLoopingTask,  (long) (timePerFrame*1000), TimeUnit.MILLISECONDS);
        }
    }

    private void printProfileEveryCertainLoops() {
        if (loopingCounter % 300 == 0)
        {
            System.out.printf("Memory Used: %d MB.\n" , (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) /8/1024/1024);
            System.out.printf("FPS: %f\n", fpsCounter.getAverageFramesPerSecond());
        }
    }

    @Override
    public AppView getAppView() {
        return gameView;
    }

    public AppStateMachine getAppStateMachine() {
        return appStateMachine;
    }


}
