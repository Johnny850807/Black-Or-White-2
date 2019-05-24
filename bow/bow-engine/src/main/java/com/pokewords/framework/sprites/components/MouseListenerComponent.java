package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;

import java.awt.*;

/**
 * The component that allows the sprite to listen to the mouse event related to this sprite's area.
 * @author johnny850807 (waterball)
 */
public class MouseListenerComponent extends CloneableComponent {
    protected Sprite sprite;
    protected boolean mouseEntered = false;
    protected Listener wrappedListener;
    protected WrapperListener listener;

    public static MouseListenerComponent ofListener(Listener listener) {
        MouseListenerComponent mouseListenerComponent = new MouseListenerComponent();
        mouseListenerComponent.wrappedListener = listener;
        mouseListenerComponent.listener = new WrapperListener(mouseListenerComponent, listener);
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

    public abstract static class Listener implements Cloneable {
        public void onMousePressed(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        public void onMouseReleased(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        public void onMouseClicked(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        public void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        public void onMouseExit(Sprite sprite, Point mousePositionInWorld) { }
        public Listener clone() {
            try {
                return (Listener) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new Error(e);
            }
        }
    }

    private static class WrapperListener extends MouseListenerComponent.Listener {
        private MouseListenerComponent mouseListenerComponent;
        private Listener listener;

        public WrapperListener(MouseListenerComponent mouseListenerComponent, Listener listener) {
            this.mouseListenerComponent = mouseListenerComponent;
            this.listener = listener;
        }

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
        public void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
            mouseListenerComponent.mouseEntered = true;
            listener.onMouseEnter(sprite, mousePositionInWorld, mousePositionInArea);
        }

        @Override
        public void onMouseExit(Sprite sprite, Point mousePositionInWorld) {
            mouseListenerComponent.mouseEntered = false;
            listener.onMouseExit(sprite, mousePositionInWorld);
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isMouseEntered() {
        return mouseEntered;
    }

    @Override
    public MouseListenerComponent clone() {
        MouseListenerComponent clone = (MouseListenerComponent) super.clone();
        clone.wrappedListener = wrappedListener.clone();
        clone.listener = new WrapperListener(clone, wrappedListener);
        return clone;
    }
}
