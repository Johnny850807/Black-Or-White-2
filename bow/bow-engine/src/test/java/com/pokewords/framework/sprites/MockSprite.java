package com.pokewords.framework.sprites;

import com.pokewords.framework.sprites.components.PropertiesComponent;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

public class MockSprite extends Sprite{
    public int onAppStateStartCount = 0;
    public int onAppStateEnterCount = 0;
    public int onAppStateExitCount = 0;
    public int onAppStateDestroyCount = 0;
    public int onUpdateCount = 0;

    protected MockSprite(String type) {
        super(type);
    }

    public MockSprite(PropertiesComponent propertiesComponent) {
        super(propertiesComponent);
    }

    @Override
    public void onAppStateStart(AppStateWorld world) {
        onAppStateStartCount ++;
    }

    @Override
    public void onAppStateEnter() {
        onAppStateEnterCount ++;
    }

    @Override
    public void onAppStateExit() {
        onAppStateExitCount ++;
    }

    @Override
    public void onAppStateDestroy() {
        onAppStateDestroyCount ++;
    }

    @Override
    public void onUpdate(int timePerFrame) {
        onUpdateCount ++;
    }
}
