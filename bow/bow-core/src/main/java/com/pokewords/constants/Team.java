package com.pokewords.constants;

import com.pokewords.framework.sprites.components.CompositeType;

/**
 * @author johnny850807 (waterball)
 */
public enum Team {
    NONE, HERO, MONSTER;

    public static Team fromSpriteType(CompositeType type) {
        if (type.isType(SpriteTypes.HERO))
            return HERO;
        if (type.isType(SpriteTypes.MONSTER))
            return MONSTER;
        return NONE;
    }
}
