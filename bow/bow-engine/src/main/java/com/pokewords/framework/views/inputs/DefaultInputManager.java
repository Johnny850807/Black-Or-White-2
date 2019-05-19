package com.pokewords.framework.views.inputs;

import com.pokewords.framework.engine.asm.AppState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * TODO make memory-efficient (use action-pool)
 * TODO check synchronization issue
 * @author johnny850807 (waterball)
 */
public class DefaultInputManager implements InputManager {
    private AppState currentAppState;

    // <current AppState, <key's id, action>>
    private Map<AppState, HashMap<Integer, Runnable>> keyListenersSpace = Collections.synchronizedMap(new IdentityHashMap<>());

    // <current AppState, <mouse event's id, action (given mouse position)>>
    private Map<AppState, HashMap<Integer, Consumer<Point>>> mouseListenersSpace = Collections.synchronizedMap(new IdentityHashMap<>());

    private List<KeyAction> keyActions = new LinkedList<>();
    private List<MouseAction> mouseActions = new LinkedList<>();

    @Override
    public void onUpdate(double timePerFrame) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(this::fireAllActions);
    }

    private void fireAllActions() {
        keyActions.forEach(KeyAction::fireAction);
        keyActions.clear();
        mouseActions.forEach(MouseAction::fireAction);
        mouseActions.clear();
    }

    @Override
    public synchronized void bindAppState(AppState appState) {
        this.currentAppState = appState;
        initListenersOfAppStateIfNotExists(appState);
    }

    @Override
    public synchronized void unbind() {
        this.currentAppState = null;
    }

    @Override
    public void bindKeyEvent(AppState appState, int eventId, Runnable keyListener) {
        initListenersOfAppStateIfNotExists(appState);
        if (keyListenersSpace.get(appState).containsKey(eventId))
            throw new IllegalArgumentException("The eventId has already been bound.");
        keyListenersSpace.get(appState).put(eventId, keyListener);
    }

    @Override
    public void bindMouseEvent(AppState appState, int eventId, Consumer<Point> mouseListener) {
        initListenersOfAppStateIfNotExists(appState);
        if (mouseListenersSpace.get(appState).containsKey(eventId))
            throw new IllegalArgumentException("The eventId has already been bound.");
        mouseListenersSpace.get(appState).put(eventId, mouseListener);
    }

    private void initListenersOfAppStateIfNotExists(AppState appState) {
        if (!keyListenersSpace.containsKey(appState))
            keyListenersSpace.put(appState, new HashMap<>());
        if (!mouseListenersSpace.containsKey(appState))
            mouseListenersSpace.put(appState, new HashMap<>());
    }

    @Override
    public int compositeCode(int... events) {
        return Arrays.hashCode(events);
    }

    @Override
    public void onButtonPressedDown(int id) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(
                () -> keyActions.add(keyAction(KeyEvent.KEY_PRESSED, id))
        );
    }

    @Override
    public void onButtonReleasedUp(int id) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(() -> {
            keyActions.add(keyAction(KeyEvent.KEY_RELEASED, id));
            keyActions.add(keyAction(KeyEvent.KEY_TYPED, id));
        });
    }

    private KeyAction keyAction(int event, int keyCode) {
        return new KeyAction(event, keyCode);
    }

    @Override
    public void onMouseMoved(Point position) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(
                ()-> mouseActions.add(mouseAction(MouseEvent.MOUSE_MOVED, position))
        );
    }

    @Override
    public void onMouseHitDown(Point position) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(
                ()-> mouseActions.add(mouseAction(MouseEvent.MOUSE_PRESSED, position))
        );
    }


    @Override
    public void onMouseReleasedUp(Point position) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(() -> {
            mouseActions.add(mouseAction(MouseEvent.MOUSE_RELEASED, position));
            mouseActions.add(mouseAction(MouseEvent.MOUSE_CLICKED, position));
        });
    }

    @Override
    public void onMouseDragged(Point position) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(
                () -> mouseActions.add(mouseAction(MouseEvent.MOUSE_DRAGGED, position))
        );
    }

    private MouseAction mouseAction(int eventId, Point position) {
        return new MouseAction(eventId, position);
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
            doubleCheckedSyncCurrentAppStateNotNullThenDo(()-> {
                if (keyListenersSpace.get(currentAppState).containsKey(id))
                    keyListenersSpace.get(currentAppState).get(id).run();
            });
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
            doubleCheckedSyncCurrentAppStateNotNullThenDo(()-> {
                if (mouseListenersSpace.get(currentAppState).containsKey(id))
                    mouseListenersSpace.get(currentAppState).get(id).accept(mousePosition);
            });
        }

        public Point getMousePosition() {
            return mousePosition;
        }
    }


    private void doubleCheckedSyncCurrentAppStateNotNullThenDo(Runnable runnable) {
        if (currentAppState != null) {
            synchronized (this) {
                if (currentAppState != null)
                    runnable.run();
            }
        }
    }
}
