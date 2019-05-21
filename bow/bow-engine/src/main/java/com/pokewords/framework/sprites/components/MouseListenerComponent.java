package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

import java.awt.*;

/**
 * The component that allows the sprite to listen to the mouse event related to this sprite's area.
 * @author johnny850807 (waterball)
 */
public class MouseListenerComponent extends CloneableComponent {
    protected boolean mouseEntered = false;
    protected Sprite sprite;
    public Listener listener;

    public static MouseListenerComponent ofListener(Listener listener) {
        MouseListenerComponent mouseListenerComponent = new MouseListenerComponent();
        mouseListenerComponent.listener = new Listener() {
            @Override
            public void onMousePressed(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
                listener.onMousePressed(sprite, mousePositionInWorld, mousePositionInArea);
            }

            @Override
            public void onMouseReleased(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
                listener.onMouseReleased(sprite, mousePositionInWorld, mousePositionInArea);
            }

            @Override
            public void onMouseClicked(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
                listener.onMouseClicked(sprite, mousePositionInWorld, mousePositionInArea);
            }

            @Override
            public void onMouseDragged(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
                listener.onMouseDragged(sprite, mousePositionInWorld, mousePositionInArea);
            }

            @Override
            public void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
                mouseListenerComponent.mouseEntered = true;
                listener.onMouseEnter(sprite, mousePositionInWorld, mousePositionInArea);
            }

            @Override
            public void onMouseExit(Sprite sprite, Point mousePositionInWorld) {
                mouseListenerComponent.mouseEntered = false;
                listener.onMouseExit(sprite, mousePositionInWorld);
            }
        };

        return mouseListenerComponent;
    }

    private MouseListenerComponent() { }

    @Override
    public void onComponentAttachedSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Listener getListener() {
        return listener;
    }

    public interface Listener {
        default void onMousePressed(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        default void onMouseReleased(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        default void onMouseClicked(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        default void onMouseDragged(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        default void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        default void onMouseExit(Sprite sprite, Point mousePositionInWorld) {}
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isMouseEntered() {
        return mouseEntered;
    }
}
