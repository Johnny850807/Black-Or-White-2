package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

public class MockComponent implements Component{
    private boolean hasStarted = false;
    private int update = 0;
    private Sprite sprite;

    @Override
    public void onBoundToSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void onStart() {
        hasStarted = true;
    }

    @Override
    public void onUpdate() {
        this.update ++;
    }

}
