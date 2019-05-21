package com.pokewords.framework.sprites.parsing;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nyngwang
 */
public interface Script extends Segment {
    Script addSegment(Segment segment);

    default Segment getSegmentByName(String name) {
        return getSegmentsByName(name).get(0);
    }

    boolean containsSegmentId(int id);
    boolean containsSegmentDescription(String description);
    List<Segment> getSegmentsByName(String name);
    Segment getSegmentById(int id);
    List<Segment> getSegmentsByDescription(String description);
    List<Segment> getSegments();

    String toString(int indentation);
}
