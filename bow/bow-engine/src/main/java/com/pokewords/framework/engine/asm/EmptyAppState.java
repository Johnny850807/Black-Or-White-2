package com.pokewords.framework.engine.asm;

public final class EmptyAppState extends AppState {
    @Override
    public void onAppStateEnter() {}

    @Override
    public void onAppStateExit() {}

    @Override
    public void onAppStateDestroy() {}

    @Override
    public void onUpdate(double timePerFrame) {}
}
