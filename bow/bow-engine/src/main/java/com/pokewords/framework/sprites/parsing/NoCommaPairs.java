package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.KeyValuePairsException;

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
        while (context.hasNextToken()) {
            if (context.peekToken().matches("<\\S+>"))
                return;
            String key = context.fetchNextToken();
            String colon = context.fetchNextToken();
            String value = context.fetchNextToken();

            if (!colon.equals(":"))
                throw new KeyValuePairsException(String.format(
                        "This is not a key-value pair: %s %s %s", key, colon, value));
            if (value.matches("<\\S+>"))
                throw new KeyValuePairsException(String.format(
                        "The value cannot be a tag: %s %s %s", key, colon, value));
            put(key, value);
        }
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
