package com.pokewords.framework.sprites.parsing;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nyngwang
 */
public abstract class Script extends Segment {
    public Script() {
        super(null, Integer.MIN_VALUE, null);
    }

    // Segments

    @Override
    public LinScript addSegment(Segment segment) {
        super.addSegment(segment);
        return this;
    }

    @Override
    public List<Segment> getSegments() {
        return super.getSegments();
    }

    @Override
    public boolean containsSegment(String name) {
        return super.containsSegment(name);
    }

    @Override
    public boolean containsSegmentId(int id) {
        return super.containsSegmentId(id);
    }

    @Override
    public boolean containsSegmentDescription(String description) {
        return super.containsSegmentDescription(description);
    }

    @Override
    public List<Segment> getSegments(String name) {
        return super.getSegments(name);
    }

    @Override
    public List<Segment> getSegmentsById(int id) {
        return super.getSegmentsById(id);
    }

    @Override
    public List<Segment> getSegmentsByDescription(String description) {
        return super.getSegmentsByDescription(description);
    }

    //

}
