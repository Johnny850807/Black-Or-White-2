package com.pokewords.framework.commons;

import java.util.HashMap;
import java.util.Map;

public class KeyValuePairs {
    private Map<String, String> map;

    public KeyValuePairs() {
        map = new HashMap<>();
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
}
