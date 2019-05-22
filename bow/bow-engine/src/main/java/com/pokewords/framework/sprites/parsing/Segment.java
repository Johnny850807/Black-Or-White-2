package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Each segment is a composite Node.
 * @author nyngwang
 */
public abstract class Segment extends Node {
    protected List<Element> elements;
    protected List<Segment> segments;

    protected Segment(String name, int id, String description) {
        super(name, id, description);
    }

    // Elements

    public Segment addElement(Element element) {
        elements.add(element);
        element.setParent(this);
        return this;
    }

    public List<Element> getElements() {
        return elements;
    }

    public boolean containsElement(String name) {
        for (Element element : elements)
            if (element.getName().equals(name))
                return true;
        return false;
    }

    public List<Element> getElements(String name) {
        return elements.stream()
                .filter(element -> element.getName().equals(name))
                .collect(Collectors.toList());
    }

    public Element getElement(String name) {
        return containsElement(name)? getElements(name).get(0) : null;
    }

    // Segments

    protected Segment addSegment(Segment segment) {
        segments.add(segment);
        segment.setParent(this);
        return this;
    }

    protected List<Segment> getSegments() {
        return segments;
    }

    protected boolean containsSegment(String name) {
        for (Segment segment : segments)
            if (segment.getName().equals(name))
                return true;
        return false;
    }

    protected boolean containsSegmentId(int id) {
        for (Segment segment : segments)
            if (segment.getId() == id)
                return true;
        return false;
    }

    protected boolean containsSegmentDescription(String description) {
        for (Segment segment : segments)
            if (segment.getDescription().equals(description))
                return true;
        return false;
    }

    protected List<Segment> getSegments(String name) {
        return segments.stream()
                .filter(segment -> segment.getName().equals(name))
                .collect(Collectors.toList());
    }

    protected List<Segment> getSegmentsById(int id) {
        return segments.stream()
                .filter(segment -> segment.getId() == id)
                .collect(Collectors.toList());
    }

    protected List<Segment> getSegmentsByDescription(String description) {
        return segments.stream()
                .filter(segment -> segment.getDescription().equals(description))
                .collect(Collectors.toList());
    }

    //

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Segment put(String key, String value) {
        super.put(key, value);
        return this;
    }

    @Override
    public Segment put(String key, int value) {
        super.put(key, value);
        return this;
    }

    @Override
    public Segment setParent(Segment parent) {
        super.setParent(parent);
        return this;
    }
}
