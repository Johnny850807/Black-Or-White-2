package com.pokewords.framework.views.effects;

import com.pokewords.framework.engine.asm.AppState;

/**
 * @author johnny850807 (waterball)
 */
public interface AppStateTransitionEffect {
    void effect(AppState from, AppState to, Listener ...transitionEffectListener);

    default void notifyOnExitingAppStateEffectEnd(Listener ...transitionEffectListeners) {
        for (Listener transitionEffectListener : transitionEffectListeners) {
            transitionEffectListener.onExitingAppStateEffectEnd();
        }
    }

    default void notifyOnEnteringAppStateEffectEnd(Listener ...transitionEffectListeners) {
        for (Listener transitionEffectListener : transitionEffectListeners) {
            transitionEffectListener.onEnteringAppStateEffectEnd();
        }
    }

    class DefaultListener implements Listener {
        @Override
        public void onExitingAppStateEffectEnd() { }

        @Override
        public void onEnteringAppStateEffectEnd() { }
    }

    interface Listener {
        void onExitingAppStateEffectEnd();
        void onEnteringAppStateEffectEnd();
    }
}
