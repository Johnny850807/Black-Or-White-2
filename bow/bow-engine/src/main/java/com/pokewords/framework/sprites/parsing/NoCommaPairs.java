package com.pokewords.framework.sprites.parsing;

import java.util.Map;

/**
 * @author nyngwang
 */
public class NoCommaPairs extends KeyValuePairs {
    public NoCommaPairs(Node parent) {
        super(parent);
    }

    public NoCommaPairs() {
        super(null);
    }

    @Override
    public void parse(Context context) {
        do {
            String key = context.fetchNextToken(
                    "[^\\s:<>\\[\\]]+", "Invalid key format: " + context.peekToken());
            String colon = context.fetchNextToken(
                    ":", "Expect: " + key + ": " + context.peekToken());
            String value = context.fetchNextToken("[^\\s:<>]+", "Invalid value: " + context.peekToken());
            put(key, value);
            if (!context.peekToken().matches("[^\\s:<>\\[\\]]+"))
                return;
        } while (true);
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
