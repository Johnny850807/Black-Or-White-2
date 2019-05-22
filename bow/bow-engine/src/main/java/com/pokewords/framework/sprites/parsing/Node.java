package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.KeyValuePairs;
import com.pokewords.framework.commons.Pair;
import com.pokewords.framework.commons.Triple;
import com.pokewords.framework.engine.exceptions.ContextException;
import com.pokewords.framework.engine.exceptions.NodeException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public abstract class Node {
    protected String name;
    protected int id;
    protected String description;
    protected KeyValuePairs keyValuePairs;
    protected Segment parent;

    protected Node(String name, int id, String description) {
        init();
        this.name = name;
        this.id = id;
        this.description = description;
    }

    private void init() {
        keyValuePairs = new KeyValuePairs();
    }

    public String getName() {
        return name;
    }

    protected int getId() {
        return id;
    }

    protected String getDescription() {
        return description;
    }

    protected Node put(String key, String value) {
        keyValuePairs.put(key, value);
        return this;
    }

    protected Node put(String key, int value) {
        keyValuePairs.put(key, value);
        return this;
    }

    public boolean containsKey(String key) {
        return keyValuePairs.containsKey(key);
    }

    public String getString(String key) {
        return keyValuePairs.getString(key);
    }

    public Integer getInt(String key) {
        return keyValuePairs.getInt(key);
    }

    public Optional<String> getStringOptional(String key) {
        return keyValuePairs.containsKey(key)? Optional.of(keyValuePairs.getString(key)) : Optional.empty();
    }

    public OptionalInt getIntOptional(String key) {
        return keyValuePairs.containsKey(key)? OptionalInt.of(keyValuePairs.getInt(key)) : OptionalInt.empty();
    }

    public Collection<String> getKeys() {
        return keyValuePairs.getMap().keySet();
    }

    protected Node setParent(Segment parent) {
        this.parent = parent;
        return this;
    }

    public Segment getParent() {
        return parent;
    }

    protected boolean parseTag(Context context) {
        if (context.getTag() == null)
            return false;
        name = Context.deTag(context.getTag());
        return true;
    }

    protected boolean parseId(Context context) {
        if (context.getSingle() == null)
            return false;
        id = Integer.parseInt(context.getSingle());
        return true;
    }

    protected boolean parseDescription(Context context) {
        if (context.getSingle() == null)
            return false;
        description = context.getSingle();
        return true;
    }

    protected boolean parseKeyValuePair(Context context) {
        if (context.getKey() == null || context.getValue() == null)
            return false;
        keyValuePairs.put(context.getKey(), context.getValue());
        return true;
    }

    protected boolean parseClosedTag(Context context) {
        if (context.getTag() == null)
            return false;
        if (!name.equals(Context.deTag(context.getTag())))
            return false;
        return true;
    }

    public abstract void parse(Context context);

    public abstract String toString(int indentation, int width);

    @Override
    public String toString() {
        return toString(2, 4);
    }

    protected String keyValuePairsToString(int width) {
        StringBuilder resultBuilder = new StringBuilder();
        int counter = 0;
        for (Map.Entry entry: keyValuePairs.getMap().entrySet()) {
            counter = counter % width + 1;
            resultBuilder.append(String.format(
                    "%s%s: %s%s",
                    counter > 1? " ": "", entry.getKey(), entry.getValue(), counter == width? "\n": ""));
        }
        if (counter != width)
            resultBuilder.append('\n');
        return resultBuilder.toString();
    }

    static void main(String[] args) {
        Segment script = new LinScriptSegment();
        // 1
        script.parse(Context.fromFile("path/to/lin_script_text"));
        // 2
        System.out.println(script);
    }
}
