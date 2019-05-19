package com.pokewords.framework.views.inputs;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.listeners.GameLoopingListener;

import java.awt.*;
import java.awt.geom.Point2D;

public interface InputManager extends Inputs, GameLoopingListener {

    /**
     * Bind and enable an appState to listen to the input events.
     * InputManager will save the input event listeners bound by each AppState,
     * but it should only fire the events that the current AppState's interested.
     * So this method is necessary to be invoked anytime the current AppState is changed, InputManager
     * will only trigger the events relevant to the bound AppState.
     * @param appState the entering appState
     */
    void bindAppState(AppState appState);

    /**
     * To disable all events listening until the next appState binding.
     * This should be invoked during an AppState exiting.
     */
    void unbind();

    /**
     * Add an event releasing the held button up
     * @param id the button's id
     */
    void onButtonPressedDown(int id);

    /**
     * Add an event releasing the held button up
     * @param id the button's id
     */
    void onButtonReleasedUp(int id);

    /**
     * Add an event moving the mouse (updating the mouse's position)
     * @param point the point where the mouse move to
     */
    void onMouseMoved(Point point);

    /**
     * Add an event hitting the mouse down
     */
    void onMouseHitDown(Point position);

    /**
     * Add an event releasing the mouse up
     */
    void onMouseReleasedUp(Point position);

    /**
     * Add an event dragging the mouse
     */
    void onMouseDragged(Point position);

}
