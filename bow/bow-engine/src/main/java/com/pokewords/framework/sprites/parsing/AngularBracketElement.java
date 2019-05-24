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
        if (!context.hasNextToken())
            return;
        String openTag = context.fetchNextToken();
        if (!openTag.matches("<[^/\\s]\\S+>"))
            throw new ScriptParsingException(String.format(
                    "Should be a <openTag> but receive: %s", openTag));
        setName(deTag(openTag));
        keyValuePairs = new NoCommaPairs(this);
        keyValuePairs.parse(context);
        if (!context.hasNextToken())
            throw new ScriptParsingException(String.format(
                    "The </closeTag> for %s is missing", openTag));
        String closeTag = context.fetchNextToken();
        if (!closeTag.equals(String.format("</%s>", getName())))
            throw new ScriptParsingException(String.format(
                    "The <openTag> is incorrectly closed: %s %s", openTag, closeTag));
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
