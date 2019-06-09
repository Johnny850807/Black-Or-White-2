package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.NodeException;
import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public class LinScript extends Script {
    private List<ListNode> listNodes = new ArrayList<>();
    private List<Segment> segments = new ArrayList<>();

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
            ListNode listNode = new BracketCommaListNode();
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

    @Override
    public void addListNode(ListNode listNode) {
        if (listNode == null)
            throw new NodeException("Try to add null as a list node to script.");
        listNodes.add(listNode);
        listNode.setParent(this);
    }

    @Override
    public List<ListNode> getListNodes() {
        return listNodes;
    }

    @Override
    public boolean containsListNode(String name) {
        for (ListNode listNode : listNodes)
            if (listNode.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public List<ListNode> getListNodes(String name) {
        return listNodes.stream()
                .filter(listNode -> listNode.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public ListNode getFirstListNode(String name) {
        List<ListNode> result = getListNodes(name);
        return result.size() > 0? result.get(0) : null;
    }

    @Override
    public Optional<ListNode> getFirstListNodeOptional(String name) {
        List<ListNode> result = getListNodes(name);
        return result.size() > 0? Optional.of(result.get(0)) : Optional.empty();
    }

    @Override
    public void addSegment(Segment segment) {
        if (segment == null)
            throw new NodeException("Try to add null as a segment to script.");
        segments.add(segment);
        segment.setParent(this);
    }

    @Override
    public List<Segment> getSegments() {
        return segments;
    }

    @Override
    public boolean containsSegment(String name) {
        for (Segment segment : segments)
            if (segment.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public boolean containsSegmentId(int id) {
        for (Segment segment : segments)
            if (segment.getId() == id)
                return true;
        return false;
    }

    @Override
    public boolean containsSegmentDescription(String description) {
        for (Segment segment : segments)
            if (segment.getDescription().orElse("").equals(description))
                return true;
        return false;
    }

    @Override
    public List<Segment> getSegments(String name) {
        return segments.stream()
                .filter(segment -> segment.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Segment> getSegmentsById(int id) {
        return segments.stream()
                .filter(segment -> segment.getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public List<Segment> getSegmentsByDescription(String description) {
        return segments.stream()
                .filter(segment -> segment.getDescription().orElse("").equals(description))
                .collect(Collectors.toList());
    }

    @Override
    public Segment getFirstSegment(String name) {
        List<Segment> result = getSegments(name);
        return result.size() > 0? result.get(0) : null;
    }

    @Override
    public Optional<Segment> getFirstSegmentOptional(String name) {
        List<Segment> result = getSegments(name);
        return result.size() > 0? Optional.of(result.get(0)) : Optional.empty();
    }
}
