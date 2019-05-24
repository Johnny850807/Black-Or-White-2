package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public class LinScript extends Script {

    public LinScript() {
        super(new ArrayList<>());
    }

    public void addSegment(AngularBracketSegment segment) {
        segments.add(segment);
        segment.setParent(this);
    }

    public List<AngularBracketSegment> getSegments() {
        return segments;
    }

    public boolean containsSegment(String name) {
        for (Segment segment : segments)
            if (segment.getName().equals(name))
                return true;
        return false;
    }

    public boolean containsSegmentId(int id) {
        for (AngularBracketSegment segment : segments)
            if (segment.getId() == id)
                return true;
        return false;
    }

    public boolean containsSegmentDescription(String description) {
        for (AngularBracketSegment segment : segments)
            if (segment.getDescription().equals(description))
                return true;
        return false;
    }

    public List<AngularBracketSegment> getSegments(String name) {
        return segments.stream()
                .filter(segment -> segment.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<AngularBracketSegment> getSegmentsById(int id) {
        return segments.stream()
                .filter(segment -> segment.getId() == id)
                .collect(Collectors.toList());
    }

    public List<AngularBracketSegment> getSegmentsByDescription(String description) {
        return segments.stream()
                .filter(segment ->
                        segment.getDescription().isPresent()
                        && segment.getDescription().get().equals(description))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends Node> getNodes() {
        return segments;
    }

    @Override
    public Node getParent() {
        return null;
    }

    @Override
    public void parse(Context context) {
        if (!context.peekToken().matches("<[^/\\s]\\S+>"))
            return;
        while (context.hasNextToken()) {
            int beforeSegment = context.getRemainingTokensCount();
            AngularBracketSegment segment = new AngularBracketSegment();
            segment.parse(context);
            int afterSegment = context.getRemainingTokensCount();
            if (beforeSegment > afterSegment)
                addSegment(segment);
            if (beforeSegment == afterSegment)
                throw new ScriptParsingException(
                        "Script body contains something that is not segment");
        }
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[indent]).replace("\0", " ");
        segments.sort((o1, o2) -> {
            String leftName = o1.getName();
            String rightName = o2.getName();
            int leftId = o1.getId();
            int rightId = o2.getId();
            return leftName.compareTo(rightName) == 0? Integer.compare(leftId, rightId)
                    : leftName.compareTo(rightName);
        });
        segments.forEach(segment ->
                resultBuilder.append(segment.toString(indent)
                        .replaceAll("([^\n]*\n)", indent + "$1")));
        return resultBuilder.toString();
    }
}
