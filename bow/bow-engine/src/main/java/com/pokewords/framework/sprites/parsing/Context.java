package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.utils.FileUtility;
import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nyngwang
 */
public class Context {
    public static Context fromFile(File file) {
        try {
            return fromText(FileUtility.read(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Context fromPath(String path) {
        try {
            return fromText(FileUtility.read(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Context fromText(String text) {
        return new Context(text);
    }

    private List<String> tokens;
    private int currentLineNumber;
    private int recentContinuousNewlinesCount;

    private Context(String scriptText) {
        tokens = new ArrayList<>();
        currentLineNumber = 1;
        recentContinuousNewlinesCount = 0;
        tokenize(scriptText);
    }

    private void tokenize(String scriptText) {
        // TODO: Generator's [1 2 3 4] should be parsed into tokens: [, 1, 2, 3, 4, ]
        Pattern pattern = Pattern.compile("(<\\S+>|\\[[^,]+?]|[^:,\\s]+|:|,|\\[|]|\\n)|(\\S+)");
        Matcher matcher = pattern.matcher(scriptText);

        while (matcher.find()) {
            if (matcher.group(1) == null)
                throw new ScriptParsingException(String.format("Cannot tokenize the symbol: %s", matcher.group(2)));
            tokens.add(matcher.group(1));
        }
    }

    /**
     * Will not return '\n' to client
     */
    public String peekToken() {
        if (hasNextToken()) {
            int index = 0;
            while (tokens.get(index).equals("\n"))
                index++;
            return tokens.get(index);
        }
        return "";
    }

    public boolean hasNextToken() {
        int newlinesCount = 0;
        while (!tokens.isEmpty() && tokens.get(0).equals("\n")) {
            newlinesCount++;
            tokens.remove(0);
        }

        boolean result = !tokens.isEmpty();

        for (int i = 1; i <= newlinesCount; i++)
            tokens.add(0, "\n");

        return result;
    }

    public void consumeOneToken(String noMoreToken) {
        if (!hasNextToken())
            throw new ScriptParsingException(String.format("(%s) %s", currentLineNumber, noMoreToken));

        recentContinuousNewlinesCount = 0;

        while (tokens.get(0).equals("\n")) {
            tokens.remove(0);
            currentLineNumber++;
            recentContinuousNewlinesCount++;
        }

        tokens.remove(0);
    }

    public void consumeOneToken() {
        consumeOneToken("Run out of tokens");
    }

    public String fetchNextToken() {
        String token = peekToken();
        consumeOneToken();
        return token;
    }

    public String fetchNextToken(String noMoreToken) {
        String token = peekToken();
        consumeOneToken(noMoreToken);
        return token;
    }

    public String fetchNextToken(String regex, String noMatch) {
        String token = peekToken();
        consumeOneToken();
        if (!token.matches(regex))
            throw new ScriptParsingException(String.format("(%s) %s", currentLineNumber, noMatch));
        return token;
    }

    public void putBack(String token) {
        for (int i = 1; i <= recentContinuousNewlinesCount; i++)
            tokens.add(0, "\n");
        tokens.add(0, token);
        recentContinuousNewlinesCount = 0;
    }

    public void bind(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        tokens.forEach(token -> builder.append(" ").append(token));
        String remainingText = builder.toString();
        map.forEach((key, value) ->
            remainingText.replaceAll(
                    "([-+]?)[$]" + Pattern.quote(key),
                    "$1" + value));
        tokenize(remainingText);
    }

    public int getCurrentLineNumber() {
        return currentLineNumber;
    }
}
