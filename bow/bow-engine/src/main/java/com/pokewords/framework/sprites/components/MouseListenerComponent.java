package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

import java.awt.*;

/**
 * The component that allows the sprite to listen to the mouse event related to this sprite's area.
 * @author johnny850807 (waterball)
 */
public abstract class MouseListenerComponent extends CloneableComponent {
    protected Sprite sprite;
    public Listener listener;

    public MouseListenerComponent(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onComponentAttachedSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Listener getListener() {
        return listener;
    }

    public interface Listener {
        default void onMousePressed(Sprite sprite, Point position) {}
        default void onMouseReleased(Sprite sprite, Point position) {}
        default void onMouseClicked(Sprite sprite, Point position) {}
        default void onMouseDragged(Sprite sprite, Point position) {}
        default void onMouseEnter(Sprite sprite, Point position) {}
        default void onMouseExit(Sprite sprite) {}
    }

    public Sprite getSprite() {
        return sprite;
    }
}
