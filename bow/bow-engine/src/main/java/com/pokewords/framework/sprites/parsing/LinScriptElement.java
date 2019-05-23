package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ElementException;

import java.util.*;

/**
 * @author nyngwang
 */
public class LinScriptElement implements Element {
    private Segment parent;
    private Script.Mappings mappings;
    private String name;

    public LinScriptElement(String name) {
        init();
        this.name = name;
    }

    private void init() {
        mappings = new Script.Mappings();
    }

    @Override
    public Element put(String key, String value) {
        mappings.stringMap.put(key, value);
        return this;
    }

    @Override
    public Element put(String key, int value) {
        mappings.integerMap.put(key, value);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getStringByKeyOptional(String key) {
        return Optional.ofNullable(mappings.stringMap.get(key));
    }

    @Override
    public OptionalInt getIntByKeyOptional(String key) {
        return mappings.integerMap.containsKey(key) ? OptionalInt.of(mappings.integerMap.get(key)) : OptionalInt.empty();
    }

    @Override
    public boolean containsKey(String key) {
        return mappings.integerMap.containsKey(key) || mappings.stringMap.containsKey(key);
    }

    @Override
    public String getStringByKey(String key) {
        String result = mappings.stringMap.get(key);
        if (result == null)
            throw new ElementException(String.format("LinScriptElement: attribute %s does not exist", key));
        return result;
    }

    @Override
    public Integer getIntByKey(String key) {
        Integer result = mappings.integerMap.get(key);
        if (result == null)
            throw new ElementException(String.format("LinScriptElement: attribute %s does not exist", key));
        return result;
    }

    @Override
    public Collection<String> getKeys() {
        Set<String> keys =  new HashSet<>(mappings.integerMap.keySet());
        keys.addAll(mappings.stringMap.keySet());
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
        mappings.stringMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        mappings.integerMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        resultBuilder.append(String.format("</%s>\n", name));
        return resultBuilder.toString();
    }

    @Override
    public String toString() {
        return toString(4);
    }
}
