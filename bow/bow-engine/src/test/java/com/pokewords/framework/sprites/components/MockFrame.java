package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.util.*;
import java.util.function.Consumer;

public class MockFrame implements Frame {
    public final String name;
    public List<Consumer<AppStateWorld>> effects = new ArrayList<>();
    public Map<Consumer<AppStateWorld>, Integer> effectAppliedCount = new HashMap<>();
    public int applyCount = 0;
    public int renderCount = 0;

    public MockFrame(String name) {
        this.name = name;
    }

    @Override
    public void apply(AppStateWorld gameWorld) {
        applyCount ++;
        for (Consumer<AppStateWorld> effect : effects) {
            effectAppliedCount.put(effect, effectAppliedCount.get(effect) + 1);
            effect.accept(gameWorld);
        }
    }

    @Override
    public void addEffect(Consumer<AppStateWorld> effectToWorld) {
        effectAppliedCount.put(effectToWorld, 0);
        effects.add(effectToWorld);
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
