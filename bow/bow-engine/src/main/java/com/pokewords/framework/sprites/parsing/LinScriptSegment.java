package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.commons.KeyValuePairs;
import com.pokewords.framework.engine.exceptions.SegmentException;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  Each segment has a reference to its parent Script, possibly some Elements, and its key-value pairs.
 *  @author nyngwang
 */
public class LinScriptSegment extends Segment {
    public LinScriptSegment(String name, int id, String description) {
        super(name, id, description);
    }

    public LinScriptSegment() {
        this(null, Integer.MIN_VALUE, null);
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
                Element element = new LinScriptElement();
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
        resultBuilder.append(String.format(
                indent + "<%s>%s%s\n",
                name, id == Integer.MIN_VALUE? "" : " " + String.valueOf(id), description == null? "" : description));
        keyValuePairsToString(indentation * 2, width);
        getElements().forEach(element -> resultBuilder.append(element.toString(indentation * 2, width)));
        resultBuilder.append(String.format(indent + "</%s>", name));
        return resultBuilder.toString();
    }

//    @Override
//    public String toString(int indentation) {
//        StringBuilder resultBuilder = new StringBuilder();
//        String indent = new String(new char[indentation]).replace("\0", " ");
//        resultBuilder.append(String.format("<%s> %d %s\n", getName(), getId(), getDescription() == null? "" : getDescription()));
//        keyValuePairs.stringMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
//        keyValuePairs.integerMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
//        elements.forEach(element ->
//                resultBuilder.append(element
//                        .toString(indentation)
//                        .replaceAll("(.*?\n)", indent + "$1")));
//        resultBuilder.append(String.format("</%s>\n", getName()));
//        return resultBuilder.toString();
//    }
}
