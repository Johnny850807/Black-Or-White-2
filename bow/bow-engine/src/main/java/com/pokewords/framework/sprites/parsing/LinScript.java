package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
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
        segment.setParent(this);
        return this;
    }

    @Override
    public boolean containsSegmentId(int id) {
        for (Segment segment : segments)
            if (segment.getId() == id)
                return true;
        return false;
    }

    @Override
    public boolean containsSegmentName(String name) {
        for (Segment segment : segments)
            if (segment.getName().equals(name))
                return true;
        return false;
    }

    @Override
    public boolean containsSegmentDescription(String description) {
        for (Segment segment : segments)
            if (segment.getDescription().equals(description))
                return true;
        return false;
    }

    @Override
    public List<Segment> getSegmentsByName(String name) {
        return segments.stream()
                .filter(segment -> segment.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public Segment getSegmentById(int id) {
        for (Segment segment : segments)
            if (segment.getId() == id)
                return segment;
        return null;
    }

    @Override
    public List<Segment> getSegmentsByDescription(String description) {
        return segments.stream()
                .filter(segment -> segment.getDescription().equals(description))
                .collect(Collectors.toList());
    }

    @Override
    public List<Segment> getSegments() { return segments; }

    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();

        segments.sort((o1, o2) -> {
            String leftName = o1.getName();
            String rightName = o2.getName();
            int leftId = o1.getId();
            int rightId = o2.getId();
            return leftName.compareTo(rightName) == 0? Integer.compare(leftId, rightId)
                    : leftName.compareTo(rightName);
        });
        for (Segment segment : segments)
            resultBuilder.append(segment.toString(indentation));
        return resultBuilder.toString();
    }

    @Override
    public String toString() {
        return toString(4);
    }

    public static void main(String[] args) {
        Script script = new LinScript()
                .addSegment(new LinScriptSegment("frame", 1, "punch")
                        .put("next", 2).put("duration", 10)
                        .addElement(new LinScriptElement("bow")
                                .put("x", 1)
                                .put("y", 2))
                        .addElement(new LinScriptElement("bow2")
                                .put("a", 1)
                                .put("b", 2)))
                .addSegment(new LinScriptSegment("galleries", 1)
                        .put("length", 2)
                        .addElement(new LinScriptElement("gallery")
                                .put("path", "path/to/gallery1"))
                        .addElement(new LinScriptElement("gallery")
                                .put("path", "path/to/gallery2")));
        System.out.println(script);
    }
}
