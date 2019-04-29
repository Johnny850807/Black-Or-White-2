package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.exceptions.ScriptParsingErrorException;
import com.pokewords.framework.engine.exceptions.ScriptRulesParserException;
import com.pokewords.framework.engine.exceptions.SegmentNameUnrecognizableException;
import com.pokewords.framework.ioc.IocFactory;
import com.pokewords.framework.sprites.components.Frame;
import com.pokewords.framework.sprites.components.FrameFactory;
import com.pokewords.framework.sprites.components.FrameStateMachineComponent;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *   Script 改為 Class 而非 Interface：
 *     從介面 Script 到 LinScript 的差別應該只有合法的 key-value pairs
 *     Client 只要為具體的 Script 定義自己的 segment-name, element-name
 *
 *   Q: 是否在 Script.Parser 時一同比對 Script.Rules？
 *   Q: 是否需要將 Segment及 Element也嵌入進來，得到
 *      Script.Segment
 *      Script.Element
 *      增加凝聚力？
 *   
 * @author nyngwang
 */
public class Script {
    public interface Def {
        String SEGMENT = "Segment";
        String ELEMENT = "Element";
    }
    private ArrayList<Segment> segments;
    private Rules rules;

    public Script(Rules rules) {
        segments = new ArrayList<>();
        this.rules = rules;
    }

    // Segments management

    public Script addSegment(Segment segment) {
        segments.add(segment);
        return this;
    }

    public List<Segment> getSegmentsByName(String segmentName) {
        return segments.stream()
                .filter(segment -> segment.getStringByKey(Segment.Def.NAME).equals(segmentName))
                .collect(Collectors.toList());
    }

