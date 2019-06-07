package com.pokewords.framework.views.inputs;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.listeners.GameLoopingListener;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.function.Consumer;

/**
 * The inputManager serves as the middleware between the input triggering source and the client's input listeners.
 * It queues all the input triggered from certain source (e.g. Swing) and fire all the queued input events
 * when its onUpdate() is invoked.
 *
 * InputManager is AppState-awareness, it separates every appState's listeners, so only the bound state
 * will be triggered. (Use bindAppState() and unbind() to set the listening appState.)
 *
 * There are tow kinds of binding, root and non-root.
 * Root binding is always triggered regardless of which appState is currently bound.
 * Non-root binding is exclusively triggered for the currently bound appState.
 * @author johnny850807 (waterball)
 */
public interface InputManager extends GameLoopingListener {

    /**
     * Bind an specific key event a keyListener.
     * The listener will be triggered regardless of what appState is currently bound.
     * @param event the input event (button, mouse,... of some integer constants space)
     * @param keyListener the keyListener task will be triggered when the event occurs
     */
    void bindKeyEventForRoot(int event, Consumer<Integer> keyListener);


    /**
     * Bind an specific mouse event a mouseListener.
     * The listener will be triggered regardless of what appState is currently bound.
     * @param event the input event (button, mouse,... of some integer constants space)
     * @param mouseListener the mouseListener task will be triggered given the mouse's position when the event occurs
     */
    void bindMouseEventForRoot(int event, Consumer<Point> mouseListener);

    /**
     * Bind an specific key event a keyListener (given key code) for an AppState.
     * The listener will be triggered only when the given appState is bound using #bindAppState(appState).
     * @param event the key event
     * @param keyListener the keyListener task will be triggered when the event occurs
     */
    void bindKeyEvent(AppState appState, int event, Consumer<Integer> keyListener);


    /**
     * Bind an specific mouse event a mouseListener (given mouse's position) for an AppState.
     * The listener will be triggered only when the given appState is bound using #bindAppState(appState).
     * @param event the mouse event
     * @param mouseListener the mouseListener task will be triggered given the mouse's position when the event occurs
     */
    void bindMouseEvent(AppState appState, int event, Consumer<Point> mouseListener);

    /**
     * @param events a sequence of events happen together
     * @return the compositeCode event's event
     */
    int compositeCode(int ...events);

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
     * @param keyCode the button's event
     */
    void onButtonPressedDown(int keyCode);

    /**
     * Add an event releasing the held button up
     * @param keyCode the button's event
     */
    void onButtonReleasedUp(int keyCode);

    /**
     * Add an event moving the mouse (updating the mouse's position)
     * @param point the point where the mouse getMovement to
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
