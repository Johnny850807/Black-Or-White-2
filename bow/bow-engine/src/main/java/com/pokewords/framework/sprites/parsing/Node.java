package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.KeyValuePairs;
import com.pokewords.framework.commons.Pair;
import com.pokewords.framework.commons.Triple;
import com.pokewords.framework.engine.exceptions.NodeException;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;

public abstract class Node {
    private String name;
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

    protected String getName() {
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

    protected String keyValuePairsToString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        keyValuePairs.getMap().forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        return resultBuilder.toString();
    }

    protected String keyValuePairsToString() {
        return keyValuePairsToString(0);
    }

    protected void parseNameIdDescription(Context context) {
        name = Context.deTag(context.getTag());

        fetchAndCheck(context);
        if (context.getSingle() == null) return;
        id = Integer.parseInt(context.getSingle());

        fetchAndCheck(context);
        if (context.getSingle() == null) return;
        description = context.getSingle();
    }

    private boolean fetchAndCheck(Context context) {
        if (!context.fetchNextToken())
            throw new NodeException(String.format("Tag <%s> is not closed", name));
        return true;
    }

    protected void parseKeyValuePairs(Context context) {
        do {
            if (context.getKey() == null) return;
            put(context.getKey(), context.getValue());
        } while (fetchAndCheck(context));
    }

    public abstract void parse(Context context);
    public abstract String toString(int indentation);
    @Override
    public String toString() {
        return toString(4);
    }

    static void main(String[] args) {
        Segment script = new LinScriptSegment("name", 1);
        // 1
        script.parse(Context.fromFile("path/to/lin_script_text"));
        // 2
        System.out.println(script);
    }
}
