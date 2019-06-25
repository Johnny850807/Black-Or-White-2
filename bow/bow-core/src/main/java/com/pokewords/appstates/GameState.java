package com.pokewords.appstates;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

public class GameState extends AppState {
    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        bindMouseMovedAction(System.out::println);
    }

    @Override
    protected void onAppStateEntering() { }

    @Override
    protected void onAppStateExiting() {

    }

    @Override
    protected void onAppStateDestroying() {

    }

    @Override
    protected void onAppStateUpdating(double timePerFrame) {

    }
}
