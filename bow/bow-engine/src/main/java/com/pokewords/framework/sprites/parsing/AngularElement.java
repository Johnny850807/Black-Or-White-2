package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.bundles.ReadOnlyBundle;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;

/**
 * @author nyngwang
 */
public class AngularElement extends Element {
    public AngularElement() {}

    public AngularElement(String name) {
        super(name);
    }

    @Override
    public void parse(Context context) {
        String openTag = context.fetchNextToken(
                "<[^/\\s]\\S+>",
                "Invalid open tag: " + context.peekToken());
        setName(openTag.replaceAll("<(\\S+)>", "$1"));

        while (context.peekToken().matches("[^\\s:<>\\[\\]]+")) {
            String key = context.fetchNextToken(
                    "[^\\s:<>\\[\\]]+", "Invalid key format: " + context.peekToken());
            String colon = context.fetchNextToken(
                    ":", "Expect: " + key + ": " + context.peekToken());
            String value = context.fetchNextToken("[^\\s:<>]+", "Invalid value: " + context.peekToken());
            put(key, value);
        }

        String closeTag = context.fetchNextToken(
                "</" + getName() + ">",
                "Expect </" + getName() + "> but get: " + context.peekToken());
    }

    @Override
    public String toString(int contentIndent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[contentIndent]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>\n", getName()));

        StringBuilder resultBuilder2 = new StringBuilder();
        int counter = 0;
        int width = 5;
        for (Map.Entry entry: getMap().entrySet()) {
            counter = counter % width + 1;
            resultBuilder2.append(
                    String.format("%s%s: %s%s",
                            counter > 1? " ": "",
                            entry.getKey(), entry.getValue(),
                            counter == width? "\n": ""));
        }
        if (counter > 0 && counter != width)
            resultBuilder2.append('\n');

        resultBuilder.append(resultBuilder2.toString().replaceAll("([^\n]*\n)", spaces + "$1"));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }
}
