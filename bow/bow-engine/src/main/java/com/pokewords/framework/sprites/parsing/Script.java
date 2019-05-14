package com.pokewords.framework.sprites.parsing;


import com.pokewords.framework.engine.utils.FileUtility;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    List<Segment> getSegmentsByName(String segmentName);
    Optional<Segment> getSegmentById(String segmentId);
    List<Segment> getSegmentsByDescription(String segmentDescription);
    List<Segment> getSegments();

    String toString(int indentation);
}
