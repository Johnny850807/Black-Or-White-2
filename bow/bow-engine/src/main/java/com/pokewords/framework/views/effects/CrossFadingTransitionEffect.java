package com.pokewords.framework.views.effects;

import com.pokewords.framework.engine.asm.AppState;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.factories.SpriteInitializer;

public class CrossFadingTransitionEffect implements AppStateTransitionEffect {
    @Override
    public void effect(AppState from, AppState to) {
        AppStateWorld fromWorld = from.getAppStateWorld();
        AppStateWorld toWorld = to.getAppStateWorld();
        SpriteInitializer spriteInitializer = from.getSpriteInitializer();

    }
}
