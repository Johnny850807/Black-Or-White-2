package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.utils.FileUtility;
import com.pokewords.framework.engine.exceptions.ContextException;

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
                throw new ContextException(String.format("Invalid token: \"%s\".", matcher.group(2)));
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

    public void consumeToken() {
        if (hasNextToken())
            tokens.remove(0);
    }

    public String fetchToken() {
        String token = peekToken();
        consumeToken();
        return token;
    }

    public static void main(String[] args) {
        Context context = Context.fromText(SCRIPT_TEXT);
        while (context.hasNextToken()) {
            String current = context.fetchToken();
        }
    }
}
