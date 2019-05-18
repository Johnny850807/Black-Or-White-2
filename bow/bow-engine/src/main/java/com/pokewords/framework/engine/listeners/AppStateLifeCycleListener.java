package com.pokewords.framework.engine.listeners;

/**
 * @author johnny850807 (waterball)
 */
public interface AppStateLifeCycleListener extends GameLoopingListener {
    /**
     * triggered when the AppState is started. (this will be triggered only once for each alive app state)
     */
    void onAppStateCreate();

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
