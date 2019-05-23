package com.pokewords.framework.sprites.parsing;

import java.util.*;

public abstract class KeyValuePairs implements Node {
    private Map<String, String> pairs;

    public KeyValuePairs() {
        pairs = new HashMap<>();
    }

    public void put(String key, String value) {
        pairs.put(key, value);
    }

    public void put(String key, int value) {
        pairs.put(key, String.valueOf(value));
    }

    public boolean containsKey(String key) {
        return pairs.containsKey(key);
    }

    public String getString(String key) {
        return pairs.get(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(pairs.get(key));
    }

    public Optional<String> getStringOptional(String key) {
        return pairs.containsKey(key)? Optional.of(pairs.get(key)) : Optional.empty();
    }

    public OptionalInt getIntOptional(String key) {
        return pairs.containsKey(key)? OptionalInt.of(Integer.parseInt(pairs.get(key))) : OptionalInt.empty();
    }

    public Collection<String> getKeys() {
        return pairs.keySet();
    }

    public Map<String, String> getPairs() {
        return pairs;
    }
}
