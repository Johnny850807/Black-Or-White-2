package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.engine.exceptions.NoSuchPrototypeException;
import com.pokewords.framework.sprites.Sprite;

import java.util.HashMap;
import java.util.Map;

public class DefaultPrototypeFactory implements PrototypeFactory{
    private Map<Object, Sprite> spriteMap = new HashMap<>();

    @Override
    public Sprite cloneSprite(Object type) {
        if (!spriteMap.containsKey(type))
            throw new NoSuchPrototypeException(String.format("Does not contain a Prototype named %s.", type));

        Sprite prototype = spriteMap.get(type);
        return prototype.clone();
    }

    @Override
    public void addPrototype(Object type, Sprite prototype) {
        spriteMap.put(type, prototype);
    }

    @Override
    public void removePrototype(Object type) {
        spriteMap.remove(type);
    }

}
