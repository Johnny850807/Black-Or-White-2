package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;

public final class EmptyAppState extends AppState {
    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) { }

    @Override
    protected void onAppStateEntering() { }

    @Override
    protected void onAppStateExiting() { }

    @Override
    protected void onAppStateDestroying() { }

    @Override
    protected void onAppStateUpdating(int timePerFrame) { }
}
