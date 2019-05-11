package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParserException;
import com.pokewords.framework.engine.exceptions.LinScriptRulesParserException;
import com.pokewords.framework.engine.utils.FileUtility;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *   ScriptRules: LinScriptRules
 *   ScriptParser: LinScriptParser
 *
 *   LinScriptParser.parse() uses LinScriptRules
 *
 *   Script doesn't have to store Rules (?)
 *
 * @author nyngwang
 */
public class LinScript implements Script {

    // Demo
    public static void main(String[] args) {
        try {
            ScriptParser linScriptParser = ScriptParser.getParser(ScriptDef.LinScript.NAME);
            ScriptRulesParser linScriptRulesParser = ScriptRulesParser.getParser(ScriptDef.LinScript.NAME);
            Script linScript = linScriptParser.parse(
                    FileUtility.read("path/to/linscript.txt"),
                    linScriptRulesParser.parse("path/to/linscript_rules.txt")
            );

            linScript.addSegment(
                    new Segment("frame", 1, "punch")
                            .addElement(new Element("bow"))
                            .addElement(new Element("cow")))
                    .addSegment(
                            new Segment("frame", 2, "punch")
                                    .addElement(new Element("dow"))
                                    .addElement(new Element("fow"))
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Segment> segments;
    private Rules rules;

    private LinScript(Rules rules) {
        segments = new ArrayList<>();
        this.rules = rules;
    }

    // Segments management

    public LinScript addSegment(Segment segment) {
        segments.add(segment);
        segment.setParentScript(this);
        return this;
    }

    public List<Segment> getSegmentsByName(String segmentName) {
        return segments.stream()
                .filter(segment -> segment.getStringByKey(Segment.Def.NAME).equals(segmentName))
                .collect(Collectors.toList());
    }

    public Optional<Segment> getSegmentById(String segmentId) {
        for (Segment segment : segments) {
            if (segment.getStringByKey(Segment.Def.ID).orElse("").equals(segmentId)) {
                return Optional.of(segment);
            }
        }
        return Optional.empty();
    }

    public List<Segment> getSegmentsByDescription(String segmentDescription) {
        return segments.stream()
                .filter(segment -> segment.getStringByKey(Segment.Def.DESCRIPTION).equals(segmentDescription))
                .collect(Collectors.toList());
    }

    // Not recommended

    public List<Segment> getSegments() {
        return segments;
    }

    public Rules getRules() {
        return rules;
    }


    @Override
    public String toString() {
        return toString(4);
    }

    public String toString(int indentation) {
        // TODO: Print by order: gallery -> frame, with id ascending
        StringBuilder resultBuilder = new StringBuilder();

        for (String segmentName : rules.validSegmentNames) {
            List<Segment> segments = getSegmentsByName(segmentName);
            segments.sort((o1, o2) -> {
                int left = o1.getIntByKey(Segment.Def.ID).get();
                int right = o2.getIntByKey(Segment.Def.ID).get();
                return Integer.compare(left, right);
            });
            for (Segment segment : segments)
                resultBuilder.append(segment.toString(indentation));
        }
        return resultBuilder.toString();
    }

    /**
     *  The LinScript.Rules
     */
    public static class Rules {

        /**
         *  The LinScript.Rules.Parser:
         *  Usage: LinScript.Rules.Parser.parse(scriptRulesText) -> LinScript.Rules
         */
        public static class Parser {
            private static Rules rules;
            private static String segmentBlock;
            private static String elementBlock;

            private Parser() {
            }

            public static Rules parse(String scriptRulesText) {
                init();
                setupBlocks(scriptRulesText);
                addRulesFromBlock(rules.validSegmentNames, rules.validSegmentKVRules, segmentBlock);
                addRulesFromBlock(rules.validElementNames, rules.validElementKVRules, elementBlock);
                return rules;
            }

            private static void init() {
                rules = new Rules();
                segmentBlock = "";
                elementBlock = "";
            }

            private static void setupBlocks(String scriptRulesText) {
                Pattern pattern = Pattern.compile("(\\w+)\\n(.*?)(?=\\n\\w|\\Z)");
                Matcher matcher = pattern.matcher(scriptRulesText);

                while (matcher.find()) {
                    String blockName = matcher.group(1);
                    String blockContent = matcher.group(2);
                    blockRouter(blockName, blockContent);
                }
            }

            private static void blockRouter(String blockName, String blockContent) {
                switch (blockName) {
                    case LinScript.Def.SEGMENT: segmentBlock = blockContent; break;
                    case LinScript.Def.ELEMENT: elementBlock = blockContent; break;
                    default: throw new LinScriptRulesParserException(
                        "LinScript.Rules Parsing Error: Unrecognized LinScript Node Name."
                    );
                }
            }

            private static void addRulesFromBlock(
                    Set<String> validNames, Map<String, Pair> validKVRules, String block) {
                Pattern pattern = Pattern.compile(
                        " {4}(\\w+)\n(.*?)(?=(?:\\n {4}\\w)|\\Z)",
                        Pattern.DOTALL | Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(block);

                while (matcher.find()) {
                    String validName = matcher.group(1);
                    String kvBlock = matcher.group(2);
                    validNames.add(validName);
                    addKVRulesFromBlock(validKVRules, kvBlock);
                }
            }

            private static void addKVRulesFromBlock(Map<String, Pair> validKVRules, String kvBlock) {
                Pattern pattern = Pattern.compile(
                        " {8}(.+?) (.+?) (.+?) *\\n|\\Z",
                        Pattern.DOTALL | Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(kvBlock);

                while (matcher.find()) {
                    String key = matcher.group(1);
                    String regex = matcher.group(2);
                    String type = matcher.group(3);
                    validKVRules.put(key, new Pair(regex, type));
                }
            }
        }
    }

    /**
     *  The LinScript.Parser
     *
     *  只管建立 LinScript，不用擔心 FSM component
     *
     */
    public static class Parser {
        private static LinScript script;
        private static Rules rules;
        private static Segment segment;
        private static Element element;

        private Parser() { }

        public static LinScript parse(String scriptText, Rules rules) {
            init(rules);
            setupScript(scriptText);
            return script;
        }

        private static void init(Rules rules) {
            script = new LinScript(rules);
            Parser.rules = rules;
        }

        private static void setupScript(String scriptText) {
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

        private static void setupSegment(
                String segmentName, String segmentId, String segmentDescription, String segmentText) {
            // 創建前validate
            validateNameOfWhom(segmentName, Def.SEGMENT);
            segment = new Segment(segmentName, Integer.parseInt(segmentId), segmentDescription);
            setupSegmentKVPairsAndElementsIfExist(segmentText);
            // 與parent的相互設置放在setup中
            script.addSegment(segment);
            segment.setParentScript(script);
        }

        private static void validateNameOfWhom(String name, String whom) {
            switch (whom) {
                case Def.SEGMENT:
                    if ( !rules.validSegmentNames.contains(name) )
                        throw new ScriptParserException("Segment name is unrecognizable!");
                case Def.ELEMENT:
                    if ( !rules.validElementNames.contains(name) )
                        throw new ScriptParserException("Element name is unrecognizable!");
                default:
                    throw new ScriptParserException(
                            "Invalid parameter 'whom' of validateNameOfWhom(String name, String whom)");
            }
        }

        private static void setupSegmentKVPairsAndElementsIfExist(String segmentText) {
            Pattern pattern = Pattern.compile(
                    "(?:\\s*(<(\\w+)>.+?</\\2>)\\s*)|([^<]+)",
                    Pattern.DOTALL | Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(segmentText);

            // KVPairs or single Element
            while (matcher.find()) {
                String kvPairsText = matcher.group(3);
                String elementText = matcher.group(1);
                router(kvPairsText, elementText);
            }
        }

        private static void router(String kvPairsText, String elementText) {
            if (elementText == null) {
                setupKVPairsOfWhom(kvPairsText, Def.SEGMENT);
            } else {
                setupElement(elementText);
            }
        }

        private static void setupKVPairsOfWhom(String kvPairsText, String whom) {
            Pattern pattern = Pattern.compile(
                    "(\\S+)\\s*:\\s*(\\S+)",
                    Pattern.DOTALL | Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(kvPairsText);

            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                validateKeyOfKVPairOfWhom(key, whom);
                validateValueOfKVPairOfWhom(key, value, whom);
                putKVPairOfWhom(key, value, whom);
            }
        }

        private static void validateKeyOfKVPairOfWhom(String key, String whom) {
            switch (whom) {
                case Def.SEGMENT:
                    if ( !rules.validSegmentKVRules.containsKey(key) ) {
                        throw new ScriptParserException(
                            "validateKeyOfKVPairOfWhom(String key, String whom): key is invalid");
                    }
                    break;
                case Def.ELEMENT:
                    if ( !rules.validElementKVRules.containsKey(key) ) {
                        throw new ScriptParserException(
                            "validateKeyOfKVPairOfWhom(String key, String whom): key is invalid");
                    }
                    break;
                default: throw new ScriptParserException(
                    "validateKeyOfKVPairOfWhom(String key, String whom): whom is invalid");
            }
        }

        private static void validateValueOfKVPairOfWhom(String key, String value, String whom) {
            switch (whom) {
                case Def.SEGMENT: if (!Pattern.matches(rules.validSegmentKVRules.get(key).regex, value)) {
                    throw new ScriptParserException(
                        "validateValueOfKVPairOfWhom(String key, String value, String whom): value is invalid");
                } break;
                case Def.ELEMENT: if ( !Pattern.matches(rules.validElementKVRules.get(key).regex, value) ) {
                    throw new ScriptParserException(
                        "validateValueOfKVPairOfWhom(String key, String value, String whom): value is invalid");
                } break;
                default: throw new ScriptParserException(
                    "validateValueOfKVPairOfWhom(String key, String value, String whom): whom is invalid");
            }
        }

        private static void putKVPairOfWhom(String key, String value, String whom) {
            switch (whom) {
                case Def.SEGMENT: switch (rules.validSegmentKVRules.get(key).type) {
                    case "Integer": segment.putKVPair(key, Integer.parseInt(value)); break;
                    case "String": segment.putKVPair(key, value); break;
                } break;
                case Def.ELEMENT: switch (rules.validElementKVRules.get(key).type) {
                    case "Integer": element.putKVPair(key, Integer.parseInt(value)); break;
                    case "String": element.putKVPair(key, value); break;
                } break;
                default: throw new ScriptParserException(
                    "putKVPairOfWhom(String key, String value, String whom): whom is invalid");
            }
        }

        // 注意是一個element而不是多個
        private static void setupElement(String elementText) {
            Pattern pattern = Pattern.compile(
                    "<(\\S+)>(.*?)</\\1>",
                    Pattern.DOTALL | Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(elementText);

            if (matcher.find()) {
                String elementName = matcher.group(1);
                String elementKVPairsText = matcher.group(2);
                validateNameOfWhom(elementName, Def.ELEMENT);
                element = new Element(elementName);
                setupKVPairsOfWhom(elementKVPairsText, Def.ELEMENT);
                // 相互設置
                segment.addElement(element);
                element.setParentSegment(segment);
            }
        }

    }
}
