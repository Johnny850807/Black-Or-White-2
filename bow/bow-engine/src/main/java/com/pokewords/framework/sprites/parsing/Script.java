package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public abstract class Script implements Node {
    private List<ListNode> listNodes = new ArrayList<>();
    private List<Segment> segments = new ArrayList<>();

    public Script() { }

    public void addListNode(ListNode listNode) {
        listNodes.add(listNode);
        listNode.setParent(this);
    }

    public List<ListNode> getListNodes() {
        return listNodes;
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
        segment.setParent(this);
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public boolean containsSegment(String name) {
        for (Segment segment : segments)
            if (segment.getName().equals(name))
                return true;
        return false;
    }

    public List<Segment> getSegments(String name) {
        return segments.stream()
                .filter(segment -> segment.getName().equals(name))
                .collect(Collectors.toList());
    }

    public Segment getFirstSegment(String name) {
        return containsSegment(name)? getSegments(name).get(0) : null;
    }

    public Optional<Segment> getFirstSegmentOptional(String name) {
        return containsSegment(name)? Optional.of(getSegments(name).get(0)) : Optional.empty();
    }
}
