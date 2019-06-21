package com.pokewords.framework.sprites.parsing;

import java.util.List;

/**
 * @author nyngwang
 */
public abstract class ListNode implements Node {
    private Node parent;
    private String name;

    public ListNode() {
        this.name = "undefined";
    }

    public ListNode(String name) {
        this.name = name;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract int getInt(int index);
    public abstract String getString(int index);
    public abstract List<String> getList();
    public abstract void add(Object object);
    public abstract void remove(Object object);
    public abstract void clear();
}
