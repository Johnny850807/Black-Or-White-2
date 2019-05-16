package com.pokewords.framework.engine.asm;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;

/**
 * @author johnny850807 (waterball)
 */
public class MockAppState extends AppState {
    private int onAppStateCreatingCount = 0;
    private int onAppStateEntering = 0;
    private int onAppStateExiting = 0;
    private int onAppStateDestroying = 0;
    private int onAppStateUpdating = 0;

    private boolean isRunning = false;

    @Override
    protected void onAppStateCreating(AppStateWorld appStateWorld) {
        onAppStateCreatingCount++;
    }

    @Override
    protected void onAppStateEntering() {
        onAppStateEntering++;
        isRunning = true;
    }

    @Override
    protected void onAppStateUpdating(int timePerFrame) {
        onAppStateUpdating++;
    }

    @Override
    protected void onAppStateExiting() {
        onAppStateExiting++;
        isRunning = false;
    }

    @Override
    protected void onAppStateDestroying() {
        onAppStateDestroying++;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getOnAppStateCreatingCount() {
        return onAppStateCreatingCount;
    }

    public int getOnAppStateEntering() {
        return onAppStateEntering;
    }

    public int getOnAppStateExiting() {
        return onAppStateExiting;
    }

    public int getOnAppStateDestroying() {
        return onAppStateDestroying;
    }

    public int getOnAppStateUpdating() {
        return onAppStateUpdating;
    }
}
