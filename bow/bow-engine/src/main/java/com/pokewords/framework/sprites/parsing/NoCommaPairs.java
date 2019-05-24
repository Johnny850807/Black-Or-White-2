package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.KeyValuePairsException;
import com.pokewords.framework.engine.exceptions.ScriptParsingException;

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
            String key = context.fetchNextToken("[^\\s:<>]+", "Invalid key " + context.peekToken());
            String colon = context.hasNextToken()?
                    context.fetchNextToken(
                            ":",
                            "Expect a colon between " + key + " and " + context.peekToken())
                    : context.fetchNextToken("Run out of token after " + key);
            String value = context.hasNextToken()?
                    context.fetchNextToken(
                            "[^\\s:<>]+",
                            "Invalid value " + context.peekToken())
                    : context.fetchNextToken("Run out of token after colon(:)");
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
