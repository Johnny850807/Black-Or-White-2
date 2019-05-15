package com.pokewords.framework.sprites.components.mocks;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.Component;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

public class MockComponentImp extends Component implements MockComponent {
    private Sprite sprite;  // this field is expected to be injected
    private boolean hasStarted = false;
    private boolean hasDestroyed = false;
    private boolean isRunning = false;
    private int enterCount;
    private int exitCount;
    private int updateCount = 0;

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void onAppStateCreate() {
        hasStarted = true;
    }

    @Override
    public void onAppStateEnter() {
        enterCount++;
        isRunning = true;
    }

    @Override
    public void onAppStateExit() {
        assert isRunning : "onAppStateEnter() should be triggered before onAppStateExit().";
        exitCount++;
        isRunning = false;
    }

    @Override
    public void onAppStateDestroy() {
        assert !isRunning : "onAppStateExit() should be triggered before onAppStateDestroy().";
        hasDestroyed = true;
    }

    @Override
    public void onUpdate(int timePerFrame) {
        assert isRunning : "onUpdate() should be triggered after onAppStateEnter().";
        updateCount++;
    }

    @Override
    public boolean hasStarted() {
        return hasStarted;
    }

    @Override
    public boolean hasDestroyed() {
        return hasDestroyed;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getEnterCount() {
        return enterCount;
    }

    @Override
    public int getExitCount() {
        return exitCount;
    }

    @Override
    public int getUpdateCount() {
        return updateCount;
    }


}
