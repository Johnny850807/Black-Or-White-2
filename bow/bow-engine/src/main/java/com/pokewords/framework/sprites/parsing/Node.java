package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.KeyValuePairs;
import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * A Node provides some basic data structures / data.
 * @author nyngwang
 */
public abstract class Node {
    protected String name;
    protected KeyValuePairs keyValuePairs;

    protected Node(String name) {
        this.name = name;
        keyValuePairs = new KeyValuePairs();
    }

    public String getName() {
        return name;
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

    protected boolean parseTag(Context context) {
        if (context.getTag() == null)
            return false;
        name = Context.deTag(context.getTag());
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
        return name.equals(Context.deTag(context.getTag()));
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

    public static void main(String[] args) {
        Segment script = new DefaultSegment();
        // 1
        script.parse(Context.fromFile("path/to/lin_script_text"));
        // 2
        System.out.println(script);
    }
}
