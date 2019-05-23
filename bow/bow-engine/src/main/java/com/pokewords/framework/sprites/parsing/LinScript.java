package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.SegmentException;

/**
 * LinScript is a special segment which only contains other segments.
 * @author nyngwang
 */
public class LinScript extends Script {
    protected LinScript(String name, int id, String description) {
        super(name, id, description);
    }

    @Override
    public void parse(Context context) {
        while (context.consumeToken()) {
            if (parseTag(context)) {
                Segment segment = new DefaultSegment();
                segment.parse(context);
                addSegment(segment);
                continue;
            }
            throw new SegmentException(String.format(
                    "LinScript format error: \"%s\" is not allowed here", context.peekToken()));
        }
    }

    @Override
    public String toString(int indentation, int width) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = new String(new char[indentation]).replace("\0", " ");
        getSegments().sort((o1, o2) -> {
            String leftName = o1.getName();
            String rightName = o2.getName();
            int leftId = o1.getId();
            int rightId = o2.getId();
            return leftName.compareTo(rightName) == 0? Integer.compare(leftId, rightId)
                    : leftName.compareTo(rightName);
        });
        getSegments().forEach(segment ->
                resultBuilder.append(segment.toString(indentation, width)
                        .replaceAll("([^\n]*\n)", indent + "$1")));
        return resultBuilder.toString();
    }

    public static void main(String[] args) {
        LinScript script = new LinScript()
                .addSegment(new DefaultSegment("frame", 1, "punch")
                        .put("next", 2).put("duration", 10)
                        .addElement(new DefaultElement("bow").put("x", 1).put("y", 2))
                        .addElement(new DefaultElement("bow2").put("a", 1).put("b", 2)))
                .addSegment(new DefaultSegment("galleries", 1)
                        .put("length", 2)
                        .addElement(new DefaultElement("gallery")
                                .put("path", "path/to/gallery1"))
                        .addElement(new DefaultElement("gallery")
                                .put("path", "path/to/gallery2")));
        System.out.println(script);
    }
}
