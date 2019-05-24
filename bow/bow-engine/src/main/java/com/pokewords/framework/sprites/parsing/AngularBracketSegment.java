package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;

public class AngularBracketSegment extends Segment {
    public AngularBracketSegment(Node parent, String name, KeyValuePairs keyValuePairs, List<Element> elements) {
        super(parent, name, keyValuePairs, elements);
    }

    public AngularBracketSegment(String name) {
        this(null, name, null, new ArrayList<>());
        keyValuePairs = new NoCommaPairs(this);
    }

    @Override
    public void parse(Context context) {

    }

    @Override
    public String toString(int indent) {
        return null;
    }
}
