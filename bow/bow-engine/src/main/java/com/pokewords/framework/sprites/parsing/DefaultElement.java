package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ElementException;

/**
 * Default element: parsing format = LinScript.
 * @author nyngwang
 */
public class DefaultElement extends Element {

    public DefaultElement(String name) {
        super(name, Integer.MIN_VALUE, null);
    }

    public DefaultElement() {
        this(null);
    }

    @Override
    public void parse(Context context) {
        if (context.currentTokenIsEmpty()) // if hasn't fetch
            context.consumeToken();
        if (!parseTag(context))
            throw new ElementException(String.format("Token %s is not a tag", context.peekToken()));

        while (context.consumeToken()) {
            if (parseClosedTag(context)) {
                context.consumeToken();
                return;
            }
            if (!parseKeyValuePair(context))
                throw new ElementException(String.format("In <%s>: token \"%s\" is not allowed here", name, context.peekToken()));
        }
        throw new ElementException(String.format("Tag <%s> is not closed", name));
    }

    @Override
    public String toString(int indentation, int width) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>\n", getName()));
        resultBuilder.append(keyValuePairsToString(width).replaceAll("([^\n]*\n)", indent + "$1"));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }
}
