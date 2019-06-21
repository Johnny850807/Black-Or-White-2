package com.pokewords.framework.sprites.parsing;

import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author nyngwang
 */
public abstract class Segment extends Element {
    private int id;
    private Optional<String> description;

    public Segment() {
        setId(Integer.MIN_VALUE);
        setDescription(null);
    }

    public Segment(String name, int id, @Nullable String description) {
        super(name);
        setId(id);
        setDescription(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null? Optional.of(description) : Optional.empty();
    }

    public abstract void addElement(Element element);
    public abstract boolean containsElement(String name);
    public abstract List<Element> getElements();
    public abstract List<Element> getElements(String name);
    public abstract Element getFirstElement(String name);
    public abstract Optional<Element> getFirstElementOptional(String name);

    public abstract void addListNode(ListNode listNode);
    public abstract boolean containsListNode(String name);
    public abstract ListNode getListNode(String name);
    public abstract Optional<ListNode> getListNodeOptional(String name);
}
