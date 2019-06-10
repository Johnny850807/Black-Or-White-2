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
            return new Context(FileUtility.read(file), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Context fromPath(String path) {
        try {
            return new Context(FileUtility.read(path), path);
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
    private String source;

    private Context(String scriptText, File file) {
        this(scriptText);
        source = file.getPath();
    }

    private Context(String scriptText, String path) {
        this(scriptText);
        source = path;
    }

    private Context(String scriptText) {
        tokens = new ArrayList<>();
        currentLineNumber = 1;
        recentContinuousNewlinesCount = 0;
        source = "String";
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

    public String getErrorHeader() {
        return String.format("%s: %s", source, currentLineNumber);
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
            throw new ScriptParsingException(String.format("(%s) %s", getErrorHeader(), noMoreToken));

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
            throw new ScriptParsingException(String.format("(%s) %s", getErrorHeader(), noMatch));
        return token;
    }

    public void putBack(String token) {
        for (int i = 1; i <= recentContinuousNewlinesCount; i++)
            tokens.add(0, "\n");
        tokens.add(0, token);
        recentContinuousNewlinesCount = 0;
    }

    public void updateTokens(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        tokens.forEach(token -> builder.append(" ").append(token));
        String remainingText = builder.toString();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            remainingText = remainingText.replaceAll(
                    "[$]" + Pattern.quote(entry.getKey()),
                    entry.getValue());
        }
        tokenize(remainingText);
    }

    public int getCurrentLineNumber() {
        return currentLineNumber;
    }
}
