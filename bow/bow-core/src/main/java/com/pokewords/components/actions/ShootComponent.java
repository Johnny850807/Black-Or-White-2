package com.pokewords.components.actions;

import com.pokewords.components.GunComponent;
import com.pokewords.components.actions.ActionComponent;

/**
 * @author johnny850807 (waterball)
 */
public class ShootComponent extends ActionComponent {
    private Class<? extends GunComponent> gunType;

    public Class<? extends GunComponent> getGunType() {
        return gunType;
    }

    public void setGunType(Class<? extends GunComponent> gunType) {
        this.gunType = gunType;
    }

    @Override
    public void action() {
        getOwnerSprite().getComponent(gunType).shootIfAvailable();
    }
}
