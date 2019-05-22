package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.SegmentException;

/**
 *  Default segment: parsing format = LinScript.
 *  @author nyngwang
 */
public class DefaultSegment extends Segment {
    public DefaultSegment(String name, int id, String description) {
        super(name, id, description);
    }

    public DefaultSegment() {
        this(null, Integer.MIN_VALUE, null);
    }

    public DefaultSegment(String name, int id) {
        this(name, id, null);
    }

    @Override
    public void parse(Context context) {
        if (context.currentTokenIsEmpty()) // if hasn't fetch
            context.fetchNextToken();
        if (!parseTag(context))
            throw new SegmentException(String.format("Token %s is not a tag", context.getCurrentToken()));
        context.fetchNextToken();
        if (!parseId(context))
            throw new SegmentException(String.format("Token %s is not an ID", context.getCurrentToken()));
        context.fetchNextToken();
        if (parseDescription(context))
            context.fetchNextToken();
        else do {
            if (parseClosedTag(context)) {
                context.fetchNextToken();
                return;
            }
            if (parseKeyValuePair(context)) continue;
            if (parseTag(context)) {
                Element element = new DefaultElement();
                element.parse(context);
                addElement(element);
                continue;
            }
            throw new SegmentException(String.format("In <%s>: token \"%s\" is not allowed here", name, context.getCurrentToken()));
        } while (context.fetchNextToken());
        throw new SegmentException(String.format("Tag <%s> is not closed", name));
    }

    @Override
    public String toString(int indentation, int width) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        resultBuilder.append(String.format("<%s>%s%s\n",
                name,
                id == Integer.MIN_VALUE? "" : " " + String.valueOf(id),
                description == null? "" : " " + description));
        resultBuilder.append(keyValuePairsToString(width).replaceAll("([^\n]*\n)", indent + "$1"));
        getElements().forEach(element ->
                resultBuilder.append(element
                        .toString(indentation, width)
                        .replaceAll("([^\n]*\n)", indent + "$1")));
        resultBuilder.append(String.format("</%s>\n", name));
        return resultBuilder.toString();
    }
}
