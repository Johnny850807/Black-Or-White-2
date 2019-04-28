package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

public class MockComponent extends Component {
    protected boolean hasStarted = false;
    protected boolean hasDestroyed = false;
    protected boolean isRunning = false;
    protected int enterCount;
    protected int exitCount;
    protected int updateCount = 0;
    protected Sprite sprite;


    @Override
    public void onAppStateStart(AppStateWorld world) {
        hasStarted = true;
    }

    @Override
    public void onAppStateEnter() {
        enterCount ++;
        isRunning = true;
    }

    @Override
    public void onAppStateExit() {
        exitCount++;
        isRunning = false;
    }

    @Override
    public void onAppStateDestroy() {
        hasDestroyed = true;
    }

    @Override
    public void onUpdate(double tpf) {
        updateCount++;
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    public boolean hasDestroyed() {
        return hasDestroyed;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getEnterCount() {
        return enterCount;
    }

    public int getExitCount() {
        return exitCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
