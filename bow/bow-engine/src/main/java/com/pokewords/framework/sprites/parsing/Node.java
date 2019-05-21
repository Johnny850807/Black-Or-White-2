package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.KeyValuePairs;

import java.util.Collection;
import java.util.Optional;
import java.util.OptionalInt;

public abstract class Node {
    private String name;
    private int id;
    private String description;
    private KeyValuePairs keyValuePairs;
    private Segment parent;

    protected Node(String name, int id, String description) {
        init();
        this.name = name;
        this.id = id;
        this.description = description;
    }

    protected Node(String name) {
        init();
        this.name = name;
        this.id = Integer.MIN_VALUE;
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

    public void setParent(Segment parent) {
        this.parent = parent;
    }

    public Segment getParent() {
        return parent;
    }

    public String keyValuePairsToString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        keyValuePairs.getMap()
                .forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        return resultBuilder.toString();
    }

    public String keyValuePairsToString() {
        return keyValuePairsToString(0);
    }

    protected void parseKeyValuePairs(Context context) {
        do {
            String token;
        } while (true);
    }

    public abstract void parse(Context context);

    static void main(String[] args) {
        Segment script = new LinScriptSegment("name", 1);
        // 1
        script.parse(new Context("path/to/lin_script_text"));
        // 2
        System.out.println(script);
    }
}
