package com.pokewords.framework.sprites.components;


import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.Sprite;

public abstract class Component implements AppStateLifeCycleListener {


    @Override
    public void onAppStateCreate() {
        //hook
    }

    @Override
    public void onAppStateEnter() {
        //hook
    }

    public void onComponentAttached(Sprite sprite) {
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
