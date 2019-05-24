package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public abstract class Segment extends Element {
    private List<Element> elements;

    public Segment(Node parent, String name, KeyValuePairs keyValuePairs, List<Element> elements) {
        super(parent, name, keyValuePairs);
        this.elements = elements;
    }

    public void addElement(Element element) {
        elements.add(element);
        element.setParent(this);
    }

    public boolean containsElement(String name) {
        for (Element element : elements)
            if (element.getName().equals(name))
                return true;
        return false;
    }

    public List<Element> getElements() {
        return elements;
    }

    public List<Element> getElements(String name) {
        return elements.stream()
                .filter(element -> element.getName().equals(name))
                .collect(Collectors.toList());
    }

    public Element getFirstElement(String name) {
        return containsElement(name)? getElements(name).get(0) : null;
    }

    public Optional<Element> getFirstElementOptional(String name) {
        return containsElement(name)? Optional.of(getElements(name).get(0)) : Optional.empty();
    }
}
