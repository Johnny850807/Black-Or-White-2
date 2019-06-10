package com.pokewords.components;

import com.pokewords.components.actions.ShootComponent;
import com.pokewords.framework.engine.exceptions.GameEngineException;
import com.pokewords.framework.sprites.components.CloneableComponent;

import java.util.Arrays;

/**
 * @author johnny850807 (waterball)
 */
public class PackageComponent extends CloneableComponent {
    private final int maxGunSize;
    private int currentChosenGunIndex = 0;
    private Class<? extends GunComponent>[] guns;
    private Class<? extends ItemComponent> itemType;

    @SuppressWarnings("unchecked")
    public PackageComponent(int maxGunSize) {
        this.maxGunSize = maxGunSize;
        this.guns = new Class[maxGunSize];
    }

    /**
     * put the gun at the current chosen gun index
     */
    public void setGunTypeToCurrentIndex(Class<? extends GunComponent> gun) {
        guns[currentChosenGunIndex] = gun;
    }

    public GunComponent getGun(int gunIndex) {
        return getOwnerSprite().getComponent(guns[gunIndex]);
    }

    public GunComponent getCurrentGun() {
        return getGun(currentChosenGunIndex);
    }

    public void setCurrentChosenGunIndex(int currentChosenGunIndex) {
        if (currentChosenGunIndex < 0 || currentChosenGunIndex >= maxGunSize)
            throw new GameEngineException(String.format("Invalid currentChosenGunIndex set %d.", currentChosenGunIndex));

        this.currentChosenGunIndex = currentChosenGunIndex;
        getOwnerSprite().getComponentOptional(ShootComponent.class)
                .ifPresent(shootComponent -> shootComponent.setGunType(guns[currentChosenGunIndex]));
    }

    public int getCurrentChosenGunIndex() {
        return currentChosenGunIndex;
    }

    public void setItemType(Class<? extends ItemComponent> itemType) {
        this.itemType = itemType;
        getOwnerSprite().getComponentOptional(UseItemComponent.class)
                .ifPresent(useItemComponent -> useItemComponent.setItemType(itemType));
    }

    public ItemComponent getItem() {
        return getOwnerSprite().getComponent(itemType);
    }

    @Override
    public PackageComponent clone() {
        PackageComponent clone = (PackageComponent) super.clone();
        clone.guns = Arrays.copyOf(guns, maxGunSize);
        return clone;
    }
}
