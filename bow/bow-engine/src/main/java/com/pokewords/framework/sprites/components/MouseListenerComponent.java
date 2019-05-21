package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * The component that allows the sprite to listen to the mouse event related to this sprite's area.
 * @author johnny850807 (waterball)
 */
public class MouseListenerComponent extends CloneableComponent {
    protected boolean mouseEntered = false;
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

    public abstract static class Listener {
        public void onMousePressed(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        public void onMouseReleased(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        public void onMouseClicked(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        public void onMouseDragged(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        public void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
            sprite.getComponent(MouseListenerComponent.class).mouseEntered = true;
        }
        public void onMouseExit(Sprite sprite, Point mousePositionInWorld) {
            sprite.getComponent(MouseListenerComponent.class).mouseEntered = false;
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isMouseEntered() {
        return mouseEntered;
    }
}
