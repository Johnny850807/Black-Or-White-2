package com.pokewords.framework.views.effects;

import com.pokewords.framework.engine.asm.AppState;

/**
 * This transition effect is used for wrapping an AppStateTransitionEffect with the known listeners.
 * And it also accepts more listeners in the effect method.
 * @author johnny850807 (waterball)
 */
public class AppStateTransitionEffectListenersWrapper implements AppStateTransitionEffect {
    private AppStateTransitionEffect appStateTransitionEffect;
    private AppStateTransitionEffect.Listener[] clientListeners;

    public AppStateTransitionEffectListenersWrapper(AppStateTransitionEffect appStateTransitionEffect,
                                                    AppStateTransitionEffect.Listener... clientListeners) {
        this.appStateTransitionEffect = appStateTransitionEffect;
        this.clientListeners = clientListeners;
    }

    public void effect(AppState from, AppState to) {
        appStateTransitionEffect.effect(from, to, clientListeners);
    }

    @Override
    public void effect(AppState from, AppState to, AppStateTransitionEffect.Listener... moreListeners) {
        appStateTransitionEffect.effect(from, to, new AppStateTransitionEffect.Listener() {
            @Override
            public void onExitingAppStateEffectEnd() {
                notifyOnExitingAppStateEffectEnd(clientListeners);
                notifyOnExitingAppStateEffectEnd(moreListeners);
            }

            @Override
            public void onEnteringAppStateEffectEnd() {
                notifyOnEnteringAppStateEffectEnd(clientListeners);
                notifyOnEnteringAppStateEffectEnd(moreListeners);
            }
        });
    }

}
