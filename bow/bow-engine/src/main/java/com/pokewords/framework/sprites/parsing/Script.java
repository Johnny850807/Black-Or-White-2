package com.pokewords.framework.sprites.parsing;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public abstract class Script extends Node {
    protected List<Segment> segments;

    protected Script(String name, int id, String description) {
        super(name, id, description);
    }

    // Segments

    protected Segment addSegment(Segment segment) {
        segments.add(segment);
        segment.setParent(this);
        return this;
    }

    protected List<Segment> getSegments() {
        return segments;
    }

    protected boolean containsSegment(String name) {
        for (Segment segment : segments)
            if (segment.getName().equals(name))
                return true;
        return false;
    }

    protected boolean containsSegmentId(int id) {
        for (Segment segment : segments)
            if (segment.getId() == id)
                return true;
        return false;
    }

    protected boolean containsSegmentDescription(String description) {
        for (Segment segment : segments)
            if (segment.getDescription().equals(description))
                return true;
        return false;
    }

    protected List<Segment> getSegments(String name) {
        return segments.stream()
                .filter(segment -> segment.getName().equals(name))
                .collect(Collectors.toList());
    }

    protected List<Segment> getSegmentsById(int id) {
        return segments.stream()
                .filter(segment -> segment.getId() == id)
                .collect(Collectors.toList());
    }

    protected List<Segment> getSegmentsByDescription(String description) {
        return segments.stream()
                .filter(segment -> segment.getDescription().equals(description))
                .collect(Collectors.toList());
    }

}
