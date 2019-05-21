package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.Pair;
import com.pokewords.framework.commons.Triple;
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
    private List<Triple<String, Pair<String, String>, String>> tokens = new ArrayList<>();
    private String tag;
    private String key, value;
    private String single;

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

    public static String deTag(String tag) {
        return tag.replaceAll("</?(\\S+)>", "$1");
    }

    private Context(String scriptText) {
        tokenize(scriptText);
    }

    private void tokenize(String scriptText) {
        Pattern pattern = Pattern.compile(
                "(<\\S+>)" +
                "|(\\S+)\\s*:\\s*(\\S+)" +
                "|(\\S+)");
        Matcher matcher = pattern.matcher(scriptText);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                tokens.add(new Triple<>(matcher.group(1), null, null));
            } else if (matcher.group(2) != null && matcher.group(3) != null) {
                tokens.add(new Triple<>(null, new Pair<>(matcher.group(2), matcher.group(3)), null));
            } else if (matcher.group(4) != null) {
                tokens.add(new Triple<>(null, null, matcher.group(4)));
            } else {
                throw new ContextException(String.format("Unrecognizable token: %s", matcher.group(0)));
            }
        }
    }

    public boolean fetchNextToken() {
        if (tokens.size() > 0) {
            consumeToken(tokens.remove(0));
            return true;
        }
        return false;
    }

    private void consumeToken(Triple<String, Pair<String, String>, String> token) {
        tag = token.getFirst();
        key = token.getSecond() != null? token.getSecond().getFirst() : null;
        value = token.getSecond() != null? token.getSecond().getSecond() : null;
        single = token.getThird();
    }

    public String getTag() {
        return tag;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getSingle() {
        return single;
    }

    public Triple<String, Pair<String, String>, String> peekNextToken() {
        if (tokens.isEmpty())
            return new Triple<>(null, null, null);
        return tokens.get(0);
    }

    public static void main(String[] args) {
        Context context = Context.fromText(SCRIPT_TEXT);
        while (context.fetchNextToken()) {
            String tag = context.getTag();
            String key = context.getKey();
            String value = context.getValue();
            String single = context.getSingle();

            String tagName = Context.deTag(tag);
        }
    }
}
