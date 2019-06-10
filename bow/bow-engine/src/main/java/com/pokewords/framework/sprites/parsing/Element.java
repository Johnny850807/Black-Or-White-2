package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.bundles.Packable;
import javafx.util.Pair;
import java.util.Map;


/**
 * @author nyngwang
 */
public abstract class Element implements Node, Packable, Iterable<Pair<String, String>> {
    private Node parent;
    private String name;

    public Element() {
        this.name = "undefined";
    }

    public Element(String name) {
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

    public abstract KeyValuePairs getKeyValuePairs();
    public abstract Map<String, String> getMap();
    public abstract int getInt(String key);
    public abstract String getString(String key);
}
