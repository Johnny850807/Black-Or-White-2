package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public class LinScript implements Script {
    private ArrayList<Segment> segments;

    public LinScript() {
        segments = new ArrayList<>();
    }

    @Override
    public Script addSegment(Segment segment) {
        segments.add(segment);
        segment.setParentScript(this);
        return this;
    }

    @Override
    public List<Segment> getSegmentsByName(String segmentName) {
        return segments.stream()
                .filter(segment ->
                        segment.getStringByKey(ScriptDefinitions.LinScript.Segment.NAME)
                               .equals(segmentName))
                .collect(Collectors.toList());
    }

    @Override
    public Segment getSegmentById(String segmentId) {
        for (Segment segment : segments) {
            if (segment.getStringByKey(ScriptDefinitions.LinScript.Segment.ID).equals(segmentId)) {
                return segment;
            }
        }
        return null;
    }

    @Override
    public List<Segment> getSegmentsByDescription(String segmentDescription) {
        return segments.stream()
                .filter(segment ->
                        segment.getStringByKey(ScriptDefinitions.LinScript.Segment.DESCRIPTION)
                               .equals(segmentDescription))
                .collect(Collectors.toList());
    }

    @Override
    public List<Segment> getSegments() { return segments; }

    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();

        segments.sort((o1, o2) -> {
            String leftName = o1.getStringByKey(ScriptDefinitions.LinScript.Segment.NAME);
            String rightName = o2.getStringByKey(ScriptDefinitions.LinScript.Segment.NAME);
            int leftId = o1.getIntByKey(ScriptDefinitions.LinScript.Segment.ID);
            int rightId = o2.getIntByKey(ScriptDefinitions.LinScript.Segment.ID);

            return leftName.compareTo(rightName) == 0? Integer.compare(leftId, rightId)
                    : leftName.compareTo(rightName);
        });

        for (Segment segment : segments) {
            resultBuilder.append(segment.toString(indentation));
        }
        return resultBuilder.toString();
    }

    @Override
    public String toString() {
        return toString(4);
    }
}
