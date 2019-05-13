package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
 *   ScriptRules: LinScriptRules
 *   ScriptParser: LinScriptParser
 *                 LinScriptParser.parse() uses LinScriptRules
 *
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
    public Optional<Segment> getSegmentById(String segmentId) {
        for (Segment segment : segments)
            if (segment.getStringByKey(ScriptDefinitions.LinScript.Segment.ID).equals(segmentId))
                return Optional.of(segment);
        return Optional.empty();
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
    public String toString() {
        return toString(4);
    }

    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();

        segments.sort((o1, o2) -> {
            String leftName = o1.getStringByKey(ScriptDefinitions.LinScript.Segment.NAME).get();
            String rightName = o2.getStringByKey(ScriptDefinitions.LinScript.Segment.NAME).get();
            int leftId = o1.getIntByKey(ScriptDefinitions.LinScript.Segment.ID).get();
            int rightId = o2.getIntByKey(ScriptDefinitions.LinScript.Segment.ID).get();

            return leftName.compareTo(rightName) == 0? Integer.compare(leftId, rightId)
                    : leftName.compareTo(rightName);
        });

        for (Segment segment : segments) {
            resultBuilder.append(segment.toString(indentation));
        }
        return resultBuilder.toString();
    }
}
