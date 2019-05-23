package com.pokewords.framework.sprites.parsing;

import java.util.*;

/**
 * @author nyngwang
 */
public abstract class KeyValuePairs implements Node {
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
}
