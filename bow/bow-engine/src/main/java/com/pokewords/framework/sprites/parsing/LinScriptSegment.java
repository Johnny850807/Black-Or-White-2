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

    public LinScriptSegment() {
        super(null, Integer.MIN_VALUE, null);
    }

    @Override
    public void parse(Context context) {
        parseNameIdDescription(context);
        do {

        } while (fetchAndCheck(context));

    }

    @SuppressWarnings("Duplicates")
    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        resultBuilder.append(String.format("<%s> %d %s\n", getName(), getId(), getDescription() == null? "" : getDescription()));
        keyValuePairs.stringMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        keyValuePairs.integerMap.forEach((key, value) -> resultBuilder.append(String.format(indent + "%s: %s\n", key, value)));
        elements.forEach(element ->
                resultBuilder.append(element
                        .toString(indentation)
                        .replaceAll("(.*?\n)", indent + "$1")));
        resultBuilder.append(String.format("</%s>\n", getName()));
        return resultBuilder.toString();
    }

    @Override
    public String toString() {
        return toString(4);
    }

    public static void main(String[] args) {
        Segment segment = new LinScriptSegment("frame", 1, "punch")
                .addElement(new LinScriptElement("bow"))
                .addElement(new LinScriptElement("cow"));
        System.out.println(segment);

    }
}
