package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author johnny850807 (waterball)
 */
public abstract class ListNode implements Node {
    private Node parent;
    private String name;
    private List<String> list = new ArrayList<>();

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
        return Integer.parseInt(list.get(index));
    }

    public String getString(int index) {
        return list.get(index);
    }

    public List<String> getList() {
        return list;
    }

    public void add(Object object) {
        list.add(String.valueOf(object));
    }

    public void remove(Object object) {
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s == object || s.equals(String.valueOf(object))) {
                list.remove(i);
                return;
            }
        }
    }

    public void clear() {
        list.clear();
    }
}
