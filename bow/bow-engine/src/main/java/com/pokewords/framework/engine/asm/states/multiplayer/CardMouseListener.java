package com.pokewords.framework.engine.asm.states.multiplayer;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.MouseListenerComponent;

import java.awt.*;

public class CardMouseListener extends MouseListenerComponent.Listener {
    @Override
    public void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
        PlayerComponent playerComponent = sprite.getComponent(PlayerComponent.class);
        PlayerCardFrame frame = playerComponent.getFrame();
        frame.setMouseEntered(true);
    }

    @Override
    public void onMouseExit(Sprite sprite, Point mousePositionInWorld) {
        PlayerComponent playerComponent = sprite.getComponent(PlayerComponent.class);
        PlayerCardFrame frame = playerComponent.getFrame();
        frame.setMouseEntered(false);
    }
}
