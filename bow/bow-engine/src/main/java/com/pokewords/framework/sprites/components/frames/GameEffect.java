package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

import java.util.function.BiConsumer;

/**
 * A GameEffect that can be added to an EffectFrame.
 * Note that the subclass of GameEffect should not contain any states, because it's not cloned during sprite cloning.
 * the GameEffect should be used functionally.
 * @author johnny850807
 */
@FunctionalInterface
public interface GameEffect {

    default void onFirstApply(AppStateWorld world, Sprite sprite) {}

    /**
     * @param world the effected world
     * @param sprite the effected sprite
     */
    void apply(AppStateWorld world, Sprite sprite);


    static GameEffect empty() {
        return (world, sprite) -> {};
    }

}
