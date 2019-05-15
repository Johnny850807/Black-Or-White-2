package com.pokewords.framework.sprites.factories;

import com.pokewords.framework.sprites.Sprite;

/**
 * The mock PrototypeFactory only stores the latest added prototype
 * @author johnny850807 (waterball)
 */
public class MockPrototypeFactory implements PrototypeFactory {
    private Object clonedType;
    private Sprite prototypeSprite;

    @Override
    public Sprite cloneSprite(Object type) {
        if (clonedType.equals(type))
            return prototypeSprite.clone();
        throw new IllegalArgumentException("The type " + type + "not found.");
    }

    @Override
    public void addPrototype(Object type, Sprite prototype) {
        this.prototypeSprite = prototype;
        this.clonedType = type;
    }

    @Override
    public void removePrototype(Object type) {
        if (type.equals(clonedType))
        {
            this.clonedType = null;
            this.prototypeSprite = null;
        }
    }

    public Object getClonedType() {
        return clonedType;
    }

    public Sprite getPrototypeSprite() {
        return prototypeSprite;
    }
}
