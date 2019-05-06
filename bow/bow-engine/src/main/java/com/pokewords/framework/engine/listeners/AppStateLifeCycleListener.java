package com.pokewords.framework.engine.listeners;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;

/**
 * @author johnny850807 (waterball)
 */
public interface AppStateLifeCycleListener extends GameLifecycleListener {
    /**
     * triggered when the AppState is started. (this will be triggered only once for each alive app state)
     * @param world the setup world
     */
    void onAppStateStart(AppStateWorld world);

    /**
     * triggered whenever enters into the app state
     */
    void onAppStateEnter();

    /**
     * triggered whenever exits the app state
     */
    void onAppStateExit();

    /**
     * triggered when the AppState is destroyed
     */
    void onAppStateDestroy();

}
