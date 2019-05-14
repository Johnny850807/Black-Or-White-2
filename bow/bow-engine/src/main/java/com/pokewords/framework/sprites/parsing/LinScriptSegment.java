package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *  Each segment has a reference to its parent Script, possibly some Elements, and its key-value pairs.
 *  @author nyngwang
 */
public class LinScriptSegment implements Segment {
    private LinScript parentScript;
    private List<Element> elements;
    private Script.Mappings mappings;

    public LinScriptSegment(String segmentName, int segmentId) {
        init();
        mappings.stringMap.put(ScriptDefinitions.LinScript.Segment.NAME, segmentName);
        mappings.integerMap.put(ScriptDefinitions.LinScript.Segment.ID, segmentId);
    }

    public LinScriptSegment(String segmentName, int segmentId, String segmentDescription) {
        init();
        mappings.stringMap.put(ScriptDefinitions.LinScript.Segment.NAME, segmentName);
        mappings.integerMap.put(ScriptDefinitions.LinScript.Segment.ID, segmentId);
        mappings.stringMap.put(ScriptDefinitions.LinScript.Segment.DESCRIPTION, segmentDescription);
    }

    private void init() {
        elements = new ArrayList<>();
        mappings = new Script.Mappings();
    }

    @Override
    public Segment addElement(Element element) {
        elements.add(element);
        element.setParentSegment(this);
        return this;
    }

    @Override
    public List<Element> getElementsByName(String elementName) {
        return elements.stream()
                .filter(element ->
                        element.getStringByKey(ScriptDefinitions.LinScript.Element.NAME).orElse("")
                                .equals(elementName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Element> getElements() {
        return elements;
    }

    @Override
    public Segment put(String key, String value) {
        mappings.stringMap.put(key, value);
        return this;
    }

    @Override
    public Segment put(String key, int value) {
        mappings.integerMap.put(key, value);
        return this;
    }

    @Override
    public Optional<String> getStringByKey(String key) {
        return Optional.of(mappings.stringMap.get(key));
    }

    @Override
    public Optional<Integer> getIntByKey(String key) {
        return Optional.of(mappings.integerMap.get(key));
    }

    @Override
    public Segment setParentScript(Script parentScript) {
        this.parentScript = (LinScript) parentScript;
        return this;
    }

    @Override
    public LinScript getParentScript() {
        return parentScript;
    }

    @Override
    public String toString() {
        return toString(4);
    }

    @Override
    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = ""; for (int i = 1; i<=indentation; i++) indent += " ";
        resultBuilder
                .append("<").append(mappings.stringMap.get(ScriptDefinitions.LinScript.Segment.NAME)).append(">")
                .append(" ").append(mappings.integerMap.get(ScriptDefinitions.LinScript.Segment.ID))
                .append(" ").append(mappings.stringMap.get(ScriptDefinitions.LinScript.Segment.DESCRIPTION)).append('\n');
        for (Map.Entry<String, String> entry : mappings.stringMap.entrySet()) {
            resultBuilder
                    .append(indent).append(entry.getKey())
                    .append(" ").append(entry.getValue()).append('\n');
        }
        for (Map.Entry<String, Integer> entry : mappings.integerMap.entrySet()) {
            resultBuilder
                    .append(indent).append(entry.getKey())
                    .append(" ").append(entry.getValue()).append('\n');
        }
        for (Element element : elements) {
            resultBuilder.append(indent).append(element.toString(indentation)).append('\n');
        }
        resultBuilder
                .append("</").append(mappings.stringMap.get(ScriptDefinitions.LinScript.Segment.NAME)).append(">").append('\n');
        return resultBuilder.toString();
    }
}
