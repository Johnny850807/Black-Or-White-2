package com.pokewords.framework.engine.listeners;

public interface GameLifecycleListener {

    /**
     * triggered in every game-loop
     * @param timePerFrame time per frame
     */
    void onUpdate(int timePerFrame);



}
