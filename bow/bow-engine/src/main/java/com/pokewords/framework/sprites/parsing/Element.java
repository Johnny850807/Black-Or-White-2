package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ElementAttributeNameDoesNotExistException;

import java.util.HashMap;
import java.util.Map;

public class Element {

    private Map<String, String> stringMap;
    private Map<String, Integer> intMap;

    public Element() {
        this.stringMap = new HashMap<>();
        this.intMap = new HashMap<>();
    }

    // [!] The two getter's behavior of throwing exception is not consistent.

    public void addPair(String name, int value) {
        intMap.put(name, value);
    }

    public void addPair(String name, String value) {
        stringMap.put(name, value);
    }

    public String getString(String name){
        if (!stringMap.containsKey(name)) {

        }
        return stringMap.getOrDefault(name, "");
    }

    public int getInt(String name){
        if (!intMap.containsKey(name)) {
            throw new ElementAttributeNameDoesNotExistException("Invalid attribute name for element!");
        }
        return intMap.get(name);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
