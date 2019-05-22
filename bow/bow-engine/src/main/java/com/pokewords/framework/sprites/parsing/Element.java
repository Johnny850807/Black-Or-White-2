package com.pokewords.framework.sprites.parsing;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * An Element can contain: key-value pairs, name, parent.
 * @author nyngwang
 */
public abstract class Element extends Node {

    protected Element(String name, int id, String description) {
        super(name, id, description);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Element put(String key, String value) {
        super.put(key, value);
        return this;
    }

    @Override
    public Element put(String key, int value) {
        super.put(key, value);
        return this;
    }

    @Override
    public Element setParent(Segment parent) {
        super.setParent(parent);
        return this;
    }
}
