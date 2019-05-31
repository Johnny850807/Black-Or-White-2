package com.pokewords.framework.engine;

import com.pokewords.framework.views.AppView;

public class MockGameEngine implements GameEngine {
    protected AppView appView;
    protected boolean launched;
    protected LoopCounter loopCounter = new LoopCounter();

    @Override
    public void setGameView(AppView appView) {
        this.appView = appView;
    }

    @Override
    public void launchEngine() {
        launched = true;
        appView.onAppInit();
        appView.onAppLoading();
        appView.onAppStarted();
    }

    @Override
    public AppView getAppView() {
        return appView;
    }

    @Override
    public LoopCounter getLoopCounter() {
        return loopCounter;
    }
}
