package com.pokewords.framework.sprites.components;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.gameworlds.AppStateWorld;

import java.util.function.BiConsumer;

/**
 * @author johnny850807
 */
@FunctionalInterface
public interface GameEffect extends BiConsumer<AppStateWorld, Sprite> {

    /**
     * @param world the effected world
     * @param sprite the effected sprite
     */
    default void apply(AppStateWorld world, Sprite sprite){
        this.accept(world, sprite);
    }
}
