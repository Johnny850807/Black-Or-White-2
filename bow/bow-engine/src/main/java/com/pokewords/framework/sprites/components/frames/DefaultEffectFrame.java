package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import javafx.scene.effect.Effect;

import java.util.ArrayList;
import java.util.List;

public abstract class DefaultEffectFrame implements EffectFrame {
    public List<GameEffect> effects = new ArrayList<>();

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


}
