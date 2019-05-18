package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.LinScriptRulesParserException;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nyngwang
 */
public class LinScriptRulesParser implements ScriptRulesParser {
    private ScriptRules linScriptRules;
    private String segmentBlock;
    private String elementBlock;

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
    }

    private void setupBlocks(String linScriptRulesText) {
        Pattern pattern = Pattern.compile(
                "(\\S+)\\n(.*?)(?:\\Z|\\n(?=\\S))",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(linScriptRulesText);
        while (matcher.find()) {
            String blockName = matcher.group(1);
            String blockContent = matcher.group(2);
            blockRouter(blockName, blockContent);
        }
    }

    private void blockRouter(String blockName, String blockContent) {
        switch (blockName) {
            case ScriptDefinitions.LinScript.SEGMENT: segmentBlock = blockContent; break;
            case ScriptDefinitions.LinScript.ELEMENT: elementBlock = blockContent; break;
            default: throw new LinScriptRulesParserException(
                    String.format("LinScriptRulesParser failed: Unrecognized script rules node name (%s)."
            , blockName));
        }
    }

    private void addToRulesFromBlock(Set<String> validNames, Map<String, ScriptRules.Pair> validKVRules,
                                            String textBlock) {
        Pattern pattern = Pattern.compile(
                " {4}(\\S+)\n(.*?)(?=(?:\\n {4}\\S)|\\Z)",
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
                " {8}(\\S+) (?:(\\S+)|(\\S+) *(\\S+))",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(kvBlock);
        while (matcher.find()) {
            String key = matcher.group(1);
            String regex = matcher.group(3);
            String type = regex == null? matcher.group(2): matcher.group(4);
            validKVRules.put(key, new ScriptRules.Pair(regex, type));
        }
    }

    public static void main(String[] args) {
        ScriptRulesParser parser = new LinScriptRulesParser();
        ScriptRules rules = parser.parse(ScriptDefinitions.LinScript.Samples.SCRIPT_RULES_TEXT);
        System.out.println(rules);

    }
}
