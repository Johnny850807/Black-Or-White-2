package com.pokewords.framework.sprites.components.mocks;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.Frame;
import com.pokewords.framework.sprites.components.frames.GameEffect;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.util.*;


public class MockFrame implements EffectFrame {
    public final String name;
    public Collection<GameEffect> effects = new HashSet<>();
    public Map<GameEffect, Integer> effectAppliedCount = new HashMap<>();
    public int applyCount = 0;
    public int renderCount = 0;

    public MockFrame(String name) {
        this.name = name;
    }

    @Override
    public int getLayerIndex() {
        return 0;
    }

    @Override
    public void apply(AppStateWorld gameWorld, Sprite sprite) {
        applyCount ++;
        for (GameEffect effect : effects) {
            effectAppliedCount.put(effect, effectAppliedCount.get(effect) + 1);
            effect.apply(gameWorld, sprite);
        }
    }

    @Override
    public void addEffect(GameEffect effect) {
        effectAppliedCount.put(effect, 0);
        effects.add(effect);
    }

    @Override
    public void renderItself(Canvas canvas) {
        renderCount ++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockFrame mockFrame = (MockFrame) o;
        return Objects.equals(name, mockFrame.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
