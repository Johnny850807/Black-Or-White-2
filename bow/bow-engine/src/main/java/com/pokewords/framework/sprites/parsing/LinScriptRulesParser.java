package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.LinScriptRulesParserException;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nyngwang
 * @deprecated Not mandatory.
 */
public class LinScriptRulesParser implements ScriptRulesParser {
    private ScriptRules linScriptRules;
    private Map<String, ScriptRules.Pair> currentMap;
    private Set<String> currentSet;

    public LinScriptRulesParser() {
        init();
    }

    @Override
    public ScriptRules parse(String linScriptRulesText) {
        init();
        parseLines(linScriptRulesText);
        return linScriptRules;
    }

    private void init() {
        linScriptRules = new LinScriptRules();
        currentMap = null;
        currentSet = null;
    }

    private void parseLines(String linScriptRulesText) {
        Pattern pattern = Pattern.compile(
                "(.+?)(?:\\n|\\Z)",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(linScriptRulesText);
        while (matcher.find()) {
            router(matcher.group(1));
        }
    }

    private void router(String line) {
        if (Pattern.matches("\\s+", line))
            return;
        if (Pattern.matches("\\S+", line)) {
            changeMap(line);
            return;
        }
        if (Pattern.matches(" {4}\\S+", line)) {
            parseName(line);
            return;
        }
        parseKeyValuePair(line);
    }

    private void changeMap(String blockType) {
        switch (blockType) {
            case ScriptDefinitions.LinScript.SEGMENT:
                currentSet = linScriptRules.getValidSegmentNames();
                currentMap = linScriptRules.getValidSegmentKVRules();
                break;
            case ScriptDefinitions.LinScript.ELEMENT:
                currentSet = linScriptRules.getValidElementNames();
                currentMap = linScriptRules.getValidElementKVRules();
                break;
            default:
                throw new LinScriptRulesParserException(String.format("Unrecognized block type: %s", blockType));
        }
    }

    private void parseName(String line) {
        String name = line.trim();
        currentSet.add(name);
    }

    private void parseKeyValuePair(String line) {
        Pattern pattern = Pattern.compile(" {8}(\\S+) +(?:(\\S+) +(\\S+)|(\\S+))");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            throw new LinScriptRulesParserException(String.format("Unrecognized line: %s", line));
        } else {
            String key = matcher.group(1);
            String regex = matcher.group(2);
            String type = regex != null? matcher.group(3): matcher.group(4);
            currentMap.put(key, new ScriptRules.Pair(regex, type));
        }
    }

    public static void main(String[] args) {
        ScriptRulesParser parser = new LinScriptRulesParser();
        ScriptRules rules = parser.parse(ScriptDefinitions.LinScript.Samples.SCRIPT_RULES_TEXT);
        System.out.println(rules);
    }
}
