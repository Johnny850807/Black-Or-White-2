package com.pokewords.framework.sprites.components.mocks;

import com.pokewords.framework.sprites.components.PropertiesComponent;

/**
 * @author johnny850807 (waterball)
 */
public class MockPositionListener implements PropertiesComponent.PositionListener {
    private int triggerCount = 0;
    private int x = -1;
    private int y = -1;

    @Override
    public void onPositionUpdated(int x, int y) {
        triggerCount ++;
        this.x = x;
        this.y = y;
    }

    public int getTriggerCount() {
        return triggerCount;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
