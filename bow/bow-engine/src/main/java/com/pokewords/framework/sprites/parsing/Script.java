package com.pokewords.framework.sprites.parsing;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public abstract class Script implements Node {
    protected List<Segment> segments;

    public Script(List<Segment> segments) {
        this.segments = segments;
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
        segment.setParent(this);
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public boolean containsSegment(String name) {
        for (Segment segment : segments)
            if (segment.getName().equals(name))
                return true;
        return false;
    }

    public List<Segment> getSegments(String name) {
        return segments.stream()
                .filter(segment -> segment.getName().equals(name))
                .collect(Collectors.toList());
    }
}
