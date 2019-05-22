package com.pokewords.framework.sprites.parsing;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nyngwang
 */
public abstract class Script extends Node {
    protected List<Segment> segments;

    protected Script(String name, int id, String description) {
        super(name, id, description);
    }
}
