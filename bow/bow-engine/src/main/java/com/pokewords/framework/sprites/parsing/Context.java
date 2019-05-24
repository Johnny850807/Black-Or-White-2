package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.utils.FileUtility;
import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.pokewords.framework.sprites.parsing.ScriptDefinitions.LinScript.Samples.*;

/**
 * @author nyngwang
 */
public class Context {
    public static Context fromFile(String path) {
        try {
            return new Context(FileUtility.read(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Context fromText(String text) {
        return new Context(text);
    }

    private List<String> tokens;

    private Context(String scriptText) {
        tokens = new ArrayList<>();
        tokenize(scriptText);
    }

    private void tokenize(String scriptText) {
        Pattern pattern = Pattern.compile("(<\\S+>|[^:\\s]+|:)|(\\S+)");
        Matcher matcher = pattern.matcher(scriptText);

        while (matcher.find()) {
            if (matcher.group(1) == null)
                throw new ScriptParsingException(String.format("Cannot tokenize the symbol: %s", matcher.group(2)));
            tokens.add(matcher.group(1));
        }
    }

    public String peekToken() {
        if (hasNextToken())
            return tokens.get(0);
        return "";
    }

    public boolean hasNextToken() {
        return !tokens.isEmpty();
    }

    public void consumeToken(String noMoreToken) {
        if (!hasNextToken())
            throw new ScriptParsingException(noMoreToken);
        tokens.remove(0);
    }

    public void consumeToken() {
        consumeToken("Run out of tokens");
    }

    public String fetchNextToken() {
        String token = peekToken();
        consumeToken();
        return token;
    }

    public String fetchNextToken(String noMoreToken) {
        String token = peekToken();
        consumeToken(noMoreToken);
        return token;
    }

    public String fetchNextToken(String regex, String noMatch) {
        String token = peekToken();
        consumeToken("Should check hasNextToken() before fetchNextToken() or parse()");
        if (!token.matches(regex))
            throw new ScriptParsingException(noMatch);
        return token;
    }

    public void putBack(String token) {
        tokens.add(0, token);
    }

    public static void main(String[] args) {
        Context context = Context.fromText(SCRIPT_TEXT);
        while (context.hasNextToken()) {
            String openTag = context.fetchNextToken("Can fetch openTag: run out of token");
        }
    }
}
