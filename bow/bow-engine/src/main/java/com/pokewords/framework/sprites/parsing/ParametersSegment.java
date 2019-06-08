package com.pokewords.framework.sprites.parsing;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ParametersSegment extends Segment {
    public ParametersSegment(Node parent, String name, @NotNull KeyValuePairs keyValuePairs, int id, String description, List<Element> elements) {
        super(parent, name, keyValuePairs, id, description, elements);
    }

    @Override
    public void parse(Context context) {

    }

    @Override
    public String toString(int indent) {
        return null;
    }
}
