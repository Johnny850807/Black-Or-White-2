package com.pokewords.framework.sprites.parsing;

import org.jetbrains.annotations.NotNull;

/**
 * @author nyngwang
 */
public class AngularBracketElement extends Element {
    public AngularBracketElement(Node parent, String name, @NotNull KeyValuePairs keyValuePairs) {
        super(parent, name, keyValuePairs);
    }

    public AngularBracketElement(String name) {
        this(null, name, new NoCommaPairs());
    }

    public AngularBracketElement() {
        this(null);
    }

    @Override
    public void parse(Context context) {
        String openTag = context.fetchNextToken(
                "<[^/\\s]\\S+>",
                "Invalid open tag: " + context.peekToken());
        setName(deTag(openTag));
        if (context.peekToken().matches("[^\\s:<>]+"))
            getKeyValuePairs().parse(context);
        String closeTag = context.fetchNextToken(
                "</" + getName() + ">",
                "Expect </" + getName() + "> but get: " + context.peekToken());
    }

    @Override
    public String toString(int indent) {
        StringBuilder resultBuilder = new StringBuilder();
        String spaces = new String(new char[indent]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>\n", getName()));
        resultBuilder.append(getKeyValuePairs().toString(indent).replaceAll("([^\n]*\n)", spaces + "$1"));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }

    private String deTag(String tag) {
        return tag.replaceAll("</?(\\S+)>", "$1");
    }
}
