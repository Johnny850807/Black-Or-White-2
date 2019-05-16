package com.pokewords.framework.sprites.parsing;


import com.pokewords.framework.engine.utils.FileUtility;

import java.io.IOException;

/**
 * @author nyngwang
 */
public interface ScriptDefinitions {
    // Demo
    static void main(String[] args) {
        try {
            ScriptParser linScriptParser = new LinScriptParser();
            ScriptRulesParser linScriptRulesParser = new LinScriptRulesParser();
            Script linScript = linScriptParser.parse(
                    FileUtility.read("path/to/linscript.txt"),
                    linScriptRulesParser.parse("path/to/linscript_rules.txt")
            );

            linScript.addSegment(
                    new LinScriptSegment("frame", 1, "punch")
                            .addElement(new LinScriptElement("bow"))
                            .addElement(new LinScriptElement("cow")))
                    .addSegment(
                            new LinScriptSegment("frame", 2, "punch")
                                    .addElement(new LinScriptElement("dow"))
                                    .addElement(new LinScriptElement("fow"))
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element element = new LinScriptElement("bow");
        element.put("hey", 123);
        element.put("hey2", 456);
        element.put("yo", "yo");
        element.put("yoho", "yoho");
        System.out.println(element);
    }

    interface LinScript {
        String NAME = "LinScript";
        String SEGMENT = "Segment";
        String ELEMENT = "Element";
        interface Segment {
            String NAME = "segment-name";
            String ID = "segment-id";
            String DESCRIPTION = "segment-description";
        }
        interface Element {
            String NAME = "element-name";
        }
        interface Samples {
            String SCRIPT_RULES_TEXT =
                    "Segment\n" +
                            "    gallery\n" +
                            "        segment-id ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        segment-description \\S+.*?\\S+ String\n" +
                            "        padding ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        startPic ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        endPic ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        path \\S+.*?\\S+ String\n" +
                            "        row ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        col ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "    frame\n" +
                            "        segment-id ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        segment-description \\S+.*?\\S+ String\n" +
                            "        pic ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        duration ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        next ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        layer ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "Element\n" +
                            "    properties\n" +
                            "        x ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        y ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        w ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        h ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        centerX ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        centerY ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "    effect\n" +
                            "        moveX ([1-9][0-9]+)|([0-9]) Integer\n" +
                            "        moveY ([1-9][0-9]+)|([0-9]) Integer";
            ScriptRules SCRIPT_RULES = new LinScriptRulesParser().parse(SCRIPT_RULES_TEXT);
        }
    }
}
