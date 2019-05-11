package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.utils.FileUtility;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *   ScriptRules: LinScriptRules
 *   ScriptParser: LinScriptParser
 *
 *   LinScriptParser.parse() uses LinScriptRules
 *
 *   Script doesn't have to store Rules (?)
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
                .filter(segment -> segment.getStringByKey(ScriptDef.LinScript.SEGMENT).orElse("")
                        .equals(segmentName))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Segment> getSegmentById(String segmentId) {
        for (Segment segment : segments)
            if (segment.getStringByKey(Segment.Def.ID).orElse("").equals(segmentId))
                return Optional.of(segment);
        return Optional.empty();
    }

    @Override
    public List<Segment> getSegmentsByDescription(String segmentDescription) {
        return segments.stream()
                .filter(segment -> segment.getStringByKey(Segment.Def.DESCRIPTION).equals(segmentDescription))
                .collect(Collectors.toList());
    }

    // Not recommended

    public List<Segment> getSegments() {
        return segments;
    }


    @Override
    public String toString() {
        return toString(4);
    }

    public String toString(int indentation) {
        // TODO: Print by order: gallery -> frame, with id ascending
        StringBuilder resultBuilder = new StringBuilder();

        for (String segmentName : rules.validSegmentNames) {
            List<Segment> segments = getSegmentsByName(segmentName);
            segments.sort((o1, o2) -> {
                int left = o1.getIntByKey(Segment.Def.ID).get();
                int right = o2.getIntByKey(Segment.Def.ID).get();
                return Integer.compare(left, right);
            });
            for (Segment segment : segments)
                resultBuilder.append(segment.toString(indentation));
        }
        return resultBuilder.toString();
    }
}
