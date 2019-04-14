package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

public interface AppStateLifeCycleListener extends GameLifecycleListener{
    /**
     * triggered when the AppState is started. (this will be triggered only once for each alive app state)
     * @param world the setup world
     */
    void onAppStateInit(AppStateWorld world);

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