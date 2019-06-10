package com.pokewords.framework.engine.asm.states.multiplayer;

import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;

public class CardMouseListenerComponent extends MouseListenerComponent {
    @Override
    public void onMouseEnter(Point mousePositionInWorld, Point mousePositionInArea) {
        super.onMouseEnter(mousePositionInWorld, mousePositionInArea);
        PlayerComponent playerComponent = getOwnerSprite().getComponent(PlayerComponent.class);
        PlayerCardFrame frame = playerComponent.getFrame();
        frame.setMouseEntered(true);
    }

    @Override
    public void onMouseExit(Point mousePositionInWorld) {
        super.onMouseExit(mousePositionInWorld);
        PlayerComponent playerComponent = getOwnerSprite().getComponent(PlayerComponent.class);
        PlayerCardFrame frame = playerComponent.getFrame();
        frame.setMouseEntered(false);
    }
}
