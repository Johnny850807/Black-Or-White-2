package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.util.function.Consumer;

public class MockFrame implements Frame {
    @Override
    public void apply(AppStateWorld gameWorld) {

    }

    @Override
    public void addEffect(Consumer<AppStateWorld> effectToWorld) {

    }

    @Override
    public void renderItself(Canvas canvas) {

    }
}
