package com.pokewords.framework.sprites.components.mocks;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.AbstractFrame;
import com.pokewords.framework.sprites.components.frames.EffectFrame;
import com.pokewords.framework.sprites.components.frames.GameEffect;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.views.Canvas;

import java.util.*;


public class MockEffectFrame extends AbstractFrame implements EffectFrame {
    public final String name;
    public Collection<GameEffect> effects = new HashSet<>();
    public Map<GameEffect, Integer> effectAppliedCount = new HashMap<>();
    public int applyCount = 0;
    public int renderCount = 0;

    public MockEffectFrame(String name) {
        this(0, name);
    }

    public MockEffectFrame(int id, String name) {
        super(id, 0);
        this.name = name;
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
    public int getDuration() {
        return 0;
    }

    @Override
    public void renderItself(Canvas canvas) {
        renderCount ++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MockEffectFrame that = (MockEffectFrame) o;
        return applyCount == that.applyCount &&
                renderCount == that.renderCount &&
                name.equals(that.name) &&
                effects.equals(that.effects) &&
                effectAppliedCount.equals(that.effectAppliedCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, effects, effectAppliedCount, applyCount, renderCount);
    }
}
