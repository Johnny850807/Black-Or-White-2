package com.pokewords.framework.views.inputs;

import com.pokewords.framework.engine.asm.AppState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author johnny850807 (waterball)
 */
public class DefaultInputManager implements InputManager {
    private AppState currentAppState;

    // <current AppState, <key's id, action>>
    private IdentityHashMap<AppState, HashMap<Integer, Runnable>> keyListenersSpace = new IdentityHashMap<>();

    // <current AppState, <mouse event's id, action (given mouse position)>>
    private IdentityHashMap<AppState, HashMap<Integer, Consumer<Point>>> mouseListenersSpace = new IdentityHashMap<>();

    private LinkedList<Integer> keyActions = new LinkedList<>();
    private LinkedList<Integer> mouseActions = new LinkedList<>();

    @Override
    public void onUpdate(double timePerFrame) { }

    @Override
    public void bindAppState(AppState appState) {
        this.currentAppState = appState;

        if (!keyListenersSpace.containsKey(appState))
            keyListenersSpace.put(appState, new HashMap<>());
        if (!mouseListenersSpace.containsKey(appState))
            mouseListenersSpace.put(appState, new HashMap<>());
    }

    @Override
    public void bindKeyEvent(int event, Runnable keyListener) {
        if (keyListenersSpace.get(currentAppState).containsKey(event))
            throw new IllegalArgumentException("The event has already been bound.");
        keyListenersSpace.get(currentAppState).put(event, keyListener);
    }

    @Override
    public void bindMouseEvent(int event, Consumer<Point> mouseListener) {
        if (mouseListenersSpace.get(currentAppState).containsKey(event))
            throw new IllegalArgumentException("The event has already been bound.");
        mouseListenersSpace.get(currentAppState).put(event, mouseListener);
    }

    @Override
    @SuppressWarnings("PrimitiveArrayArgumentToVarargsMethod")
    public int compositeCode(int... events) {
        return Objects.hash(events);
    }

    @Override
    public void onButtonPressedDown(int id) {
        keyActions.add(compositeCode(KeyEvent.KEY_PRESSED, id));
    }

    @Override
    public void onButtonReleasedUp(int id) {
        keyActions.add(compositeCode(KeyEvent.KEY_RELEASED, id));
        keyActions.add(compositeCode(KeyEvent.KEY_TYPED, id));
    }

    @Override
    public void onMouseMoved(Point point) {
        keyActions.add(MouseEvent.MOUSE_MOVED);
    }

    @Override
    public void onMouseHitDown(Point position) {
        keyActions.add(MouseEvent.MOUSE_PRESSED);
    }


    @Override
    public void onMouseReleasedUp() {
        keyActions.add(MouseEvent.MOUSE_RELEASED);
        keyActions.add(MouseEvent.MOUSE_CLICKED);
    }


    public abstract class Action {
        protected long timeStamp;
        protected int id;

        public Action(int id) {
            this.timeStamp = System.currentTimeMillis();
            this.id = id;
        }

        protected abstract void fireAction();

        public long getTimeStamp() {
            return timeStamp;
        }

        public int getId() {
            return id;
        }
    }
    public class KeyAction extends Action {
        private int event;
        private int keyCode;

        public KeyAction(int id, int event, int keyCode) {
            super(id);
            this.event = event;
            this.keyCode = keyCode;
        }

        @Override
        protected void fireAction() {
            keyListenersSpace.get(currentAppState).get(id).run();
        }

        public int getEvent() {
            return event;
        }

        public int getKeyCode() {
            return keyCode;
        }
    }

    public class MouseAction extends Action {
        public Point mousePosition;

        public MouseAction(int id, Point mousePosition) {
            super(id);
            this.mousePosition = mousePosition;
        }

        @Override
        protected void fireAction() {
            mouseListenersSpace.get(currentAppState).get(id).accept(mousePosition);
        }

        public Point getMousePosition() {
            return mousePosition;
        }
    }
}
