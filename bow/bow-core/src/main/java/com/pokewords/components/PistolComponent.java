package com.pokewords.components;

import com.pokewords.constants.SpriteTypes;
import com.pokewords.framework.sprites.Sprite;

public class PistolComponent extends GunComponent {
    private static final int SHOOT_INTERVAL = 45;
    private boolean available = true;

    @Override
    public void shootIfAvailable() {
        if (available) {
            shoot();
            available = false;
            getGameEngineFacade().addLoopCountdownHook(SHOOT_INTERVAL, () -> setAvailable(true));
        }
    }

    private void shoot() {
        Sprite bluePellet = getGameEngineFacade().createSprite(SpriteTypes.BULLET_BLUE_PELLET);
        getAttachedWorld().spawn(bluePellet);
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
