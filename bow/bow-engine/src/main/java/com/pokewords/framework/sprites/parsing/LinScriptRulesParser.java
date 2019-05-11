package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.LinScriptRulesParserException;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinScriptRulesParser implements ScriptRulesParser {

    private ScriptRules linScriptRules;
    private String segmentBlock;
    private String elementBlock;
    private boolean hasRegex;

    public LinScriptRulesParser() {
        init();
    }

    @Override
    public ScriptRules parse(String linScriptRulesText) {
        init();
        setupBlocks(linScriptRulesText);
        addToRulesFromBlock(linScriptRules.getValidSegmentNames(), linScriptRules.getValidSegmentKVRules(), segmentBlock);
        addToRulesFromBlock(linScriptRules.getValidElementNames(), linScriptRules.getValidElementKVRules(), elementBlock);
        return linScriptRules;
    }

    private void init() {
        linScriptRules = new LinScriptRules();
        segmentBlock = "";
        elementBlock = "";
        hasRegex = false;
    }

    private void setupBlocks(String linScriptRulesText) {
        Pattern pattern = Pattern.compile("(\\w+)\\n(.*?)(?=\\n\\w|\\Z)");
        Matcher matcher = pattern.matcher(linScriptRulesText);
        while (matcher.find()) {
            String blockName = matcher.group(1);
            String blockContent = matcher.group(2);
            blockRouter(blockName, blockContent);
        }
    }

    private void blockRouter(String blockName, String blockContent) {
        switch (blockName) {
            case ScriptDef.LinScript.SEGMENT: segmentBlock = blockContent; break;
            case ScriptDef.LinScript.ELEMENT: elementBlock = blockContent; break;
            default: throw new LinScriptRulesParserException(
                    "LinScriptRulesParser failed: Unrecognized script rules node name."
            );
        }
    }

    private void addToRulesFromBlock(Set<String> validNames, Map<String, ScriptRules.Pair> validKVRules,
                                            String textBlock) {
        Pattern pattern = Pattern.compile(
                " {4}(\\w+)\n(.*?)(?=(?:\\n {4}\\w)|\\Z)",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(textBlock);
        while (matcher.find()) {
            String tagName = matcher.group(1);
            String kvBlock = matcher.group(2);
            validNames.add(tagName);
            addToKVRulesFromBlock(validKVRules, kvBlock);
        }
    }

    private void addToKVRulesFromBlock(Map<String, ScriptRules.Pair> validKVRules, String kvBlock) {
        Pattern pattern = Pattern.compile(
                " {8}(.+?) (.*?) (.+?) *\\n|\\Z",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(kvBlock);
        while (matcher.find()) {
            String key = matcher.group(1);
            String regex = matcher.group(2);
            String type = matcher.group(3);
            validKVRules.put(key, new ScriptRules.Pair(regex, type));
        }
    }
}
