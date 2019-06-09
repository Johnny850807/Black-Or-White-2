package com.pokewords.framework.sprites.parsing;

import java.util.List;
import java.util.Optional;

/**
 * @author nyngwang
 */
public abstract class Script implements Node {

    public abstract void addListNode(ListNode listNode);
    public abstract List<ListNode> getListNodes();
    public abstract boolean containsListNode(String name);
    public abstract List<ListNode> getListNodes(String name);
    public abstract ListNode getFirstListNode(String name);
    public abstract Optional<ListNode> getFirstListNodeOptional(String name);

    public abstract void addSegment(Segment segment);
    public abstract List<Segment> getSegments();
    public abstract boolean containsSegment(String name);
    public abstract boolean containsSegmentId(int id);
    public abstract boolean containsSegmentDescription(String description);
    public abstract List<Segment> getSegments(String name);
    public abstract List<Segment> getSegmentsById(int id);
    public abstract List<Segment> getSegmentsByDescription(String description);

    public abstract Segment getFirstSegment(String name);
    public abstract Optional<Segment> getFirstSegmentOptional(String name);
}
