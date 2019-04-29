package com.pokewords.framework.sprites.components.mocks;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.Frame;
import com.pokewords.framework.sprites.components.GameEffect;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.util.*;

public class MockFrame implements Frame {
    public final String name;
    public List<GameEffect> effects = new ArrayList<>();
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
