package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import javafx.scene.effect.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class DefaultEffectFrame extends AbstractFrame implements EffectFrame {
    private List<GameEffect> effects = new ArrayList<>();

    public DefaultEffectFrame(int id, int layerIndex) {
        super(id, layerIndex);
    }


    @Override
    public void apply(AppStateWorld gameWorld, Sprite sprite) {
        for (GameEffect effect : effects) {
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
}
