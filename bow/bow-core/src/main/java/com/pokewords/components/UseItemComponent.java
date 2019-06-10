package com.pokewords.components;

import com.pokewords.framework.sprites.components.CloneableComponent;

public class UseItemComponent extends CloneableComponent {
    private Class<? extends ItemComponent> itemType;

    public Class<? extends ItemComponent> getItemType() {
        return itemType;
    }

    public void setItemType(Class<? extends ItemComponent> itemType) {
        this.itemType = itemType;
    }

    private void use() {
        getOwnerSprite().getComponent(itemType).use();
    }
}
