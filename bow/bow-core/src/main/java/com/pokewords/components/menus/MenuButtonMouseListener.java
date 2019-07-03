package com.pokewords.components.menus;

import com.pokewords.framework.sprites.components.ImageComponent;
import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;

public class MenuButtonMouseListener extends MouseListenerComponent {
    private String buttonName;

    public MenuButtonMouseListener(String buttonName) {
        this.buttonName = buttonName;
    }

    @Override
    public void onMouseEnter(Point mousePositionInWorld, Point mousePositionInArea) {
        super.onMouseEnter(mousePositionInWorld, mousePositionInArea);
        getOwnerSprite().getComponent(ImageComponent.class).setImage("assets/pics/menu/orange" +  buttonName + ".png");
    }

    @Override
    public void onMouseExit(Point mousePositionInWorld) {
        super.onMouseExit(mousePositionInWorld);
        getOwnerSprite().getComponent(ImageComponent.class).setImage("assets/pics/menu/blue" +  buttonName + ".png");
    }
}
