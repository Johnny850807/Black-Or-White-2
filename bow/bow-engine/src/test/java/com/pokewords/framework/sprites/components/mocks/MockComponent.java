package com.pokewords.framework.sprites.components.mocks;

import com.pokewords.framework.engine.listeners.AppStateLifeCycleListener;
import com.pokewords.framework.sprites.Sprite;

/**
 * @author johnny850807 (waterball)
 */
public interface MockComponent extends AppStateLifeCycleListener {
    boolean hasStarted();
    boolean hasDestroyed();
    boolean isRunning();
    int getEnterCount();
    int getExitCount();
    int getUpdateCount();
}
