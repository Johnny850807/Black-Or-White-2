package com.pokewords.framework.sprites.parsing;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ParametersBindingElement extends Element {
    public ParametersBindingElement(Node parent, String name, @NotNull KeyValuePairs keyValuePairs) {
        super(parent, name, keyValuePairs);
    }

    @Override
    public void parse(Context context) {

    }

    @Override
    public String toString(int indent) {
        return null;
    }
}
