package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.utils.StringUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * @author johnny850807 (waterball)
 */
public abstract class ListNode implements Node {
    private Node parent;
    private String name;
    private List<String> elements = new ArrayList<>();

    public ListNode() { }

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

    public int getInt(int index) {
        return Integer.parseInt(elements.get(index));
    }

    public String getString(int index) {
        return elements.get(index);
    }

    public List<String> getList() {
        return elements;
    }

    public void add(Object object) {
        elements.add(String.valueOf(object));
    }

    public void remove(Object object) {
        for (int i = 0; i < elements.size(); i++) {
            String s = elements.get(i);
            if (s == object || s.equals(String.valueOf(object))) {
                elements.remove(i);
                return;
            }
        }
    }

    public void clear() {
        elements.clear();
    }

    @Override
    public String toString(int indent) {
        return String.format("%" + indent + "s", this.toString());
    }

    @Override
    public String toString() {
        return String.format("@%s %s", getName(), StringUtility.toString(getList()));
    }

}
