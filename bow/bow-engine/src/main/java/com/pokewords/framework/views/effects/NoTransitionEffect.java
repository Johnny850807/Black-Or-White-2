package com.pokewords.framework.views.effects;

import com.pokewords.framework.engine.asm.AppState;

public class NoTransitionEffect implements AppStateTransitionEffect {
    @Override
    public void effect(AppState from, AppState to, Listener transitionEffectListener) {
        transitionEffectListener.onFromEffectEnd();
        transitionEffectListener.onToEffectEnd();
    }
}
