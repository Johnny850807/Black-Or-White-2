package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Segment can contain Elements or Segments.
 * @author nyngwang
 */
public abstract class Segment extends Node {
    protected List<Element> elements;
    protected int id;
    protected String description;

    protected Segment(String name, int id, String description) {
        super(name, id, description);
    }

    protected int getId() {
        return id;
    }

    protected String getDescription() {
        return description;
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

    // Override for Fluent interface

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
    protected Segment setParent(Segment parent) {
        super.setParent(parent);
        return this;
    }

    protected boolean parseId(Context context) {
        if (context.getSingle() == null)
            return false;
        id = Integer.parseInt(context.getSingle());
        return true;
    }

    protected boolean parseDescription(Context context) {
        if (context.getSingle() == null)
            return false;
        description = context.getSingle();
        return true;
    }
}
