package com.pokewords.framework.sprites.components.mocks;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.ClickableComponent;
import com.pokewords.framework.sprites.components.marks.Shareable;

/**
 * @author johnny850807 (waterball)
 */
public class MockClickableComponent extends ClickableComponent implements Shareable, MockComponent {
    private Sprite sprite;
    private int clickCount = 0;
    private boolean hasStarted = false;
    private boolean hasDestroyed = false;
    private boolean isRunning = false;
    private int enterCount;
    private int exitCount;
    private int updateCount = 0;

    @Override
    public void onClick() {
        clickCount ++;
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
    public Sprite getSprite() {
        return sprite;
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
