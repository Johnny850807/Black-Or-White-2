package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.LinScriptParserException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nyngwang
 */
public class LinScriptParser implements ScriptParser {

    private LinScript linScript;
    private LinScriptRules linScriptRules;
    private Segment segment;
    private Element element;

    public LinScriptParser() {
    }

    @Override
    public Script parse(String scriptText, ScriptRules scriptRules) {
        init(scriptRules);
        setupScript(scriptText);
        return linScript;
    }

    private void init(ScriptRules scriptRules) {
        linScript = new LinScript();
        linScriptRules = (LinScriptRules) scriptRules;
        segment = null;
        element = null;
    }

    private void setupScript(String scriptText) {
        Pattern pattern = Pattern.compile(
                "<(\\w+)> +(\\S+)(?: +(\\S+))? *\\n(.*?)\\n</\\1>",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(scriptText);
        while (matcher.find()) {
            String segmentName = matcher.group(1);
            String segmentId = matcher.group(2);
            String segmentDescription = matcher.group(3);
            String segmentText = matcher.group(4);
            setupSegment(segmentName, segmentId, segmentDescription, segmentText);
        }
    }

    private void setupSegment(String segmentName, String segmentId,
                              String segmentDescription, String segmentText) {
        // 創建前validate
        validateNameOfWhom(segmentName, ScriptDefinitions.LinScript.SEGMENT);
        segment = new LinScriptSegment(segmentName, Integer.parseInt(segmentId), segmentDescription);
        setupSegmentKVPairsAndElementsIfExist(segmentText);
        // 與parent的相互設置放在setup中
        linScript.addSegment(segment);
        segment.setParentScript(linScript);
    }

    private void validateNameOfWhom(String name, String whom) {
        switch (whom) {
            case ScriptDefinitions.LinScript.SEGMENT:
                if ( !linScriptRules.getValidSegmentNames().contains(name) )
                    throw new LinScriptParserException("Segment name is unrecognizable!");
            case ScriptDefinitions.LinScript.ELEMENT:
                if ( !linScriptRules.getValidElementNames().contains(name) )
                    throw new LinScriptParserException("Element name is unrecognizable!");
            default:
                throw new LinScriptParserException("Internal error: misuse of validateNameOfWhom()");
        }
    }

    private void setupSegmentKVPairsAndElementsIfExist(String segmentText) {
        Pattern pattern = Pattern.compile(
                "(?:\\s*(<(\\w+)>.*?</\\2>)\\s*)|([^<]+)",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(segmentText);
        // KVPairs or single Element
        while (matcher.find()) {
            String kvPairsText = matcher.group(3);
            String elementText = matcher.group(1);
            router(kvPairsText, elementText);
        }
    }

    private void router(String kvPairsText, String elementText) {
        if (elementText == null) {
            setupKVPairsOfWhom(kvPairsText, ScriptDefinitions.LinScript.SEGMENT);
        } else {
            setupElement(elementText);
        }
    }

    private void setupKVPairsOfWhom(String kvPairsText, String whom) {
        Pattern pattern = Pattern.compile(
                "(\\S+)\\s*:\\s*(\\S+)",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(kvPairsText);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            validateKVPairOfWhom(key, value, whom);
            putKVPairOfWhom(key, value, whom);
        }
    }

    private void validateKVPairOfWhom(String key, String value, String whom) {
        switch (whom) {
            case ScriptDefinitions.LinScript.SEGMENT:
                ScriptRules.Pair pair = linScriptRules.getValidSegmentKVRules().get(key);
                if (pair == null) throw new LinScriptParserException
                    ("LinScriptParser: invalid key of script segment");
                if (pair.regex == null) return;
                if (Pattern.matches(pair.regex, value)) return;
                throw new LinScriptParserException
                    ("LinScriptParser: value doesn't match regex in the script.");
            case ScriptDefinitions.LinScript.ELEMENT:
                pair = linScriptRules.getValidElementKVRules().get(key);
                if (pair == null) throw new LinScriptParserException
                    ("LinScriptParser: invalid key of script element");
                if (pair.regex == null) return;
                if (Pattern.matches(pair.regex, value)) return;
                throw new LinScriptParserException
                    ("LinScriptParser: value doesn't match regex in the script.");
            default:
                throw new LinScriptParserException("Internal error: misuse of validateKVPairOfWhom()");
        }
    }

    private void putKVPairOfWhom(String key, String value, String whom) {
        switch (whom) {
            case ScriptDefinitions.LinScript.SEGMENT:
                switch (linScriptRules.getValidSegmentKVRules().get(key).type) {
                    case "Integer": segment.putKVPair(key, Integer.parseInt(value)); break;
                    case "String": segment.putKVPair(key, value); break;
                }
                break;
            case ScriptDefinitions.LinScript.ELEMENT:
                switch (linScriptRules.getValidElementKVRules().get(key).type) {
                    case "Integer": element.putKVPair(key, Integer.parseInt(value)); break;
                    case "String": element.putKVPair(key, value); break;
                }
                break;
            default:
                throw new LinScriptParserException("Internal error: misuse of putKVPairOfWhom()");
        }
    }

    // 注意是一個element而不是多個
    private void setupElement(String elementText) {
        Pattern pattern = Pattern.compile(
                "<(\\S+)>(.*?)</\\1>",
                Pattern.DOTALL | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(elementText);
        if (matcher.find()) {
            String elementName = matcher.group(1);
            String elementKVPairsText = matcher.group(2);
            validateNameOfWhom(elementName, ScriptDefinitions.LinScript.ELEMENT);
            element = new LinScriptElement(elementName);
            setupKVPairsOfWhom(elementKVPairsText, ScriptDefinitions.LinScript.ELEMENT);
            // 相互設置
            segment.addElement(element);
            element.setParentSegment(segment);
        }
    }
}
