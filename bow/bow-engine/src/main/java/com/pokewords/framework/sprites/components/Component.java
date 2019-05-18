package com.pokewords.framework.sprites.components;


import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;

public abstract class Component implements AppStateLifeCycleListener {


    @Override
    public void onAppStateCreate() {
        //hook
    }

    @Override
    public void onAppStateEnter() {
        //hook
    }

    public void onComponentAdded() {
        //hook
    }

    /**
     * invoked during the component injected, the injected instances are 'Sprite' and 'AppStateWorld'.
     * Where the sprite is the component's owner, and the AppStateWorld is where the Sprite located.
     *
     * Init those objects that have the injected dependencies in this hook method.
     */
    public void onComponentInjected() {
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



    public void onComponentRemoved() {
        //hook
    }
}
