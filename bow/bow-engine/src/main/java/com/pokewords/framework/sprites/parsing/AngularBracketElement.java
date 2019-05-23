package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ElementException;

public class AngularBracketElement extends Element {
    public AngularBracketElement(String name, Node parent, KeyValuePairs keyValuePairs) {
        super(name, parent, keyValuePairs);
    }

    public AngularBracketElement(String name) {
        super(name, null, null);
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
        name = deTag(openTag);
        String closeTag = String.format("</%s>", name);

        keyValuePairs.parse(context);
        String shouldBeCloseTag = context.fetchNextToken();
        if (!shouldBeCloseTag.equals(closeTag))
            throw new ElementException(String.format(
                    "Angular brackets do not match: %s %s", openTag, shouldBeCloseTag));
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[indent]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>\n", name));
        resultBuilder.append(keyValuePairs.toString(indent).replaceAll("([^\n]*\n)", spaces + "$1"));
        resultBuilder.append(String.format("</%s>\n", name));
        return resultBuilder.toString();
    }

    private String deTag(String tag) {
        return tag.replaceAll("</?(\\S+)>", "$1");
    }
}
