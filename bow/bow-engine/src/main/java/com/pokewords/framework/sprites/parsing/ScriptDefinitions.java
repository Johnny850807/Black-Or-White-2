package com.pokewords.framework.sprites.parsing;


/**
 * @author nyngwang
 */
public interface ScriptDefinitions {
    interface LinScript {
        String SEGMENT = "Segment";
        String ELEMENT = "Element";
        interface Samples {
            String SCRIPT_RULES_TEXT =
                    "Segment\n" +
                    "    galleries\n" +
                    "    frame\n" +
                    "        segment-event ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        segment-description \\S+.*?\\S+ String\n" +
                    "        pic ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        duration ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        next ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        layer ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "    test\n" +
                    "        noRegex Integer\n" +
                    "Element\n" +
                    "    sheet\n" +
                    "        padding ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        startPic ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        endPic ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        path \\S+.*?\\S+ String\n" +
                    "        row ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        col ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "    sequence\n" +
                    "        startPic ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        endPic ([1-9][0-9]+)|([0-9]) Integer\n" +
                    "        path \\S+.*?\\S+ String\n" +
                    "        \n" +
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
