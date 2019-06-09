package com.pokewords.framework.sprites.components;

import java.awt.*;

/**
 * The component that allows the sprite to listen to the mouse event related to this sprite's area.
 * @author johnny850807 (waterball)
 */
public class MouseListenerComponent extends CloneableComponent {
    protected boolean mouseEntered = false;
    public void onMousePressed(Point mousePositionInWorld, Point mousePositionInArea) {}
    public void onMouseReleased(Point mousePositionInWorld, Point mousePositionInArea) {}
    public void onMouseClicked(Point mousePositionInWorld, Point mousePositionInArea) {}

    public void onMouseEnter(Point mousePositionInWorld, Point mousePositionInArea) {
        mouseEntered = true;
    }

    public void onMouseExit(Point mousePositionInWorld) {
        mouseEntered = false;
    }

    public boolean isMouseEntered() {
        return mouseEntered;
    }
}
