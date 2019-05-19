package com.pokewords.framework.views.inputs;

import com.pokewords.framework.engine.asm.AppState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
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

    private final Object keyLock = new Object();
    private final Object mouseLock = new Object();
    private List<KeyAction> keyActions = new LinkedList<>();
    private List<MouseAction> mouseActions = new LinkedList<>();

    @Override
    public void onUpdate(double timePerFrame) {
        if (currentAppState != null)
        {
            synchronized (keyLock) {
                keyActions.forEach(KeyAction::fireAction);
                keyActions.clear();
            }

            synchronized (mouseLock) {
                mouseActions.forEach(MouseAction::fireAction);
                mouseActions.clear();
            }
        }
    }

    @Override
    public void bindAppState(AppState appState) {
        this.currentAppState = appState;

        if (!keyListenersSpace.containsKey(appState))
            keyListenersSpace.put(appState, new HashMap<>());
        if (!mouseListenersSpace.containsKey(appState))
            mouseListenersSpace.put(appState, new HashMap<>());
    }

    @Override
    public void unbind() {
        this.currentAppState = null;
    }

    @Override
    public void bindKeyEvent(AppState appState, int event, Runnable keyListener) {
        if (keyListenersSpace.get(appState).containsKey(event))
            throw new IllegalArgumentException("The event has already been bound.");
        keyListenersSpace.get(appState).put(event, keyListener);
    }

    @Override
    public void bindMouseEvent(AppState appState, int event, Consumer<Point> mouseListener) {
        if (mouseListenersSpace.get(appState).containsKey(event))
            throw new IllegalArgumentException("The event has already been bound.");
        mouseListenersSpace.get(appState).put(event, mouseListener);
    }

    @Override
    @SuppressWarnings("PrimitiveArrayArgumentToVarargsMethod")
    public int compositeCode(int... events) {
        return Objects.hash(events);
    }

    @Override
    public void onButtonPressedDown(int id) {
        synchronized (keyLock) {
            keyActions.add(new KeyAction(KeyEvent.KEY_PRESSED, id));
        }
    }

    @Override
    public void onButtonReleasedUp(int id) {
        synchronized (keyLock) {
            keyActions.add(new KeyAction(KeyEvent.KEY_RELEASED, id));
            keyActions.add(new KeyAction(KeyEvent.KEY_TYPED, id));
        }
    }

    @Override
    public void onMouseMoved(Point point) {
        synchronized (mouseLock) {
            mouseActions.add(new MouseAction(MouseEvent.MOUSE_MOVED, point));
        }
    }

    @Override
    public void onMouseHitDown(Point position) {
        synchronized (mouseLock) {
            mouseActions.add(new MouseAction(MouseEvent.MOUSE_PRESSED, position));
        }
    }


    @Override
    public void onMouseReleasedUp(Point position) {
        synchronized (mouseLock) {
            mouseActions.add(new MouseAction(MouseEvent.MOUSE_RELEASED, position));
            mouseActions.add(new MouseAction(MouseEvent.MOUSE_CLICKED, position));
        }
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

        public KeyAction(int event, int keyCode) {
            super(compositeCode(event, keyCode));
            this.event = event;
            this.keyCode = keyCode;
        }

        @Override
        protected void fireAction() {
            if (keyListenersSpace.get(currentAppState).containsKey(id))
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
            if (mouseListenersSpace.get(currentAppState).containsKey(id))
                mouseListenersSpace.get(currentAppState).get(id).accept(mousePosition);
        }

        public Point getMousePosition() {
            return mousePosition;
        }
    }
}
