package com.pokewords.framework.sprites.parsing;

import java.util.*;

/**
 * Each segment is a composite Node.
 * @author nyngwang
 */
public abstract class Segment extends Node {
    private List<Element> elements;
    private List<Segment> segments;

    void addElement(Element element) {

    }

    boolean containsElement(String name) {

    }

    boolean containsElementId(int id) {

    }

    boolean containsElementDescription() {

    }

    List<Element> getElements(String name);

    List<Element> getElements();

    default Element getElement(String name) {
        return containsElement(name)? getElements(name).get(0) : null;
    }

    String toString(int indentation);
}
