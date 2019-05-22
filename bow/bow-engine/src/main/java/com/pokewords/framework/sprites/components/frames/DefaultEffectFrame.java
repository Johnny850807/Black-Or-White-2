package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import javafx.scene.effect.Effect;

import java.util.*;

public abstract class DefaultEffectFrame extends AbstractFrame implements EffectFrame {
    private List<GameEffect> effects = new ArrayList<>();
    protected Set<GameEffect> appliedGameEffects = Collections.newSetFromMap(new IdentityHashMap<>());

    public DefaultEffectFrame(int id, int layerIndex) {
        super(id, layerIndex);
    }


    @Override
    public void apply(AppStateWorld gameWorld, Sprite sprite) {
        for (GameEffect effect : effects) {
            if (!appliedGameEffects.contains(effect))
            {
                effect.onFirstApply(gameWorld, sprite);
                appliedGameEffects.add(effect);
            }
            effect.apply(gameWorld, sprite);
        }
    }

    @Override
    public void addEffect(GameEffect effect) {
        effects.add(effect);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DefaultEffectFrame that = (DefaultEffectFrame) o;
        return effects.equals(that.effects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), effects);
    }

    @Override
    public DefaultEffectFrame clone() {
        DefaultEffectFrame clone = (DefaultEffectFrame) super.clone();
        clone.appliedGameEffects = Collections.newSetFromMap(new IdentityHashMap<>());
        return clone;
    }
}
