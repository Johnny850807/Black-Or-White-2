package com.pokewords.framework.sprites.components.mocks;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;

/**
 * @author johnny850807 (waterball)
 */
public class MockFrameStateMachineComponent extends FrameStateMachineComponent implements MockComponent {
    private boolean hasStarted = false;
    private boolean hasDestroyed = false;
    private boolean isRunning = false;
    private int enterCount;
    private int exitCount;
    private int updateCount = 0;

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
    public void onUpdate(double timePerFrame) {
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
