package com.pokewords.framework.views.effects;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.sprites.factories.SpriteBuilder;

public class NoTransitionEffect implements AppStateTransitionEffect {
    private static NoTransitionEffect instance = new NoTransitionEffect();

    public static NoTransitionEffect getInstance() {
        return instance;
    }

    @Override
    public void effect(SpriteBuilder spriteBuilder, AppState from, AppState to, Listener... transitionEffectListeners) {
        notifyOnExitingAppStateEffectEnd(transitionEffectListeners);
        notifyOnEnteringAppStateEffectEnd(transitionEffectListeners);
    }
}
