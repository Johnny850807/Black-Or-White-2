package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.bundles.ReadOnlyBundle;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public abstract class Segment extends Element {
    protected int id;
    protected Optional<String> description;
    private List<Element> elements;

    public Segment(Node parent, String name, @NotNull KeyValuePairs keyValuePairs,
                   int id, String description, List<Element> elements) {
        super(parent, name, keyValuePairs);
        this.id = id;
        this.description = Optional.ofNullable(description);
        this.elements = new ArrayList<>();
        elements.forEach(this::addElement);
    }

    public int getId() {
        return id;
    }

    public Optional<String> getDescription() {
        return description;
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
