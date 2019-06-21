package com.pokewords.components.actions;

import com.pokewords.components.ItemComponent;

public class UseItemComponent extends ActionComponent {
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

    @Override
    public void action() {

    }
}
