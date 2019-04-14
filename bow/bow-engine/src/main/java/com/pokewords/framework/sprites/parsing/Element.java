package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ElementAttributeNameDoesNotExistException;
import sun.plugin.dom.exception.InvalidAccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class Element {

    private Map<String, String> stringMap;
    private Map<String, Integer> intMap;

    public Element(Map<String, String> stringMap, Map<String, Integer> intMap) {
        this.stringMap = stringMap;
        this.intMap = intMap;
    }

    // [!] The two getter's behavior of throwing exception is not consistent.

    public String getString(String name){
        return stringMap.getOrDefault(name, "");
    }

    public int getInt(String name){
        if (!intMap.containsKey(name)) {
            throw new ElementAttributeNameDoesNotExistException("Invalid attribute name for element!");
        }
        return intMap.get(name);
    }

}
