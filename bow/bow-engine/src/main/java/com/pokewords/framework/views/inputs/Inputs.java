package com.pokewords.framework.views.inputs;

import java.awt.*;
import java.util.function.Consumer;

public interface Inputs {
    /**
     * Bind an specific input event a keyListener.
     * @param event the input event (button, mouse,... of some integer constants space)
     * @param keyListener the keyListener task will be triggered when the event occurs
     */
    void bindKeyEvent(int event, Runnable keyListener);


    /**
     * Bind an specific input event a mouseListener (given mouse's position).
     * @param event the input event (button, mouse,... of some integer constants space)
     * @param mouseListener the mouseListener task will be triggered given the mouse's position when the event occurs
     */
    void bindMouseEvent(int event, Consumer<Point> mouseListener);

    /**
     * @param events a sequence of events happen together
     * @return the compositeCode event's id
     */
    int compositeCode(int ...events);
}
