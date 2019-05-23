package com.pokewords.framework.sprites.parsing;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * An Element doesn't contain other Nodes.
 * @author nyngwang
 */
public abstract class Element implements Node {
    protected Element(String name, int id, String description) {
        super(name, id, description);
    }

    // Fluent

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

    // Change to public

    @Override
    public Element setParent(Segment parent) {
        super.setParent(parent);
        return this;
    }
}
