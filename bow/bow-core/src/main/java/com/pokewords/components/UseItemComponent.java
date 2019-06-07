package com.pokewords.components;

import com.pokewords.framework.sprites.components.CloneableComponent;

public class UseItemComponent extends CloneableComponent {
    private Class<? extends ItemComponent> item;

    public void setItem(Class<? extends ItemComponent> item) {
        this.item = item;
    }

    private void use() {

    }
}
