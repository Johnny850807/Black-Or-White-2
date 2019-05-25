package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.bundles.Packable;
import com.pokewords.framework.commons.bundles.ReadOnlyBundle;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author nyngwang
 */
public abstract class KeyValuePairs implements Node, Packable, Iterable<Pair<String, String>> {
    private Map<String, String> map;
    private Node parent;

    public KeyValuePairs(Node parent) {
        this.map = new HashMap<>();
        this.parent = parent;
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public void put(String key, int value) {
        map.put(key, String.valueOf(value));
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public String getString(String key) {
        return map.get(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(map.get(key));
    }

    public Optional<String> getStringOptional(String key) {
        return map.containsKey(key)? Optional.of(map.get(key)) : Optional.empty();
    }

    public OptionalInt getIntOptional(String key) {
        return map.containsKey(key)? OptionalInt.of(Integer.parseInt(map.get(key))) : OptionalInt.empty();
    }

    public Collection<String> getKeys() {
        return map.keySet();
    }

    public Map<String, String> getMap() {
        return map;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public ReadOnlyBundle pack() {
        ReadOnlyBundle readOnlyBundle = new ReadOnlyBundle();
        for (Pair pair : this)
            readOnlyBundle.put(pair.getKey(), pair.getValue());
        return readOnlyBundle;
    }

    @NotNull
    @Override
    public Iterator<Pair<String, String>> iterator() {
        return new Iterator<Pair<String, String>>() {
            private Iterator<Map.Entry<String, String>> mapIterator = map.entrySet().iterator();
            @Override
            public boolean hasNext() {
                return mapIterator.hasNext();
            }

            @Override
            public Pair<String, String> next() {
                Map.Entry<String, String> nextEntry = mapIterator.next();
                return new Pair<>(nextEntry.getKey(), nextEntry.getValue());
            }
        };
    }
}
