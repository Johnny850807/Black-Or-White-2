package com.pokewords.framework.engine;

import com.pokewords.framework.views.AppView;

/**
 * @author johnny850807 (waterball)
 */
public interface GameEngine {
    void setGameView(AppView appView);

    void launchEngine();

    AppView getAppView();

    LoopCounter getLoopCounter();
}
