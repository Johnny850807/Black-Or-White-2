package com.pokewords.framework.sprites;

import com.pokewords.framework.engine.exceptions.NoSuchPrototypeException;

import java.util.HashMap;
import java.util.Map;

public class PrototypeFactoryImp implements PrototypeFactory{
    private Map<String, Sprite> spriteMap = new HashMap<>();

    @Override
    public Sprite cloneSprite(String type) {
        if (!spriteMap.containsKey(type))
            throw new NoSuchPrototypeException(String.format("Does not contain a Prototype named %s.", type));

        return spriteMap.get(type).clone();
    }

    @Override
    public void addPrototype(String type, Sprite prototype) {
        spriteMap.put(type, prototype);
    }

    @Override
    public void removePrototype(String type) {
        spriteMap.remove(type);
    }

}
