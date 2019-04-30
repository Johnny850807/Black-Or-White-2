package com.pokewords.framework.sprites.parsing;

public interface DefaultRulesText {
    String LINSCRIPT_RULES =
            "Segment\n" +
            "    gallery\n" +
            "        startPic ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        endPic ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        path \\S+.(bmp|jpg|png) String\n" +
            "        w ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        h ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        row ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        col ([1-9][0-9]+)|([0-9]) Integer\n" +
            "    frame\n" +
            "        segment-id ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        segment-description \\S+.*?\\S+ String\n" +
            "        pic ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        duration ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        next ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        dvx ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        dvy ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        centerX ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        centerY ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        layer ([1-9][0-9]+)|([0-9]) Integer\n" +
            "Element\n" +
            "    bow\n" +
            "        state ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        injury ([1-9][0-9]+)|([0-9]) Integer\n" +
            "    body\n" +
            "        x ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        y ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        w ([1-9][0-9]+)|([0-9]) Integer\n" +
            "        h ([1-9][0-9]+)|([0-9]) Integer";
}
