package com.pokewords.framework.engine.listeners;

public interface GameLoopingListener {

    /**
     * triggered in every game-loop
     * @param timePerFrame time per frame (second)
     */
    void onUpdate(double timePerFrame);



}
