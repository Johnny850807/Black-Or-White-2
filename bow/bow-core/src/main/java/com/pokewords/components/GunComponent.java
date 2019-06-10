package com.pokewords.components;

import com.pokewords.framework.sprites.components.CloneableComponent;

public abstract class GunComponent extends CloneableComponent {
    protected int bullets;

    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }

    public abstract void shootIfAvailable();
}
