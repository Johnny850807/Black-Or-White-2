package com.pokewords.components.actions;

import com.pokewords.components.GunComponent;
import com.pokewords.components.ItemComponent;
import com.pokewords.components.PackageComponent;
import com.pokewords.constants.SpriteTypes;
import com.pokewords.framework.sprites.Sprite;

/**
 * @author johnny850807 (waterball)
 */
public class PickComponent extends ActionComponent {

    @Override
    protected void action() {
        getAttachedWorld().getSpritesCollidedWith(getOwnerSprite())
                            .stream()
                            .filter(s -> s.isType(SpriteTypes.PICKABLE_ITEM))
                            .findFirst()
                            .ifPresent(this::pick);
    }


    private void pick(Sprite pickableSprite) {
        PackageComponent packageComponent = getOwnerSprite().getComponent(PackageComponent.class);
        if (pickableSprite.isType(SpriteTypes.PICKABLE_GUN))
        {
            GunComponent gunComponent = pickableSprite.locateFirstComponent(GunComponent.class);
            getOwnerSprite().addComponent(gunComponent);
            packageComponent.setGunTypeToCurrentIndex(gunComponent.getClass());
        }
        else if (pickableSprite.isType(SpriteTypes.PICKABLE_ITEM))
        {
            ItemComponent itemComponent = pickableSprite.locateFirstComponent(ItemComponent.class);
            getOwnerSprite().addComponent(itemComponent);
            packageComponent.setItemType(itemComponent.getClass());
        }
    }
}
