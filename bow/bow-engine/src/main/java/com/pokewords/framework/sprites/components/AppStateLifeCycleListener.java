package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

public interface AppStateLifeCycleListener {
    /**
     * triggered when the AppState is started.
     * @param world the setup world
     */
    void onStart(AppStateWorld world);

    /**
     * triggered in every game-loop
     * @param tpf time per frame
     */
    void onUpdate(double tpf);

    /**
     * triggered every time the AppState is resumed from other states.
     */
    void onResume();

    /**
     * triggered when the AppState is destroyed
     */
    void onDestroy();

}
