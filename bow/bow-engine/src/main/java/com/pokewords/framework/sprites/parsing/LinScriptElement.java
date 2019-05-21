package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.KeyValuePairs;
import com.pokewords.framework.engine.exceptions.ElementException;

import java.util.*;

/**
 * @author nyngwang
 */
public class LinScriptElement implements Element {
    private Segment parent;
    private KeyValuePairs mapping;
    private String name;

    public LinScriptElement(String name) {
        init();
        this.name = name;
    }

    private void init() {
        mapping = new KeyValuePairs();
    }

    @Override
    public Element put(String key, String value) {
        mapping.put(key, value);
        return this;
    }

    @Override
    public Element put(String key, int value) {
        mapping.put(key, value);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getStringOptional(String key) {
        return Optional.ofNullable(mapping.stringMap.get(key));
    }

    @Override
    public OptionalInt getIntOptional(String key) {
        return mapping.integerMap.containsKey(key) ? OptionalInt.of(mapping.integerMap.get(key)) : OptionalInt.empty();
    }

    @Override
    public boolean containsKey(String key) {
        return mapping.integerMap.containsKey(key) || mapping.stringMap.containsKey(key);
    }

    @Override
    public String getString(String key) {
        String result = mapping.stringMap.get(key);
        if (result == null)
            throw new ElementException(String.format("LinScriptElement: attribute %s does not exist", key));
        return result;
    }

    @Override
    public Integer getInt(String key) {
        Integer result = mapping.integerMap.get(key);
        if (result == null)
            throw new ElementException(String.format("LinScriptElement: attribute %s does not exist", key));
        return result;
    }

    @Override
    public Collection<String> getKeys() {
        Set<String> keys = new HashSet<>(mapping.integerMap.keySet());
        keys.addAll(mapping.stringMap.keySet());
        return keys;
    }

    @Override
    public Element setParent(Segment parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public Segment getParent() {
        return parent;
    }

    // Pretty print
    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>\n", name));
        mapping.getMap().forEach((k, v) -> resultBuilder.append(String.format(indent + "%s: %s\n", k, v)));
        resultBuilder.append(String.format("</%s>\n", name));
        return resultBuilder.toString();
    }

    @Override
    public String toString() {
        return toString(4);
    }
}
