package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.bundles.Packable;
import com.pokewords.framework.commons.bundles.ReadOnlyBundle;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;


/**
 * @author nyngwang
 */
public abstract class Element implements Node, Packable, Iterable<Pair<String, String>> {
    private Node parent;
    private String name;
    protected KeyValuePairs keyValuePairs;

    public Element(Node parent, String name, KeyValuePairs keyValuePairs) {
        this.parent = parent;
        this.name = name;
        this.keyValuePairs = keyValuePairs;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KeyValuePairs getKeyValuePairs() {
        return keyValuePairs;
    }

    public int getInt(String key) {
        return keyValuePairs.getInt(key);
    }

    public String getString(String key) {
        return keyValuePairs.getString(key);
    }

    @Override
    public ReadOnlyBundle pack() {
        return keyValuePairs.pack();
    }

    @NotNull
    @Override
    public Iterator<Pair<String, String>> iterator() {
        return keyValuePairs.iterator();
    }
}
