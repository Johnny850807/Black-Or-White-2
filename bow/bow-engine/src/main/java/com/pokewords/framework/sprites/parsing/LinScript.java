package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.utils.Resources;
import com.pokewords.framework.engine.exceptions.NodeException;
import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * WARNING: No detection of inclusion conflict.
 * @author nyngwang
 */
public class LinScript extends Script {
    private List<ListNode> listNodes = new ArrayList<>();
    private List<Segment> segments = new ArrayList<>();

    @Override
    public void parse(Context context) {
        if (!context.hasNextToken())
            throw new ScriptParsingException("Empty script is not allowed.");
        if (context.peekToken().equals("<meta>"))
            parseMetaSegment(context);
        do {
            if (context.peekToken().equals("<meta>"))
                throw new ScriptParsingException(
                        "Meta segment should appear at the beginning of the script.");
            parseOneNode(context);
        } while (context.hasNextToken());
    }

    private void parseMetaSegment(Context context) {
        Segment metaSegment = new AngularSegment();
        metaSegment.parse(context);
        metaSegment.getListNodeOptional("include").ifPresent(listNode -> {
            for (String path : listNode.getList()) {
                Script script = new LinScript();
                script.parse(Context.fromFile(Resources.get(path)));
                script.getListNodes().forEach(this::addListNode);
                script.getSegments().forEach(this::addSegment);
            }
        });

        context.updateTokens(metaSegment.getFirstElement("parameters").getMap());
    }

    private void parseOneNode(Context context) {
        String nextToken = context.peekToken();
        if (nextToken.matches("<[^/\\s]\\S+>")) { // Segment
            Segment segment = new AngularSegment();
            segment.parse(context);
            addSegment(segment);
        } else if (nextToken.matches("@\\S+")) { // ListNode
            ListNode listNode = new BracketCommaListNode();
            listNode.parse(context);
            addListNode(listNode);
        } else
            throw new ScriptParsingException("Script body cannot contain: " + nextToken);
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[indent]).replace("\0", " ");
        listNodes.forEach(listNode ->
                resultBuilder.append(
                        listNode.toString(indent).replaceAll("([^\n]*\n)", spaces + "$1")));
        segments.sort((o1, o2) -> {
            String leftName = o1.getName();
            String rightName = o2.getName();
            int leftId = o1.getId();
            int rightId = o2.getId();
            return leftName.compareTo(rightName) == 0? Integer.compare(leftId, rightId)
                    : leftName.compareTo(rightName);
        });
        segments.forEach(segment ->
                resultBuilder.append(
                        segment.toString(indent).replaceAll("([^\n]*\n)", spaces + "$1")));
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
