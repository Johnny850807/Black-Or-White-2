package com.pokewords.framework.sprites.parsing;


import java.util.List;

/**
 * @author nyngwang
 */
public interface Script {

    Script addSegment(Segment segment);
    List<Segment> getSegmentsByName(String segmentName);
    List<Segment> getSegmentsById(String segmentId);
    List<Segment> getSegmentsByDescription(String segmentDescription);

    // List<Segment> getSegments();
    // Rules getRules();

}
