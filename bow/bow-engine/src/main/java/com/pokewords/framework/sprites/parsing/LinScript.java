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

    public boolean containsSegmentId(int id) {
        for (Segment segment : getSegments()) {
            if (((AngularBracketSegment) segment).getId() == id)
                return true;
        }
        return false;
    }

    public boolean containsSegmentDescription(String description) {
        for (Segment segment : getSegments()) {
            if (((AngularBracketSegment) segment).getDescription().orElse("").equals(description))
                return true;
        }
        return false;
    }

    public List<Segment> getSegmentsById(int id) {
        return getSegments().stream()
                .filter(segment -> ((AngularBracketSegment) segment).getId() == id)
                .collect(Collectors.toList());
    }

    public List<Segment> getSegmentsByDescription(String description) {
        return getSegments().stream()
                .filter(segment -> ((AngularBracketSegment) segment)
                        .getDescription().orElse("").equals(description))
                .collect(Collectors.toList());
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
            Segment segment = new AngularBracketSegment();
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
        getSegments().sort((o1, o2) -> {
            String leftName = o1.getName();
            String rightName = o2.getName();
            int leftId = ((AngularBracketSegment) o1).getId();
            int rightId = ((AngularBracketSegment) o2).getId();
            return leftName.compareTo(rightName) == 0? Integer.compare(leftId, rightId)
                    : leftName.compareTo(rightName);
        });
        getSegments().forEach(segment ->
                resultBuilder.append(segment.toString(indent)
                        .replaceAll("([^\n]*\n)", indent + "$1")));
        return resultBuilder.toString();
    }
}
