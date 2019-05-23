package com.pokewords.framework.sprites.parsing;

import java.util.Map;

public class NoCommaPairs extends KeyValuePairs {
    private Element parent;

    protected boolean parseKeyValuePair(Context context) {
        if (context.getKey() == null || context.getValue() == null)
            return false;
        keyValuePairs.put(context.getKey(), context.getValue());
        return true;
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

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void parse(Context context) {

    }

    @Override
    protected boolean parseTag(Context context) {
        return false;
    }

    @Override
    protected boolean parseKeyValuePair(Context context) {
        return false;
    }

    @Override
    protected boolean parseClosedTag(Context context) {
        return false;
    }

    @Override
    protected String keyValuePairsToString(int width) {
        return null;
    }

    @Override
    public String toString(int indentation, int width) {
        return null;
    }
}
