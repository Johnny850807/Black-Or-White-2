package com.pokewords.components;

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
    protected void action() {
        getOwnerSprite().getComponent(gunType).shootIfAvailable();
    }
}
