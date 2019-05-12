package com.pokewords.framework.views.inputs;

import java.awt.geom.Point2D;

public interface Inputs {
    /**
     * @return true Iff the button is pressed down
     */
    boolean getButtonPressedDown(int key);

    /**
     * @return true Iff the button is being held.
     */
    boolean getButtonBeingHeld(int key);

    /**
     * @return true Iff the button is released up from being held.
     */
    boolean getButtonReleasedUp(int key);

    /**
     * @return The position of the mouse currently.
     */
    Point2D getMousePosition();

    /**
     * @return true Iff the mouse is hit down.
     */
    boolean getMouseHitDown();

    /**
     * @return true Iff the mouse is released up from being held.
     */
    boolean getMouseReleasedUp();

    /**
     * @return true Iff the mouse is being held.
     */
    boolean getMouseBeingHeld();
}
