package com.pokewords.framework.sprites.components;

public interface GameLifecycleListener {

    /**
     * triggered in every game-loop
     * @param tpf time per frame
     */
    void onUpdate(double tpf);



}
