package com.pokewords.framework.views.inputs;

import java.awt.*;
import java.awt.geom.Point2D;

//TODO Define sufficient methods
public interface InputManager extends Inputs {

    void onUpdate(double timePerFrame);

    /**
     * Add an event releasing the held button up
     * @param key the button's key
     */
    void onButtonPressedDown(int key);

    /**
     * Add an event holding the button
     * @param key the button's key
     */
    void onButtonBeingHeld(int key);

    /**
     * Add an event releasing the held button up
     * @param key the button's key
     */
    void onButtonReleasedUp(int key);

    /**
     * Add an event moving the mouse (updating the mouse's position)
     * @param point the point where the mouse move to
     */
    void onMouseMoved(Point2D point);

    /**
     * Add an event hitting the mouse down
     */
    void onMouseHitDown(Point position);

    /**
     * Add an event releasing the mouse up
     */
    void onMouseReleasedUp();

    /**
     * Add an event the mouse being held
     */
    void onMouseBeingHeld();
}
