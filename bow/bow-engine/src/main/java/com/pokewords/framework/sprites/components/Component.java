package com.pokewords.framework.sprites.components;


import com.pokewords.framework.engine.GameEngineFacade;
import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;

/**
 * A Component is a set of behaviors or data of a Sprite.
 * Component will not be cloned, hence it should be functional and immutable.
 * if you want your component to be cloned, use CloneableComponent instead.
 * @author johnny850807 (waterball)
 */
public abstract class Component implements AppStateLifeCycleListener {
    protected GameEngineFacade gameEngineFacade;

    public void setGameEngineFacade(GameEngineFacade gameEngineFacade) {
        this.gameEngineFacade = gameEngineFacade;
    }

    @Override
    public void onAppStateCreate() {
        //hook
    }

    @Override
    public void onAppStateEnter() {
        //hook
    }

    @Override
    public void onUpdate(double timePerFrame) {
        //hook
    }

    @Override
    public void onAppStateExit() {
        //hook
    }

    @Override
    public void onAppStateDestroy() {
        //hook
    }

    public GameEngineFacade getGameEngineFacade() {
        return gameEngineFacade;
    }
}
