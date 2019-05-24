package com.pokewords.framework.sprites.parsing;

import java.util.List;

/**
 * @author nyngwang
 */
public abstract class Script implements Node {
    private List<Segment> segments;

    public Script(List<Segment> segments) {
        this.segments = segments;
    }


}
