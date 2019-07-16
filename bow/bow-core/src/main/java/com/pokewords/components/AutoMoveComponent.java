package com.pokewords.components;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.components.CloneableComponent;

public class AutoMoveComponent extends CloneableComponent {
    private int movementInterval;

    public AutoMoveComponent(int movementInterval) {
        this.movementInterval = movementInterval;
    }

    @Override
    public void onComponentAttachedWorld(AppStateWorld appStateWorld) {
        super.onComponentAttachedWorld(appStateWorld);
        getGameEngineFacade().addLoopCountdownHook(movementInterval, movingHook);
    }

    private Runnable movingHook = this::move;


    private void move() {

        getGameEngineFacade().addLoopCountdownHook(movementInterval, movingHook);
    }

    @Override
    public void onComponentDetachedWorld(AppStateWorld appStateWorld) {
        super.onComponentDetachedWorld(appStateWorld);
        getGameEngineFacade().removeLoopCountdownHook(movingHook);
    }
}
