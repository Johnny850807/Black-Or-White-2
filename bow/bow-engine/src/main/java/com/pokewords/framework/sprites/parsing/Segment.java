package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Segment can contain Elements or Segments.
 * @author nyngwang
 */
public abstract class Segment extends Node {
    protected int id;
    protected String description;
    protected List<Element> elements;
    protected Script parent;

    public Segment(String name) {
        super(name);
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
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

    public Segment put(String key, String value) {
        keyValuePairs.put(key, value);
        return this;
    }

    public Segment put(String key, int value) {
        keyValuePairs.put(key, value);
        return this;
    }

    public Segment setParent(Script parent) {
        this.parent = parent;
        return this;
    }

    // Parsing utils

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
