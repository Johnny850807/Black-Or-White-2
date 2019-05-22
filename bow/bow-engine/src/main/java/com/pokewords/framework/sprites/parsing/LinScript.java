package com.pokewords.framework.sprites.parsing;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nyngwang
 */
public class LinScript extends Segment {


//    public String toString(int indentation) {
//        StringBuilder resultBuilder = new StringBuilder();
//
//        segments.sort((o1, o2) -> {
//            String leftName = o1.getName();
//            String rightName = o2.getName();
//            int leftId = o1.getId();
//            int rightId = o2.getId();
//            return leftName.compareTo(rightName) == 0? Integer.compare(leftId, rightId)
//                    : leftName.compareTo(rightName);
//        });
//        for (Segment segment : segments)
//            resultBuilder.append(segment.toString(indentation));
//        return resultBuilder.toString();
//    }


    @Override
    public void parse(Context context) {

    }

    @Override
    public String toString(int indentation, int width) {
        return null;
    }

    public static void main(String[] args) {
        Script script = new LinScript()
                .addSegment(new LinScriptSegment("frame", 1, "punch")
                        .put("next", 2).put("duration", 10)
                        .addElement(new LinScriptElement("bow")
                                .put("x", 1)
                                .put("y", 2))
                        .addElement(new LinScriptElement("bow2")
                                .put("a", 1)
                                .put("b", 2)))
                .addSegment(new LinScriptSegment("galleries", 1)
                        .put("length", 2)
                        .addElement(new LinScriptElement("gallery")
                                .put("path", "path/to/gallery1"))
                        .addElement(new LinScriptElement("gallery")
                                .put("path", "path/to/gallery2")));
        System.out.println(script);
    }
}
