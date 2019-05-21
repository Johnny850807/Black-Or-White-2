package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * The component that allows the sprite to listen to the mouse event related to this sprite's area.
 * @author johnny850807 (waterball)
 */
public abstract class MouseListenerComponent extends CloneableComponent {
    protected @Nullable Point latestMousePositionInArea;
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
        default void onMousePressed(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        default void onMouseReleased(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        default void onMouseClicked(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        default void onMouseDragged(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {}
        default void onMouseEnter(Sprite sprite, Point mousePositionInWorld, Point mousePositionInArea) {
            sprite.getComponent(MouseListenerComponent.class).latestMousePositionInArea = mousePositionInArea;
        }
        default void onMouseExit(Sprite sprite, Point mousePositionInWorld) {
            sprite.getComponent(MouseListenerComponent.class).latestMousePositionInArea = null;
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Nullable
    public Point getLatestMousePositionInArea() {
        return latestMousePositionInArea;
    }
}
