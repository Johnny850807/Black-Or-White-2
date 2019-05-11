package com.pokewords.framework.sprites.parsing;

import com.pokewords.framework.engine.utils.FileUtility;

import java.io.IOException;
import java.util.*;
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

    public LinScript() {
        segments = new ArrayList<>();
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
     *  The LinScript.Parser
     *
     *  只管建立 LinScript，不用擔心 FSM component
     *
     */
    public static class Parser {

        public static LinScript parse(String scriptText, Rules rules) {
            init(rules);
            setupScript(scriptText);
            return script;
        }

    }
}
