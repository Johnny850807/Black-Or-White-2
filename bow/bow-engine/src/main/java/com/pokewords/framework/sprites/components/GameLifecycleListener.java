package com.pokewords.framework.sprites.components;

public interface GameLifecycleListener {

    /**
     * triggered in every game-loop
     * @param timePerFrame time per frame
     */
    void onUpdate(int timePerFrame);



}
