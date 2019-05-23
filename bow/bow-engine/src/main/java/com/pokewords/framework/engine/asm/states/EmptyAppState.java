package com.pokewords.framework.engine.asm.states;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

public class EmptyAppState extends AppState {
    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) { }

    @Override
    protected void onAppStateEntering() { }

    @Override
    protected void onAppStateExiting() { }

    @Override
    protected void onAppStateDestroying() { }

    @Override
    protected void onAppStateUpdating(double timePerFrame) { }
}
