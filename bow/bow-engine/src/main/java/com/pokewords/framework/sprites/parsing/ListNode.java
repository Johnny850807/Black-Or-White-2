package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author johnny850807 (waterball)
 */
public abstract class ListNode implements Node {
    private Node parent;
    private String name;

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

//    public void remove(Object object) {
//        for (int i = 0; i < list.size(); i++) {
//            String s = list.get(i);
//            if (s == object || s.equals(String.valueOf(object))) {
//                list.remove(i);
//                return;
//            }
//        }
//    }

    public abstract void clear();
}
