package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.utils.FileUtility;
import com.pokewords.framework.engine.exceptions.ScriptParsingException;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.pokewords.framework.sprites.parsing.ScriptSample.LinScript.*;

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
    private int currentLineNumber;
    private int bufferedLinesCount;

    private Context(String scriptText) {
        tokens = new ArrayList<>();
        currentLineNumber = 1;
        bufferedLinesCount = 0;
        tokenize(scriptText);
    }

    private void tokenize(String scriptText) {
        Pattern pattern = Pattern.compile("(<\\S+>|[^:\\s]+|:|\\n)|(\\S+)");
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
        while (!tokens.isEmpty() && tokens.get(0).equals("\n")) {
            bufferedLinesCount++;
            tokens.remove(0);
        }
        return !tokens.isEmpty();
    }

    public void consumeOneToken(String noMoreToken) {
        if (!hasNextToken())
            throw new ScriptParsingException(String.format("(%s) %s", currentLineNumber, noMoreToken));
        currentLineNumber += bufferedLinesCount;
        bufferedLinesCount = 0;
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
        tokens.add(0, token);
    }

    public int getRemainingTokensCount() {
        return tokens.size();
    }

    public int getCurrentLineNumber() {
        return currentLineNumber;
    }

    public static void main(String[] args) {
        Context context = Context.fromText(SCRIPT_TEXT);
        while (context.hasNextToken()) {
            String openTag = context.fetchNextToken("Can fetch openTag: run out of token");
        }
    }
}
