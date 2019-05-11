package com.pokewords.framework.sprites.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LinScriptSegment implements Segment {
    private LinScript parentScript;
    private ArrayList<Element> elements;
    private Script.Mappings mappings;

    public LinScriptSegment(String segmentName, int segmentId) {
        init();
        mappings.stringMap.put(ScriptDef.LinScript.Segment.NAME, segmentName);
        mappings.integerMap.put(ScriptDef.LinScript.Segment.ID, segmentId);
    }

    public LinScriptSegment(String segmentName, int segmentId, String segmentDescription) {
        init();
        mappings.stringMap.put(ScriptDef.LinScript.Segment.NAME, segmentName);
        mappings.integerMap.put(ScriptDef.LinScript.Segment.ID, segmentId);
        mappings.stringMap.put(ScriptDef.LinScript.Segment.DESCRIPTION, segmentDescription);
    }

    private void init() {
        elements = new ArrayList<>();
        mappings = new Script.Mappings();
    }

    // Elements management

    public Segment addElement(Element element) {
        elements.add(element);
        element.setParentSegment(this);
        return this;
    }

    public List<Element> getElementsByName(String elementName) {
        return elements.stream()
                .filter(element -> element.getStringByKey(Element.Def.NAME).equals(elementName))
                .collect(Collectors.toList());
    }

    // Maps management

    public Segment putKVPair(String key, String value) {
        mappings.stringMap.put(key, value);
        return this;
    }

    public Segment putKVPair(String key, int value) {
        mappings.integerMap.put(key, value);
        return this;
    }

    public Optional<String> getStringByKey(String key) {
        return Optional.of(mappings.stringMap.get(key));
    }

    public Optional<Integer> getIntByKey(String key) {
        return Optional.of(mappings.integerMap.get(key));
    }

    // parentScript management

    public LinScript getParentScript() {
        return parentScript;
    }

    public void setParentScript(LinScript parentScript) { this.parentScript = parentScript; }

    // Not recommended

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return toString(4);
    }

    public String toString(int indentation) {
        StringBuilder resultBuilder = new StringBuilder();
        String indent = ""; for (int i = 1; i<=indentation; i++) indent += " ";
        resultBuilder.append("<" + mappings.stringMap.get(Def.NAME) + ">")
                .append(" " + mappings.integerMap.get(Def.ID))
                .append(" " + mappings.stringMap.get(Def.DESCRIPTION)).append("\\n");
        for (Map.Entry<String, String> entry : mappings.stringMap.entrySet()) { resultBuilder.append(indent + entry.getKey() + " " + entry.getValue() + "\\n"); }
        for (Map.Entry<String, Integer> entry : mappings.integerMap.entrySet()) { resultBuilder.append(indent + entry.getKey() + " " + entry.getValue() + "\\n"); }
        for (Element element : elements) { resultBuilder.append(indent + element.toString(indentation)); }
        resultBuilder.append("</" + mappings.stringMap.get(Def.NAME) + ">").append("\\n");
        return resultBuilder.toString();
    }
}
