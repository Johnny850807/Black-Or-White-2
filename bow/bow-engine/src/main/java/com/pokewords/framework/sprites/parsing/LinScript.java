package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public class LinScript extends Script {
    public boolean containsSegmentId(int id) {
        for (Segment segment : getSegments()) {
            if (segment.getId() == id)
                return true;
        }
        return false;
    }

    public boolean containsSegmentDescription(String description) {
        for (Segment segment : getSegments()) {
            if (segment.getDescription().orElse("").equals(description))
                return true;
        }
        return false;
    }

    public List<Segment> getSegmentsById(int id) {
        return getSegments().stream()
                .filter(segment -> segment.getId() == id)
                .collect(Collectors.toList());
    }

    public List<Segment> getSegmentsByDescription(String description) {
        return getSegments().stream()
                .filter(segment -> segment
                        .getDescription().orElse("").equals(description))
                .collect(Collectors.toList());
    }

    @Override
    public void parse(Context context) {
        if (!context.hasNextToken())
            throw new ScriptParsingException("Empty script is not allowed.");
        do {
            parseAndAddNextNodeByToken(context);
        } while (context.hasNextToken());
    }

    private void parseAndAddNextNodeByToken(Context context) {
        String nextToken = context.peekToken();
        if (nextToken.matches("<[^/\\s]\\S+>"))
        {
            Segment segment = new AngularSegment();
            segment.parse(context);
            addSegment(segment);
        }
        else if (nextToken.matches("@\\w+"))
        {
            ListNode listNode = new BracketListNode();
            listNode.parse(context);
            addListNode(listNode);
        }
        else
            throw new ScriptParsingException("Script body cannot contain: " + nextToken);
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[indent]).replace("\0", " ");
        getSegments().sort((o1, o2) -> {
            String leftName = o1.getName();
            String rightName = o2.getName();
            int leftId = o1.getId();
            int rightId = o2.getId();
            return leftName.compareTo(rightName) == 0? Integer.compare(leftId, rightId)
                    : leftName.compareTo(rightName);
        });
        getSegments().forEach(segment ->
                resultBuilder.append(segment.toString(indent)
                        .replaceAll("([^\n]*\n)", spaces + "$1")));
        return resultBuilder.toString();
    }

    @Override
    public void setParent(Node parent) { }

    @Override
    public Node getParent() {
        return null;
    }
}
