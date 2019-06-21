package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.bundles.Packable;
import com.pokewords.framework.commons.bundles.ReadOnlyBundle;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;


/**
 * @author nyngwang
 */
public abstract class Element implements Node, Packable, Iterable<Pair<String, String>> {
    private Node parent;
    private String name;
    private KeyValuePairs keyValuePairs;

    public Element(Node parent, String name, @NotNull KeyValuePairs keyValuePairs) {
        this.parent = parent;
        this.name = name;
        this.keyValuePairs = keyValuePairs;
        keyValuePairs.setParent(this);
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

    public KeyValuePairs getKeyValuePairs() {
        return keyValuePairs;
    }

    public int getInt(String key) {
        return keyValuePairs.getInt(key);
    }

    public String getString(String key) {
        return keyValuePairs.getString(key);
    }

    public Optional<String> getStringOptional(String key) {
        return keyValuePairs.getStringOptional(key);
    }

    public OptionalInt getIntOptional(String key) {
        return keyValuePairs.getIntOptional(key);
    }

    public boolean containsKey(String key) {
        return keyValuePairs.containsKey(key);
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
