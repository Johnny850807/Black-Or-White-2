package com.pokewords.framework.sprites.parsing;

/**
 *  //TODO: Should be put into ScriptDef
 *  Usage:
 *
 *    直接取得編譯好的rules：ScriptSample.LinScript.RULES
 *    自行編譯rules：LinScript.Parser.parse(ScriptSample.LinScript.RULES_TEXT)
 */
public interface ScriptSample {
    interface LinScript {
        String RULES_TEXT =
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
        com.pokewords.framework.sprites.parsing.LinScript.Rules RULES = com.pokewords.framework.sprites.parsing.LinScript.Rules.Parser.parse(RULES_TEXT);
    }

}
