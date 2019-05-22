package com.pokewords.framework.sprites.components.frames;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.engine.gameworlds.AppStateWorld;

import java.util.function.BiConsumer;

/**
 * @author johnny850807
 */
@FunctionalInterface
public interface GameEffect {

    /**
     * @param world the effected world
     * @param sprite the effected sprite
     */
    void apply(AppStateWorld world, Sprite sprite);


    static GameEffect empty() {
        return (world, sprite) -> {};
    }
}
