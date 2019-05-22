package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.SegmentException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * LinScript is a special segment which only contains other segments.
 * @author nyngwang
 */
public class LinScript extends Segment {
    private LinScript(String name, int id, String description) {
        super(name, id, description);
    }

    public LinScript() {
        this(null, Integer.MIN_VALUE, null);
    }

    // Segments

    @Override
    public LinScript addSegment(Segment segment) {
        super.addSegment(segment);
        return this;
    }

    @Override
    public List<Segment> getSegments() {
        return super.getSegments();
    }

    @Override
    public boolean containsSegment(String name) {
        return super.containsSegment(name);
    }

    @Override
    public boolean containsSegmentId(int id) {
        return super.containsSegmentId(id);
    }

    @Override
    public boolean containsSegmentDescription(String description) {
        return super.containsSegmentDescription(description);
    }

    @Override
    public List<Segment> getSegments(String name) {
        return super.getSegments(name);
    }

    @Override
    public List<Segment> getSegmentsById(int id) {
        return super.getSegmentsById(id);
    }

    @Override
    public List<Segment> getSegmentsByDescription(String description) {
        return super.getSegmentsByDescription(description);
    }

    //

    @Override
    public void parse(Context context) {
        while (context.fetchNextToken()) {
            if (parseTag(context)) {
                Segment segment = new DefaultSegment();
                segment.parse(context);
                addSegment(segment);
                continue;
            }
            throw new SegmentException(String.format(
                    "LinScript format error: \"%s\" is not allowed here", context.getCurrentToken()));
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
                        .addElement(new DefaultElement("bow")
                                .put("x", 1)
                                .put("y", 2))
                        .addElement(new DefaultElement("bow2")
                                .put("a", 1)
                                .put("b", 2)))
                .addSegment(new DefaultSegment("galleries", 1)
                        .put("length", 2)
                        .addElement(new DefaultElement("gallery")
                                .put("path", "path/to/gallery1"))
                        .addElement(new DefaultElement("gallery")
                                .put("path", "path/to/gallery2")));
        System.out.println(script);
    }
}
