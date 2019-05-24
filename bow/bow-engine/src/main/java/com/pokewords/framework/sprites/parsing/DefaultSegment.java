package com.pokewords.framework.sprites.parsing;

/**
 * Default segment: parsing format = LinScript.
 * @author nyngwang
 */
public class DefaultSegment extends Segment {
    public DefaultSegment() {
        this(null, Integer.MIN_VALUE, null);
    }

    public DefaultSegment(String name, int id) {
        this(name, id, null);
    }

    public DefaultSegment(String name, int id, String description) {
        super(name, id, description);
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
