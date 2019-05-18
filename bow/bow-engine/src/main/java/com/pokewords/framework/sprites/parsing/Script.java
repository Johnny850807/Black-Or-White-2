package com.pokewords.framework.sprites.parsing;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nyngwang
 */
public interface Script {
    class Mappings {
        public Map<String, String> stringMap;
        public Map<String, Integer> integerMap;
        public Mappings() {
            stringMap = new HashMap<>();
            integerMap = new HashMap<>();
        }
    }
    Script addSegment(Segment segment);

    default Segment getSegmentByName(String segmentName) {
        return getSegmentsByName(segmentName).get(0);
    }

    List<Segment> getSegmentsByName(String segmentName);
    Segment getSegmentById(String segmentId);
    List<Segment> getSegmentsByDescription(String segmentDescription);
    List<Segment> getSegments();

    String toString(int indentation);
}
