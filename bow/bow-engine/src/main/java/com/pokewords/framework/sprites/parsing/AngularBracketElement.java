package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParsingException;

/**
 * @author nyngwang
 */
public class AngularBracketElement extends Element {
    public AngularBracketElement(Node parent, String name, KeyValuePairs keyValuePairs) {
        super(parent, name, keyValuePairs);
    }

    public AngularBracketElement(String name) {
        this(null, name, null);
        keyValuePairs = new NoCommaPairs(this);
    }

    public AngularBracketElement() {
        this(null);
    }

    @Override
    public void parse(Context context) {
        if (!context.peekToken().matches("<[^/\\s]\\S+>"))
            return;
        String openTag = context.fetchNextToken();
        setName(deTag(openTag));
        if (context.hasNextToken())
            keyValuePairs.parse(context);
        String closeTag = context.hasNextToken()?
                context.fetchNextToken(
                        "</" + getName() + ">",
                        "Expect </" + getName() + "> but get: " + context.peekToken())
                : context.fetchNextToken("Run out of token before reaching: </" + getName() + ">");
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[indent]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>\n", getName()));
        resultBuilder.append(keyValuePairs.toString(indent).replaceAll("([^\n]*\n)", spaces + "$1"));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }

    private String deTag(String tag) {
        return tag.replaceAll("</?(\\S+)>", "$1");
    }
}
