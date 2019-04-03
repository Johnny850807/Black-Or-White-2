package com.pokewords.framework.views;

import java.awt.geom.Point2D;

//TODO Define sufficient methods
public interface Inputs {

    /**
     * @return true Iff the button is pressed down
     */
    boolean getButtonPressedDown();

    /**
     * Add an event releasing the held button up
     * @param key the button's key
     */
    void onButtonPressedDown(String key);

    /**
     * @return true Iff the button is being held.
     */
    boolean getButtonBeingHeld();

    /**
     * Add an event holding the button
     * @param key the button's key
     */
    void onButtonBeingHeld(String key);

    /**
     * @return true Iff the button is released up from being held.
     */
    boolean getButtonReleasedUp();

    /**
     * Add an event releasing the held button up
     * @param key the button's key
     */
    void onButtonReleasedUp(String key);

    /**
     * @return The position of the mouse currently.
     */
    Point2D getMousePosition();

    /**
     * Add an event moving the mouse (updating the mouse's position)
     * @param point the point where the mouse move to
     */
    void oMouseMoved(Point2D point);

    /**
     * @return true Iff the mouse is hit down.
     */
    boolean getMouseHitDown();

    /**
     * Add an event hitting the mouse down
     */
    void oMouseHitDown();

    /**
     * @return true Iff the mouse is released up from being held.
     */
    boolean getMouseReleasedUp();

    /**
     * Add an event releasing the mouse up
     */
    void oMouseReleasedUp();

    /**
     * @return true Iff the mouse is being held.
     */
    boolean getMouseBeingHeld();

    /**
     * Add an event the mouse being held
     */
    void oMouseBeingHeld();
}