    public Optional<Segment> getSegmentById(String segmentId) {
        for (Segment segment : segments) {
            if (segment.getStringByKey(Segment.Def.ID).equals(segmentId)) {
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

        // TODO: Print by order: gallery -> frame, with id ascending

        StringBuilder result = new StringBuilder();
        for (Segment segment : segments) {
            result.append(segment);
        }
        return result.toString();
    }

    /**
     *  The Script.Rules
     */
    public static class Rules {
        private List<String> validSegmentNames;
        private Map<String, String> validSegmentKVRules;
        private List<String> validElementNames;
        private Map<String, String> validElementKVRules;

        public Rules() {
            validSegmentNames = new ArrayList<>();
            validSegmentKVRules = new HashMap<>();
            validElementNames = new ArrayList<>();
            validElementKVRules = new HashMap<>();
        }

        public List<String> getValidSegmentNames() {
            return validSegmentNames;
        }

        public Map<String, String> getValidSegmentKVRules() {
            return validSegmentKVRules;
        }

        public List<String> getValidElementNames() {
            return validElementNames;
        }

        public Map<String, String> getValidElementKVRules() {
            return validElementKVRules;
        }

        /**
         *  The Script.Rules.Parser:
         *  Usage: Script.Rules.Parser.parse(scriptRulesText) -> Script.Rules
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
                    case Def.SEGMENT: segmentBlock = blockContent; break;
                    case Def.ELEMENT: elementBlock = blockContent; break;
                    default: throw new ScriptRulesParserException(
                        "Script.Rules Parsing Error: Unrecognized Script Node Name."
                    );
                }
            }

            private static void addRulesFromBlock(
                    List<String> validNames, Map<String, String> validKVRules, String block) {

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

            private static void addKVRulesFromBlock(Map<String, String> validKVRules, String kvBlock) {

                Pattern pattern = Pattern.compile(
                        " {8}(.+?) (.*?)(?=\\n|\\Z)",
                        Pattern.DOTALL | Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(kvBlock);

                while (matcher.find()) {
                    String key = matcher.group(1);
                    String value = matcher.group(2);

                    validKVRules.put(key, value);
                }
            }
        }
    }

    /**
     *  The Script.Parser
     */
    public static class Parser {

        private FrameFactory frameFactory;
        private FrameStateMachineComponent fsmc;
        private FrameSegment frameSegment;
        private GallerySegment gallerySegment;
        private Script script;
        private OnParsingFrameListener listener;

        /**
         * Constructor.
         * @param iocFactory abstract factory for dependency.
         */
        public ScriptTextParser(IocFactory iocFactory, Rules rules) {
            this.frameFactory = iocFactory.frameFactory();
            fsmc = null;
            frameSegment = null;
            gallerySegment = null;
            script = null;
            listener = null;
        }

        private void init() {
            fsmc = null;
            frameSegment = null;
            gallerySegment = null;
            script = null;
            listener = null;
        }

        @Override
        public FrameStateMachineComponent parse(Script script, OnParsingFrameListener listener) {

            // Prepare for fsm construction.
            fsmc = new FrameStateMachineComponent();
            this.script = script;
            this.listener = listener;

            // Start parsing.
            String scriptText = script.getText();
            Pattern globalTagPattern = Pattern.compile(
                    "<(\\w+)>(.*?)</\\1>",
                    Pattern.DOTALL | Pattern.MULTILINE);
            Matcher globalTagPatternMatcher = globalTagPattern.matcher(scriptText);

            while (globalTagPatternMatcher.find()) {

                String tagName = globalTagPatternMatcher.group(1);
                String tagContent = globalTagPatternMatcher.group(2);
                processTag(tagName, tagContent);
            }

            init();

            return fsmc;
        }


        /**
         * Process the tag.
         * @param tagName name of the tag to be parsed.
         * @param tagContent content of the tag to be parsed.
         */
        private void processTag(String tagName, String tagContent) {
            switch (tagName) {
                case "gallery": processGalleryTag(tagContent); return;
                case "frame": processFrameTag(tagContent); return;
                default: throw new SegmentNameUnrecognizableException("Segment name is unrecognizable!");
            }
        }


        /**
         * Process the gallery tag.
         * @param tagContent content of the tag to be parsed.
         */
        private void processGalleryTag(String tagContent) {

            // Parse key-value pairs in the gallery tag.
            Pattern pattern1 = Pattern.compile("(\\w+)\\s*:\\s*(\\S+)");
            Pattern pattern2 = Pattern.compile("(\\w+)\\s*:\\s*([+-]?\\d+)");

            Matcher matcher1 = pattern1.matcher(tagContent);
            while (matcher1.find()) {
                String key = matcher1.group(1);
                String value = matcher1.group(2);
                gallerySegment.addPair(key, value);
            }

            Matcher matcher2 = pattern2.matcher(tagContent);
            while (matcher2.find()) {
                String key = matcher2.group(1);
                int value = Integer.parseInt(matcher2.group(2));
                gallerySegment.addPair(key, value);
            }

            script.addGallerySegment(gallerySegment);
        }

        private void processElementTag(String tagName, String tagContent) {

            Element element = new Element();
            Pattern pattern1 = Pattern.compile("(\\w+)\\s*:\\s*(\\S+)");
            Pattern pattern2 = Pattern.compile("(\\w+)\\s*:\\s*([+-]?\\d+)");

            Matcher matcher1 = pattern1.matcher(tagContent);
            while (matcher1.find()) {
                String key = matcher1.group(1);
                String value = matcher1.group(2);
                element.addPair(key, value);
            }

            Matcher matcher2 = pattern2.matcher(tagContent);
            while (matcher2.find()) {
                String key = matcher2.group(1);
                int value = Integer.parseInt(matcher2.group(2));
                element.addPair(key, value);
            }

            frameSegment.addElement(tagName, element);
        }

        private void processFrameTag(String tagContent) {

            // [!] Under construction.

            // 1. Find frameNumber, frameName
            Pattern pattern1 = Pattern.compile("<frame>\\s*(\\d+)\\s+(\\w+)");
            Matcher pattern1Matcher = pattern1.matcher(tagContent);

            if (pattern1Matcher.find()) {

                int frameNumber = Integer.parseInt(pattern1Matcher.group(1));
                String frameName = pattern1Matcher.group(2);
                frameSegment = new FrameSegment(script, frameNumber, frameName);
                script.addFrameSegment(frameSegment);

            } else throw new ScriptParsingErrorException(
                    "Frame node: frameNumber, frameName format is incorrect!"
            );

            // 2. Find attribute pairs
            Pattern pattern2 = Pattern.compile(
                    "\\n(.*?)<(?!/)",
                    Pattern.DOTALL | Pattern.MULTILINE);
            Pattern pattern2_1 = Pattern.compile("(\\w+)\\s*:\\s*(\\S+)");
            Pattern pattern2_2 = Pattern.compile("(\\w+)\\s*:\\s*([+-]?\\d+)");
            Matcher pattern2Matcher = pattern2.matcher(tagContent);

            if (!pattern2Matcher.find()) {
                throw new ScriptParsingErrorException(
                        "Frame node: attributes pairs are missing!");
            } else do {

                String keyValuePairs = pattern2Matcher.group(1);
                Matcher pattern2_1Matcher = pattern2_1.matcher(keyValuePairs);
                while (pattern2_1Matcher.find()) {
                    String key = pattern2_1Matcher.group(1);
                    String value = pattern2_1Matcher.group(2);
                    frameSegment.addPair(key, value);
                }

                Matcher pattern2_2Matcher = pattern2_2.matcher(keyValuePairs);
                while (pattern2_2Matcher.find()) {
                    String key = pattern2_2Matcher.group(1);
                    int value = Integer.parseInt(pattern2_2Matcher.group(2));
                    frameSegment.addPair(key, value);
                }

            } while (pattern2Matcher.find());


            // 3. Find Element tags
            Pattern pattern3 = Pattern.compile(
                    "<(\\w+)>(.*?)</\\1>",
                    Pattern.DOTALL | Pattern.MULTILINE);
            Matcher pattern3Matcher = pattern3.matcher(tagContent);
            while (pattern3Matcher.find()) {
                String elementName = pattern3Matcher.group(1);
                String elementContent = pattern3Matcher.group(2);
                processElementTag(elementName, elementContent);
            }

            // 4. Before creating frame, pass it frameSegment to client's rule
            listener.onParsing(frameSegment);


            // 5. Finally, create frame and add it to fsmc
            Frame frame = frameFactory.createFrame(frameSegment);
            fsmc.addState(frame);
        }

        public Script parse(String scriptText) {}

    }
}
