package com.pokewords.framework.views.effects;

import com.pokewords.framework.engine.asm.AppState;

/**
 * @author johnny850807 (waterball)
 */
public interface AppStateTransitionEffect {
    void effect(AppState from, AppState to, Listener transitionEffectListener);

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
