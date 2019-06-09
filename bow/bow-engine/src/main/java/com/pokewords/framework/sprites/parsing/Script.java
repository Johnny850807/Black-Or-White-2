package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public abstract class Script implements Node {
    
    public abstract void addListNode(ListNode listNode);
    public abstract List<ListNode> getListNodes();

    public abstract void addSegment(Segment segment);
    public abstract List<Segment> getSegments();
    public abstract boolean containsSegment(String name);
    public abstract List<Segment> getSegments(String name);
    public abstract Segment getFirstSegment(String name);
    public abstract Optional<Segment> getFirstSegmentOptional(String name);
}
