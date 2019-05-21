package com.pokewords.framework.views.inputs;

import com.pokewords.framework.commons.ReusableReferencePool;
import com.pokewords.framework.engine.asm.AppState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * TODO composite key action, how to do?
 * @author johnny850807 (waterball)
 */
public class DefaultInputManager implements InputManager {
    private final ReusableReferencePool<KeyAction> keyActionReusablePool =
            new ReusableReferencePool<>(8, KeyAction::new);
    private final ReusableReferencePool<MouseAction> mouseActionReusablePool =
            new ReusableReferencePool<>(10, MouseAction::new);

    private AppState currentAppState;

    // <key event's event, listener (given key code)>
    private Map<Integer, Consumer<Integer>> rootKeyListeners = Collections.synchronizedMap(new HashMap<>());

    //<mouse event's event, listener (given mouse position)>>
    private Map<Integer, Consumer<Point>> rootMouseListeners = Collections.synchronizedMap(new HashMap<>());

    // <current AppState, <key's event, listener (given key code)>>
    private Map<AppState, HashMap<Integer, Consumer<Integer>>> keyListenersSpace = Collections.synchronizedMap(new IdentityHashMap<>());

    // <current AppState, <mouse event's event, listener (given mouse position)>>
    private Map<AppState, HashMap<Integer, Consumer<Point>>> mouseListenersSpace = Collections.synchronizedMap(new IdentityHashMap<>());

    private List<KeyAction> currentKeyActionList = new LinkedList<>();
    private List<MouseAction> currentMouseActionList = new LinkedList<>();

    @Override
    public void onUpdate(double timePerFrame) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(this::fireAllActions);
    }

    private void fireAllActions() {
        currentKeyActionList.forEach(KeyAction::fireAction);
        keyActionReusablePool.put(currentKeyActionList);
        currentKeyActionList.clear();

        currentMouseActionList.forEach(MouseAction::fireAction);
        mouseActionReusablePool.put(currentMouseActionList);
        currentMouseActionList.clear();
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
    public void bindKeyEventForRoot(int event, Consumer<Integer> keyListener) {
        rootKeyListeners.put(event, keyListener);
    }

    @Override
    public void bindMouseEventForRoot(int event, Consumer<Point> mouseListener) {
        rootMouseListeners.put(event, mouseListener);
    }

    @Override
    public void bindKeyEvent(AppState appState, int eventId, Consumer<Integer> keyListener) {
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
    public void onButtonPressedDown(int keyCode) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(
                () -> currentKeyActionList.add(keyAction(KeyEvent.KEY_PRESSED, keyCode))
        );
    }

    @Override
    public void onButtonReleasedUp(int keyCode) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(() -> {
            currentKeyActionList.add(keyAction(KeyEvent.KEY_RELEASED, keyCode));
            currentKeyActionList.add(keyAction(KeyEvent.KEY_TYPED, keyCode));
        });
    }

    private KeyAction keyAction(int event, int keyCode) {
        return keyActionReusablePool.get().init(event, keyCode);
    }

    @Override
    public void onMouseMoved(Point position) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(
                ()-> currentMouseActionList.add(mouseAction(MouseEvent.MOUSE_MOVED, position))
        );
    }

    @Override
    public void onMouseHitDown(Point position) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(
                ()-> currentMouseActionList.add(mouseAction(MouseEvent.MOUSE_PRESSED, position))
        );
    }


    @Override
    public void onMouseReleasedUp(Point position) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(() -> {
            currentMouseActionList.add(mouseAction(MouseEvent.MOUSE_RELEASED, position));
            currentMouseActionList.add(mouseAction(MouseEvent.MOUSE_CLICKED, position));
        });
    }

    @Override
    public void onMouseDragged(Point position) {
        doubleCheckedSyncCurrentAppStateNotNullThenDo(
                () -> currentMouseActionList.add(mouseAction(MouseEvent.MOUSE_DRAGGED, position))
        );
    }

    private MouseAction mouseAction(int eventId, Point position) {
        return mouseActionReusablePool.get().init(eventId, position);
    }


    public abstract class Action {
        protected long timeStamp;
        protected int event;

        public void init(int event) {
            this.event = event;
            this.timeStamp = System.currentTimeMillis();
        }

        protected abstract void fireAction();

        public long getTimeStamp() {
            return timeStamp;
        }

        public int getEvent() {
            return event;
        }
    }

    public class KeyAction extends Action {
        private int keyCode;

        public KeyAction init(int event, int keyCode) {
            super.init(event);
            this.keyCode = keyCode;
            return this;
        }

        @Override
        @SuppressWarnings("Duplicates")
        protected void fireAction() {
            if (rootKeyListeners.containsKey(event))
                rootKeyListeners.get(event).accept(keyCode);

            doubleCheckedSyncCurrentAppStateNotNullThenDo(()-> {
                if (keyListenersSpace.get(currentAppState).containsKey(event))
                    keyListenersSpace.get(currentAppState).get(event).accept(keyCode);
            });
        }

        public int getKeyCode() {
            return keyCode;
        }
    }

    public class MouseAction extends Action {
        public Point mousePosition;

        public MouseAction init(int eventId,  Point mousePosition) {
            super.init(eventId);
            this.mousePosition = mousePosition;
            return this;
        }

        @Override
        @SuppressWarnings("Duplicates")
        protected void fireAction() {
            if (rootMouseListeners.containsKey(event))
                rootMouseListeners.get(event).accept(mousePosition);

            doubleCheckedSyncCurrentAppStateNotNullThenDo(()-> {
                if (mouseListenersSpace.get(currentAppState).containsKey(event))
                    mouseListenersSpace.get(currentAppState).get(event).accept(mousePosition);
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
