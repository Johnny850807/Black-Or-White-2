package com.pokewords.framework.sprites.parsing;

import java.util.Map;

public class NoCommaPairs extends KeyValuePairs {
    private Element parent;

    public NoCommaPairs(Element parent) {
        this.parent = parent;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public boolean parse(Context context) {
        if (context.getKey() == null || context.getValue() == null)
            return false;

        return false;
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        int counter = 0;
        int width = 5;

        for (Map.Entry entry: getMap().entrySet()) {
            counter = counter % width + 1;
            resultBuilder.append(
                    String.format("%s%s: %s%s",
                            counter > 1? " ": "",
                            entry.getKey(), entry.getValue(),
                            counter == width? "\n": ""));
        }

        if (counter != width)
            resultBuilder.append('\n');

        return resultBuilder.toString();
    }
}
