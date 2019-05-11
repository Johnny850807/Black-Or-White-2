package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.engine.gameworlds.AppStateWorld;
import com.pokewords.framework.sprites.Sprite;

/**
 * @author johnny850807 (waterball)
 */
public interface EffectFrame extends Frame {

    /**
     * Actually effect the AppStateWorld from all of the effects added by addEffect(Consumer<AppStateWorld>) method.
     * @param gameWorld the effected AppStateWorld
     */
    void apply(AppStateWorld gameWorld, Sprite sprite);

    /**
     * Add an effect in a form of functional interface consuming the effected AppStateWorld.
     * All the effects added by this method will be applied during the invocation of apply(AppStateWorld) method.
     * @param effect functional interface describe how the effect should apply to the given world
     */
    void addEffect(GameEffect effect);
}