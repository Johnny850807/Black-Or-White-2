package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.SegmentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 *   從介面 Script 到 LinScript 的差別應該只有合法的 key-value pairs
 *      Client 只要為具體的 Script 定義自己的 segment-name, element-name
 *      Script 應該不需要是 Interface
 *
 *   檢查合法的 segment-name, element-name, key 另外開一個類別做
 *   
 * @author nyngwang
 */
public class Script {

    private ArrayList<Segment> segments;

    public Script() {
        segments = new ArrayList<>();
    }


    // g/setter of Segment

    Script addSegment(Segment segment) {
        segments.add(segment);
        return this;
    }

    List<Segment> getSegments() {
        return segments;
    }

    Optional<Segment> getSegmentById(String segmentId) {
        for (Segment segment : segments) {
            if (segment.getSegmentId().equals(segmentId)) {
                return Optional.of(segment);
            }
        }
        return Optional.empty();
    }


    @Override
    public String toString() {

        // TODO: Print by order: gallery -> frame, with id ascending

        StringBuilder result = new StringBuilder();
        for (Segment segment : segments) {
            result.append(segment);
        }
        return result.toString();
    }

}
